package controllers;

import models.Word;
import models.WordType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dictionary parser to get a word list from a CSV file
 *
 * @author Yumou, Yang
 * @version 10/3/2014
 */
public class DictionaryParser {
    String csvFile;
    BufferedReader br;
    String line;
    String cvsSplitBy = ",";
    List<Word> wordList = new ArrayList<Word>();

    /**
     * Constructor
     *
     * @param FileName The file name of the word list CSV file
     */
    public DictionaryParser(String FileName) {
        csvFile = FileName;
    }

    /**
     * Returns the CSV file name
     *
     * @return String returns the file name of the CSV file
     */
    public String getFileName() {
        return this.csvFile;
    }

    /**
     * Parses the CSV file and returns a word list
     *
     * @return List<Word> returns the word list parsed from the CSV file
     */

    public List<Word> parse() {
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] s = line.split(cvsSplitBy);
                String wordValue = s[0].trim();
                WordType wordType = stringToWordType(s[1].trim());
                if (wordType != null) {
                    Word word = new Word(wordValue, wordType);
                    wordList.add(word);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return wordList;
    }

    /**
     * transfer a string to an enum word type
     *
     * @param wordType
     * @return WordType returns the corresponding WordType of the string
     */
    public WordType stringToWordType(String wordType) {
        for (WordType w : WordType.values()) {
            if (w.toString().equalsIgnoreCase(wordType)) {
                return w;
            }
        }
        return null;
    }

}
