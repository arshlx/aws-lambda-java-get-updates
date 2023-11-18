package model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class LambdaResponseBody {
    @Expose
    public ArrayList<LambdaEntryItem> updateEntries;

    public LambdaResponseBody(ArrayList<LambdaEntryItem> updateEntries) {
        this.updateEntries = updateEntries;
    }

    public ArrayList<LambdaEntryItem> getUpdateEntries() {
        return updateEntries;
    }

    public void setUpdateEntries(ArrayList<LambdaEntryItem> updateEntries) {
        this.updateEntries = updateEntries;
    }
}
