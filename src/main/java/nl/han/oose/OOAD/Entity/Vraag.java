package nl.han.oose.OOAD.Entity;

import java.util.ArrayList;
import java.util.List;

public class Vraag {
    private int id;
    private String vraagText;
    private boolean isMultipleChoice;

    private List<Antwoord> antwoorden;

    public Vraag(int id, String vraagText, boolean isMultipleChoice) {
        this.id = id;
        this.vraagText = vraagText;
        this.isMultipleChoice = isMultipleChoice;
        this.antwoorden = new ArrayList<>();
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

    public void addAntwoord(String antwoordText, boolean isCorrect) {
        Antwoord antwoord = new Antwoord(antwoordText, isCorrect);
        antwoorden.add(antwoord);
    }

    public List<Antwoord> getAntwoorden() {
        return antwoorden;
    }
}

