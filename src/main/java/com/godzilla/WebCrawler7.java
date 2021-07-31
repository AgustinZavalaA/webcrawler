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

    /**
     * Este método se encarga de crear un objeto de tipo WebCrawler7, recibiendo como 
     * parámetros el URL inicial de la navegación y el máximo de Threads. 
     * @param startingURL Recibe como parámetro de tipo String la URL inicial.
     * @param maxThreads Recibe como parámetro de tipo int, el número de hilos que se crearán.
     */
    public WebCrawler7(String startingURL, int maxThreads) {
        this.url = startingURL;
        mainPool = new ForkJoinPool(maxThreads);
    }

    /**
     * Este método como su nombre indica, comienza a visitar la página, mandando un 
     * LinkFinderAction al método invoke del ForkJoinPool. Este contendrá la URL inicial, 
     * la profundidad, y el nombre del archivo de salida.
     * @param depth
     * @param fname
     */
    public void startCrawling(int depth, String fname) {
        System.out.println("visitando: " + this.url);
        mainPool.invoke(new LinkFinderAction(this.url, this, depth, fname));
    }

    /**
     * Getter para obtener el número de información encontrada.
     * @return Retorna el número de ocurrencias.
     */
    public int x() {
        return x++;
    }

    @Override
    public void queueLink(String link) throws Exception {

    }

    @Override
    /**
     * Getter que retorna el número de links visitados durante el 
     * proceso del web crawler.
     * @return Retorna el número de ocurrencias de links visitados.
     */
    public int size() {
        return visitedLinks.size();
    }

    @Override
    /**
     * Método que retorna si un enlace fue o no visitado.
     * @param link recibe el link que fue visitado.
     * @return Retorna true o false, si realmente se visitó o no.
     */
    public boolean visited(String link) {
        return visitedLinks.contains(link);
    }

    @Override
    /**
     * Método que añade los links visitados a una lista.
     * @param link recibe el link que fue visitado anteriormente.
     */
    public void addVisited(String link) {
        visitedLinks.add(link);
    }
}
