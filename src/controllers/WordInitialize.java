package controllers;

import models.Word;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * WordInitialize uses dictionary parser to get a word list from a CSV file
 *
 * @author Yumou, Yang
 * @version 10/28/2014
 */

public class WordInitialize {
	/**
	 * WordList readed from the parser
	 */
    Collection<Word> wordList;

    /**
     * get initial word from a file
     *
     * @param FileName
     * @return
     */
    public Collection<Word> getInitialWordFromFile(String FileName) {
        DictionaryParser parse = new DictionaryParser(FileName);
        List<Word> wordList = parse.parse();
        Collections.shuffle(wordList);
        List<Word> copy = wordList.subList(0, 100);
//		wordList = new HashSet<Word>();
//		for (Iterator itr = hashSet.iterator(); itr.hasNext();) {
//			String wordValue = (String) itr.next();
//			String wordType = (String) hashSet.get(wordValue);
//			if (stringToWordType(wordType) != null) {
//				Word word = new Word(wordValue, stringToWordType(wordType));
//				wordList.add(word);
//			}
//		}
        return copy;
    }


}
