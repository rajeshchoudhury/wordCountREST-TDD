package com.wordcount.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class WordStore {
	
	private static WordStore wStore;

	private Map <String, Integer> wordMap;
	
	private WordStore() {
		wordMap = new HashMap<String, Integer>(); 
	}
	
	public static WordStore getInstance(){
	   if(wStore == null){
	      	wStore = new WordStore();
	       }
     return wStore;
    }


	public Map<String, Integer> getWordMap() {
		return wordMap;
	}		

	 public String prettyPrint() {
	        StringBuilder sb = new StringBuilder();
	        Iterator<Entry<String, Integer>> iter = wordMap.entrySet().iterator();
	        while (iter.hasNext()) {
	            Entry<String, Integer> entry = iter.next();
	            sb.append(entry.getKey()).append(" - ").append(entry.getValue());	            
	            if (iter.hasNext()) {
	                sb.append(',').append(' ');
	            }
	        }
	        return sb.toString();
	    }
	
}
