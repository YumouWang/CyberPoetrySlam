package controllerTest;

import java.util.Hashtable;
import java.util.Set;

import controllers.Search;

public class TestSearch {
	public static void main(String[] args) {
		Search.getInstance().initTable();
		Hashtable<String,String> result = Search.getInstance().search("day", "");
		Set<String> keys = result.keySet();
		System.out.println(keys.size());
        for(String key: keys){  
            System.out.println(key +" : " + result.get(key));
        }
	}
}
