package model;

import com.google.gson.annotations.Expose;

import java.util.List;

@SuppressWarnings("unused")
public class Entry {

    @Expose
    private List<Author> authors;
    @Expose
    private String content;
    @Expose
    private String id;
    @Expose
    private List<Link> links;
    @Expose
    private String published;
    @Expose
    private String title;

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
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

}
