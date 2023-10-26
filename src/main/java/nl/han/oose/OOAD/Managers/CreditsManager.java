package nl.han.oose.OOAD.Managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreditsManager {
    private Map<String, Integer> creditBalances;

    public CreditsManager() {
        this.creditBalances = new HashMap<>();
    }

    public int getCreditBalance(String username) {
        return creditBalances.getOrDefault(username, 0);
    }

    public int buyCredits() {

        // Display available credit packages
        System.out.println("Choose a credit package:");
        System.out.println("1. 1000 credits - €1.00");
        System.out.println("2. 2250 credits - €2.00");
        System.out.println("3. 5000 credits - €4.00");
        System.out.println("4. 10000 credits - €7.00");
        System.out.print("Enter the package number: ");

        Scanner scanner = new Scanner(System.in);
        int packageChoice = scanner.nextInt();
//        TODO Proper payment method with payment processor
//        int price = calculatePrice(packageChoice);


        return getCreditAmount(packageChoice);


    }



    private int getCreditAmount(int packageChoice) {
        switch (packageChoice) {
            case 1:
                return 1000;
            case 2:
                return 2250;
            case 3:
                return 5000;
            case 4:
                return 10000;
            default:
                return 0; // Invalid choice
        }
    }
}
