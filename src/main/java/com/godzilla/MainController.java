package com.godzilla;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private TextField textFieldURL;

    @FXML
    private TextField textFieldThreads;

    @FXML
    private TextField textFieldDepth;

    @FXML
    private TextField textFieldOut;

    @FXML
    private Button buttonStart;

    @FXML
    public void initialize() {
        textFieldURL.setText("https://www.rottentomatoes.com/m/avengers_endgame");
        textFieldThreads.setText("64");
        textFieldDepth.setText("1");
        textFieldOut.setText("out.csv");
    }

    @FXML
    /**
     * Este método se encarga de realizar todo el proceso del Web Crawler al presionar 
     * el botón start, extrayendo la información de las cajas de texto.
     */
    public void handleStart() {

        // String cite = "https://www.rottentomatoes.com/tv/loki";
        // String cite = "https://www.imdb.com/title/tt4154796/";
        // String cite = "https://www.metacritic.com/movie/the-suicide-squad";
        // String cite = "https://www.rottentomatoes.com/m/avengers_endgame";
        // int noThreads = 64;
        // int depth = 1;
        // String outFileName = "out.csv";

        String cite = textFieldURL.getText();
        int noThreads = Integer.parseInt(textFieldThreads.getText());
        int depth = Integer.parseInt(textFieldDepth.getText());
        String outFileName = textFieldOut.getText();
        try {
            CSVWriter.addHeader(outFileName);
            new WebCrawler7(cite, noThreads).startCrawling(depth, outFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Finished");
    }

}