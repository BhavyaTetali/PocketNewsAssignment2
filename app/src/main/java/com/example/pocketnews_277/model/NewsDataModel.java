package com.example.pocketnews_277.model;

import java.util.ArrayList;
import java.util.List;

public class NewsDataModel {
    private String status;
    private int totalResults;
    private List<ArticleModel> articles;

    public NewsDataModel() {
        articles = new ArrayList<>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<ArticleModel> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleModel> articles) {
        this.articles = articles;
    }

}
