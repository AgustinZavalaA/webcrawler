package com.godzilla;

import com.godzilla.net.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;

public class WebCrawler7 implements LinkHandler {
    private int x = 0;

    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
    // private final Collection<String> visitedLinks =
    // Collections.synchronizedList(new ArrayList<>());
    private String url;
    private ForkJoinPool mainPool;

    public WebCrawler7(String startingURL, int maxThreads) {
        this.url = startingURL;
        mainPool = new ForkJoinPool(maxThreads);
    }

    public void startCrawling(int depth, String fname) {
        System.out.println("visitando: " + this.url);
        mainPool.invoke(new LinkFinderAction(this.url, this, depth, fname));
    }

    public int x() {
        return x++;
    }

    @Override
    public void queueLink(String link) throws Exception {

    }

    @Override
    public int size() {
        return visitedLinks.size();
    }

    @Override
    public boolean visited(String link) {
        return visitedLinks.contains(link);
    }

    @Override
    public void addVisited(String link) {
        visitedLinks.add(link);
    }
}
