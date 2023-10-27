package nl.han.oose.OOAD.Adapters;

import nl.han.oose.OOAD.Entity.Dictionary;

public class DictionaryAdapter {
    public boolean validateWord(String word) {
        return Dictionary.checkWordInWordList(word);
    }
}
