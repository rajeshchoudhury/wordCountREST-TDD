package com.wordcount.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class WordCountControllerTest {
	
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    WordStore wordStore; 
    @Before
    public void setup() {
    	wordStore = WordStore.getInstance();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void validate_addWords() throws Exception {

        mockMvc.perform(post("/addwords/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.street").value("12345 Horton Ave"));
    }
	
	
    @Test
    public void validate_getWordCounts_basic() throws Exception {
        mockMvc.perform(get("/getwordcounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    
	@Test
	 public void singleWord_AddWords() throws Exception {
		WordCountController wordCountController = new WordCountController();
		wordStore.getWordMap().clear();
		wordCountController.addWords("testword1");
		assertEquals(wordStore.getWordMap().get("testword1").intValue(),1 );
		
     }
	
	@Test
	 public void singleWord_repeatedtwice_AddWords() throws Exception {
		WordCountController wordCountController = new WordCountController();
		wordStore.getWordMap().clear();
		wordCountController.addWords("testword testword");
		assertEquals(wordStore.getWordMap().get("testword1").intValue(),2);		
    }
	
	
	@Test
	 public void empty_string_AddWords() throws Exception {
		WordCountController wordCountController = new WordCountController();
		wordStore.getWordMap().clear();
		wordCountController.addWords(" ");
		assertEquals(wordStore.getWordMap().size(),0);		
   }
	
	@Test
	 public void leading_trailing_spaces_AddWords() throws Exception {
		WordCountController wordCountController = new WordCountController();
		wordStore.getWordMap().clear();
		wordCountController.addWords(" testword1 testword2 testword1 ");
		wordCountController.addWords(" testword3 testword1   testword1");
        assertEquals(wordStore.getWordMap().get("testword1").intValue(),4);		
  }
	

	@Test
	  public void clear_resetWordCounts() throws Exception {
		WordCountController wordCountController = new WordCountController();
		wordStore.getWordMap().clear();
		wordCountController.addWords(" testword1 testword2 testword1 ");
		wordCountController.addWords(" testword3 testword1   testword1");
		wordCountController.resetWordCounts(" ");
		assertEquals(wordStore.getWordMap().size(),0);		
 }
	
	@Test
	  public void reset1word_resetWordCounts() throws Exception {
		WordCountController wordCountController = new WordCountController();
		wordStore.getWordMap().clear();
		wordCountController.addWords(" testword1 testword2 testword1 ");
		wordCountController.addWords(" testword3 testword1   testword1");
		wordCountController.resetWordCounts("testword3");
		assertEquals(wordStore.getWordMap().size(),2);		
}

}
