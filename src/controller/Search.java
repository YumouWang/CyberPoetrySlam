package controller;

import java.util.Hashtable;
import java.util.Set;

public class Search {
	private static Search search;
	public static Hashtable<String,String> wordtable = new Hashtable<String, String>();
	
	public void initTable() {
		//getTable(){}
		wordtable.put("day", "noun");
		wordtable.put("fast", "adjective");
		wordtable.put("slowly", "adverb");
		wordtable.put("class", "noun");
		wordtable.put("sun", "noun");
		wordtable.put("eye", "noun");
		wordtable.put("close", "verb");
		wordtable.put("nice", "adj");
		wordtable.put("computer", "noun");
		wordtable.put("book", "noun");
		wordtable.put("story", "noun");
		wordtable.put("city", "noun");
	}
	
	public static Search getInstance() {
		if(search == null) {
			search = new Search();
		}
		return search;
	}
	
	public Hashtable<String,String> search(String word, String wordtype) {
		
		Hashtable<String,String> result = new Hashtable<String, String>();
		if(word.equals("") && wordtype.equals("")) {
			return Search.wordtable;
		}
		Set<String> keys = wordtable.keySet();
        for(String key: keys){  
            if(word.equals(key) && wordtype.equals(wordtable.get(key))) {
            	result.put(key, wordtable.get(key));
            }
            if(word.equals(key) && wordtype.equals("")) {
            	result.put(key, wordtable.get(key));
            }
            if(word.equals("") && wordtype.equals(wordtable.get(key))) {
            	result.put(key, wordtable.get(key));
            }           
        }
		return result;
	}

}

