package nl.han.oose.OOAD.Schermen;

import nl.han.oose.OOAD.Entity.Gebruiker;

import java.util.Scanner;

public class RegistratieScherm {
    private Scanner scanner = new Scanner(System.in);

    public Gebruiker toonFormulier() {
        System.out.println("Welkom bij de registratie!");
        System.out.print("Voer een gebruikersnaam in: ");
        String gebruikersnaam = scanner.next();
        return new Gebruiker(gebruikersnaam, 1000); // Startsaldo van 1000 credits
    }
}
