package nl.han.oose.OOAD.DTO;

public class QuizDTO {
    private int id;
    private String name;
    private String theme;

    public QuizDTO(int id, String name, String theme) {
        this.id = id;
        this.name = name;
        this.theme = theme;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTheme() {
        return theme;
    }
}
