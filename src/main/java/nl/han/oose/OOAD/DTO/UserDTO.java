package nl.han.oose.OOAD.DTO;

public class UserDTO {
    private String username;
    private int credits;

    public UserDTO(String username, int credits) {
        this.username = username;
        this.credits = credits;
    }

    public String getUsername() {
        return username;
    }

    public int getCredits() {
        return credits;
    }
}
