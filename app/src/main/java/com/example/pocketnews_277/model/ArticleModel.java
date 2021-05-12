package com.example.pocketnews_277.model;

public class ArticleModel {

    private String title;
    private String author;
    private String description;
    private String publishedAt;
    private String urlToImage;
    private String url;
    private String content;
    private String category;

    private String returnEmptyIfNull(String value){
        return value == null? "": value;
    }

    private String formatDate(String date){
        return date.replaceAll("(T)|(:..Z)", " ");
    }

    public String getUrl() {
        return returnEmptyIfNull(url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return returnEmptyIfNull(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return returnEmptyIfNull(author);
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return returnEmptyIfNull(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return formatDate(returnEmptyIfNull(publishedAt));
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrlToImage() {
        return returnEmptyIfNull(urlToImage);
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return returnEmptyIfNull(content);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return returnEmptyIfNull(category);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArticleModel(String title, String author, String description, String publishedAt, String urlToImage, String url, String content) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.urlToImage = urlToImage;
        this.url = url;
        this.content = content;
        this.publishedAt =  formatDate(publishedAt);
    }
}
