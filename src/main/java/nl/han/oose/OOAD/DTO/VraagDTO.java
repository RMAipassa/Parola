package nl.han.oose.OOAD.DTO;

public class VraagDTO {
    private int id;
    private String vraagText;
    private boolean isMultipleChoice;
    private int quizId;

    public VraagDTO(int id, String vraagText, boolean isMultipleChoice, int quizId) {
        this.id = id;
        this.vraagText = vraagText;
        this.isMultipleChoice = isMultipleChoice;
        this.quizId = quizId;
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

    public int getQuizId() {
        return quizId;
    }
}

