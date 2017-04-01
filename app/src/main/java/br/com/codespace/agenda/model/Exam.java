package br.com.codespace.agenda.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gilma on 01/04/2017.
 */

public class Exam implements Serializable {
    private int id;
    private String matter;
    private String date;
    private List topics;

    public Exam(String matter, String date, List topics) {
        this.date = date;
        this.matter = matter;
        this.topics = topics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(this.matter);
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List getTopics() {
        return topics;
    }

    public void setTopics(List topics) {
        this.topics = topics;
    }
}
