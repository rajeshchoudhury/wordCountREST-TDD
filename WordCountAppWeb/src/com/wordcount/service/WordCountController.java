package com.wordcount.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/wordcount")

public class WordCountController {

	@Autowired
	WordStore wordStore = WordStore.getInstance();
	
    @RequestMapping(value="/addwords/{words}", method=RequestMethod.POST)
    public ResponseEntity<String> addWords(@PathVariable String words) {
 
    	if(null != words && !"".equalsIgnoreCase(words.trim())){
        	StringTokenizer st = new StringTokenizer(words.trim());
            while (st.hasMoreTokens()) {
            	String word = st.nextToken(); 
            	if(wordStore.getWordMap().containsKey(word))
            	{
            		int newCount = wordStore.getWordMap().get(word).intValue() + 1; 
            		wordStore.getWordMap().remove(word);
            		wordStore.getWordMap().put(word,newCount);
            	}
            	else
            	{
            		wordStore.getWordMap().put(word, 1);
            	}
            }
        }
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<String>(httpHeaders, HttpStatus.CREATED);
        }
    
    
    @RequestMapping(value="/getwordcounts/{words}", method=RequestMethod.GET)
    public ResponseEntity<String> getWordCounts(@PathVariable String words) {
    	
         if(null != words && !"".equalsIgnoreCase(words.trim())){
         	 Map <String, Integer> filteredWordMap = new HashMap<String, Integer>();
            StringTokenizer st = new StringTokenizer(words.trim());
            while (st.hasMoreTokens()) {
            	String word = st.nextToken(); 
            	if(wordStore.getWordMap().containsKey(word))
            	{
            		filteredWordMap.put (word,wordStore.getWordMap().get(word).intValue());            		
            	}
            	else
            	{
            		filteredWordMap.put (word,0);            		
            	}
            }
            
            StringBuilder sb = new StringBuilder();
	        Iterator<Entry<String, Integer>> iter = filteredWordMap.entrySet().iterator();
	        while (iter.hasNext()) {
	            Entry<String, Integer> entry = iter.next();
	            sb.append(entry.getKey()).append(" - ").append(entry.getValue());	            
	            if (iter.hasNext()) {
	                sb.append(',').append(' ');
	            }
	        }
	        final HttpHeaders httpHeaders= new HttpHeaders();
	        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	        return new ResponseEntity<String>(sb.toString(), httpHeaders, HttpStatus.OK);
   
        }
        else {
        	 final HttpHeaders httpHeaders= new HttpHeaders();
 	        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
 	        return new ResponseEntity<String>(wordStore.prettyPrint(), httpHeaders, HttpStatus.OK);                         
        }
    }
    
    
	@RequestMapping(value="/resetwordcounts/{words}", method=RequestMethod.DELETE)
    public ResponseEntity<String> resetWordCounts(@PathVariable String words) {
		
		 if(null != words && !"".equalsIgnoreCase(words.trim())){			 
			 StringTokenizer st = new StringTokenizer(words.trim());
             while (st.hasMoreTokens()) {
                     wordStore.getWordMap().remove(st.nextToken());
             }
        }
        else {
        	wordStore.getWordMap().clear();             
        }
		 
		 final HttpHeaders httpHeaders= new HttpHeaders();
	     httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	     return new ResponseEntity<String>(httpHeaders, HttpStatus.OK);
    }
 
    
    
    
    
}