package com.godzilla.net;

import com.godzilla.LinkHandler;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LinkFinder implements Runnable {

    private String url;
    private LinkHandler linkHandler;

    /**
     * Se utiliza para estadísticas
     */
    private static final long t0 = System.nanoTime();

    /**
     * Constructor que se encarga de crear un objeto de tipo LinkFinder, 
     * recibiendo como parámetro el URL, y el LinkHandler
     * @param url Recibe el URL en String.
     * @param handler Recibe el objeto de tipo LinkHandler
     */
    public LinkFinder(String url, LinkHandler handler) {
        this.url = url;
        this.linkHandler = handler;
    }

    @Override
    /**
     * Este método se encarga de ejecutar el proceso en cada uno de los Hilos.
     */
    public void run() {
        getSimpleLinks(url);
    }

    /**
     * Este método se encarga de realizar el proceso de visita, extracción de los nodos y el tiempo
     * empleado en realizar estas tareas en cada página visitada. Al final añade toda esta información a una lista
     * @param url Recibe el URL de la página a visitar.
     */
    private void getSimpleLinks(String url) {
        // si no se ha visitado
        if (!linkHandler.visited(url)) {
            try {
                URL urilink = new URL(url);
                Parser parser = new Parser(urilink.openConnection());
                NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
                List<String> urls = new ArrayList<>();

                System.out.println("Analizando: " + url);
                for (int i = 0; i < list.size(); i++) {
                    LinkTag extracted = (LinkTag) list.elementAt(i);

                    if (!extracted.getLink().isBlank() && !linkHandler.visited(extracted.getLink())) {
                        urls.add(extracted.getLink());
                    }
                }

                // visitatamos el link
                linkHandler.addVisited(url);

                if (linkHandler.size() == 1500) {
                    System.out.println("Tiempo para visitar 1500 links = " + (System.nanoTime() - t0));
                }

                for (String l : urls) {
                    linkHandler.queueLink(l);
                }
            } catch (Exception e) {
                // Ignoramos todos las excepciones
                // e.printStackTrace();
            }
        }
    }
}
