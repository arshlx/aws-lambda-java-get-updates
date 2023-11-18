package model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Attributes {

    @SerializedName("xml:base")
    private String xmlBase;

    public String getXmlBase() {
        return xmlBase;
    }

    public void setXmlBase(String xmlBase) {
        this.xmlBase = xmlBase;
    }

}
