package com.example.newsapp;

import java.util.List;

public class NewsResponse {
    private String status;

    private List<DataArticle> results;

    public NewsResponse(String status, List<DataArticle> results) {
        this.status = status;
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataArticle> getResults() {
        return results;
    }

    public void setResults(List<DataArticle> results) {
        this.results = results;
    }
}
