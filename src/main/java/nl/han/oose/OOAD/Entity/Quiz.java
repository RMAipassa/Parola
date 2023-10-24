package nl.han.oose.OOAD.Entity;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private String name;
    private String theme;
    private List<Vraag> vragen;

    public Quiz(String name, String theme) {
        this.name = name;
        this.theme = theme;
        this.vragen = new ArrayList<>();
    }

    public void addVraag(Vraag vraag) {
        vragen.add(vraag);
    }

    public String getName() {
        return name;
    }

    public String getTheme() {
        return theme;
    }

    public List<Vraag> getVragen() {
        return vragen;
    }

}
