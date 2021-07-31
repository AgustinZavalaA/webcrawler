package com.godzilla;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVWriter {
    private String filename;
    private List<String> data = new ArrayList<>();

    /**
     * Constructor de la clase que se encarga de crear el objeto CSVWriter
     * @param filename Recibe un String del nombre que tendrá el archivo de salida.
     */
    public CSVWriter(String filename) {
        this.filename = filename;

    }

    /**
     * Este método se encarga de crear la cabezera del archivo csv, asignándole un 
     * Titulo, Género, y Resumen.
     * @param fname Recibe el nombre del archivo.
     */
    public static void addHeader(String fname) {
        try {
            FileWriter csv = new FileWriter(fname);
            csv.append("Titulo, Genero, Resumen\n");
            csv.flush();
            csv.close();
        } catch (Exception e) { // TODO: handle exception
        }
    }

    /**
     * Este método añade a una Lista de String, lo recopilado.
     * @param d Recibe la lista de tipo String, de lo que recopiló 
     */
    public void add(List<String> d) {
        data.addAll(d);
    }

    /**
     * Este método se encarga de escribir en el archivo csv toda la información obtenida
     * acomodándolo según las columnas de la cabecera.
     * @throws IOException
     */
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
