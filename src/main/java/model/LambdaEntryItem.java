package model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class LambdaEntryItem {
    @Expose
    private List<Author> authors;
    @Expose
    private String link;
    @Expose
    private String published;
    @Expose
    private String title;

    public LambdaEntryItem(List<Author> authors, String link, String published, String title) {
        this.authors = authors;
        this.link = link;
        this.published = published;
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
