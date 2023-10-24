package nl.han.oose.OOAD.Entity;

import java.util.ArrayList;
import java.util.List;
public class Vraag {
    private int id;
    private String vraagText;
    private boolean isMultipleChoice;
    // Add any other properties you need

    public Vraag(int id, String vraagText, boolean isMultipleChoice) {
        this.id = id;
        this.vraagText = vraagText;
        this.isMultipleChoice = isMultipleChoice;
        // Initialize other properties as needed
    }

    public int getId() {
        return id;
    }

    public String getVraagText() {
        return vraagText;
    }

    public boolean isMultipleChoice() {
        return isMultipleChoice;
    }
}
