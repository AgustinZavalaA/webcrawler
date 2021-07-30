package com.godzilla;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVWriter {
    private String filename;
    private List<String> data = new ArrayList<>();

    public CSVWriter(String filename) {
        this.filename = filename;

    }

    public static void addHeader(String fname) {
        try {
            FileWriter csv = new FileWriter(fname);
            csv.append("Titulo, Genero, Resumen\n");
            csv.flush();
            csv.close();
        } catch (Exception e) { // TODO: handle exception
        }
    }

    public void add(List<String> d) {
        data.addAll(d);
    }

    public void write() throws IOException {
        FileWriter csv = new FileWriter(filename, true);
        List<String> newData = new ArrayList<>();
        for (String string : data) {
            string = "\"" + string.replace("\"", "") + "\"";
            // System.out.println(string);
            newData.add(string);
        }
        data = newData;
        // System.out.println(data.toString());
        if (data.size() > 0)
            csv.append(String.join(",", data) + "\n");
        csv.flush();
        csv.close();
    }
}
