package model;

import com.google.gson.annotations.Expose;

public class Author {
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
