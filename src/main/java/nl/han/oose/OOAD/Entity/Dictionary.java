package nl.han.oose.OOAD.Entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary {
    public static boolean checkWordInWordList(String word) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/wordlist.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase(word.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
