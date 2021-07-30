package com.godzilla;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class HTMLAnalizer {
    private URL url;
    private String page = null;
    private CSVWriter csvFile;

    public HTMLAnalizer(URL url) {
        csvFile = new CSVWriter("out.csv");
        this.url = url;
        if (url.toString().contains("rottentomatoes"))
            page = "tomato";
        else if (url.toString().contains("imdb"))
            page = "imdb";
        else if (url.toString().contains("metacritic"))
            page = "metacritic";

    }

    List<String> infoList = new ArrayList<>();

    public void analize() throws IOException, JSONException {
        infoList.clear();
        if (page != null) {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = in.readLine()) != null) {
                switch (page) {
                    case "tomato":
                        if (url.toString().contains("tv")) {
                            if (line.contains("dataLayer.push")) {
                                JSONObject json = new JSONObject(line.replace("dataLayer.push(", "").replace(");", ""));
                                infoList.add(json.getString("titleGenre"));
                            }
                            if (line.contains("ld+json")) {
                                JSONObject json = new JSONObject(in.readLine());
                                infoList.add(json.getString("name"));

                            }
                        } else {
                            if (line.contains("ld+json")) {
                                String ld = in.readLine();
                                // System.out.println(ld);
                                JSONObject json = new JSONObject(ld);
                                // System.out.println(json.getString("name"));
                                // System.out.println(json.getJSONArray("genre"));
                                infoList.add(json.getString("name"));
                                infoList.add(json.getJSONArray("genre").toString());
                                // System.out.println(infoList);

                            } else if (line.contains("id=\"movieSynopsis\"")) {
                                // System.out.println(in.readLine().trim());
                                infoList.add(in.readLine().trim());
                            }
                        }

                        break;
                    case "imdb":
                        if (line.contains("=\"application/ld+json")) {
                            JSONObject json = new JSONObject(line.replaceAll(".+json\">", "").replace("/*!sc*/", ""));
                            // System.out.println(line.replaceAll(".+json\">", "").replace("/*!sc*/", ""));
                            infoList.add(json.getString("name"));
                            infoList.add(json.getJSONArray("genre").toString());
                            infoList.add(json.getString("description"));
                        }
                        break;
                    default:
                        System.out.println(line);
                        break;
                }

            }
            csvFile.add(infoList);
        }
        // System.out.println(infoList);
        csvFile.write();
    }
}
