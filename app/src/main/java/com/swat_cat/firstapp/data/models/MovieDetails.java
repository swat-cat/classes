package com.swat_cat.firstapp.data.models;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieDetails {
    String id;
    String title;
    String posterUrl;
    List<String> ganres;
    String plot;
    double raiting;

    public MovieDetails(String id, String title, String posterUrl, List<String> ganres, String plot, double raiting) {
        this.id = id;
        this.title = title;
        this.posterUrl = posterUrl;
        this.ganres = ganres;
        this.plot = plot;
        this.raiting = raiting;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<String> getGanres() {
        return ganres;
    }

    public void setGanres(List<String> ganres) {
        this.ganres = ganres;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public double getRaiting() {
        return raiting;
    }

    public void setRaiting(double raiting) {
        this.raiting = raiting;
    }

    public void transformListOfGanres(String rawGanres){
        if (rawGanres!=null && !rawGanres.isEmpty()){
            if (ganres == null){
                ganres  = new ArrayList<>();
            }
            List<String> list = new ArrayList<>();
            for(String s: rawGanres.split(",")){
                s = s.trim();
                list.add(s);
            }
            ganres.addAll(list);
        }
    }

    public void transformRaitingToDouble(String rawRaiting){
        if (rawRaiting!=null && !rawRaiting.isEmpty()){
            if (rawRaiting.contains("%")){
                String percent = rawRaiting.replace("%","");
                int p = Integer.valueOf(percent);
                raiting = p/100.0;
            }else if(rawRaiting.contains("/")){
                String[]values = rawRaiting.split("/");
                List<Integer> intVal = new ArrayList<>();
                for (String v: values){
                    v = v.trim();
                    intVal.add(Integer.valueOf(v));
                }
                if (intVal.size() == 2){
                    raiting = Double.valueOf(intVal.get(0))/Double.valueOf(intVal.get(1));
                }
            }
        }
    }
}
