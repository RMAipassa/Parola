package nl.han.oose.OOAD.DTO;

public class AntwoordDTO {
    private int id;
    private String antwoordText;
    private boolean isCorrect;

    public AntwoordDTO(int id, String antwoordText, boolean isCorrect) {
        this.id = id;
        this.antwoordText = antwoordText;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return id;
    }

    public String getAntwoordText() {
        return antwoordText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
