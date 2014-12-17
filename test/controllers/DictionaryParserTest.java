package controllers;

import common.Constants;
import models.Word;
import models.WordType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DictionaryParserTest {

    @Test
    public void testDictionaryParser() {
        DictionaryParser parse = new DictionaryParser(Constants.WORDS_AND_TYPES_FILENAME);
        assertNotNull(parse);
        assertEquals(parse.getFileName(), Constants.WORDS_AND_TYPES_FILENAME);

        List<Word> wordList = parse.parse();
        assertNotNull(wordList);
        assertTrue(wordList.size() > 0);
    }


    @Test
    public void testStringToWordType() {
        DictionaryParser parse = new DictionaryParser(Constants.WORDS_AND_TYPES_FILENAME);
        String str1 = "noun";
        String str2 = "pronoun";
        String str3 = "n";
        assertEquals(parse.stringToWordType(str1), WordType.NOUN);
        assertEquals(parse.stringToWordType(str2), WordType.PRONOUN);
        assertEquals(parse.stringToWordType(str3), null);
    }

}
