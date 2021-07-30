package com.godzilla.net;

import com.godzilla.HTMLAnalizer;
import com.godzilla.LinkHandler;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class LinkFinderAction extends RecursiveAction {

    private String url;
    private LinkHandler cr;
    private static final long t0 = System.nanoTime();
    private int depth;
    private String fname;

    public LinkFinderAction(String url, LinkHandler cr, int depth, String fname) {
        this.url = url;
        this.cr = cr;
        this.depth = depth;
        this.fname = fname;
    }

    @Override
    protected void compute() {
        if (!cr.visited(url)) {
            try {
                List<RecursiveAction> actions = new ArrayList<RecursiveAction>();
                URL urilink = new URL(url);
                Parser parser = new Parser(urilink.openConnection());
                NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
                HTMLAnalizer analizer = new HTMLAnalizer(urilink);
                analizer.analize();

                for (int i = 0; i < list.size(); i++) {
                    // System.out.println(list.elementAt(i).toString());
                    LinkTag extracted = (LinkTag) list.elementAt(i);

                    if (!extracted.extractLink().isEmpty() && !cr.visited(extracted.extractLink())) {
                        actions.add(new LinkFinderAction(extracted.extractLink(), cr, depth, fname));
                    }
                }
                cr.addVisited(url);

                if (cr.size() > depth) {
                    // System.out.println(cr.x() + "Tiempo: " + (System.nanoTime() - t0));
                } else {
                    // System.out.println(actions.toString());

                    invokeAll(actions);
                }

            } catch (Exception e) {
                // ignoramos todas las excepciones
                // e.printStackTrace();
            }
        }
    }
}
