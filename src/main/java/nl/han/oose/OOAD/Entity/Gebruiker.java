package nl.han.oose.OOAD.Entity;

public class Gebruiker {
    private String gebruikersnaam;
    private int creditBalans;

    public Gebruiker(String gebruikersnaam, int creditBalans) {
        this.gebruikersnaam = gebruikersnaam;
        this.creditBalans = creditBalans;
    }

    public int getCreditBalans() {
        return creditBalans;
    }

    public void deductCredits(int credits) {
        creditBalans -= credits;
    }

    public void addCredits(int credits) {
        creditBalans += credits;
    }

}
