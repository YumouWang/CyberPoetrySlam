package controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import models.GameState;
import models.Position;
import models.Word;
import models.WordType;

import org.junit.Before;
import org.junit.Test;

import views.MainView;
import views.WordView;

public class MoveWordControllerTest {
	GameState gameState;
    MainView mainView;
    MoveWordController moveWordController;
    WordView wordViewOne;
    WordView wordViewTwo;
    WordView wordViewThree;
    WordView wordViewFour;
    WordView wordViewFive;
    Position protectedAreaPosition;
    Position unprotectedAreaPosition;

    @Before
    public void initialize() {
        gameState = new GameState();
        gameState.getProtectedArea().getAbstractWordCollection().clear();
        mainView = new MainView(gameState);
        
        wordViewOne = new WordView(new Word("Word1", WordType.NOUN), new Position(10, 10));
        wordViewTwo = new WordView(new Word("Word2", WordType.NOUN), new Position(50, 50));
        wordViewThree = new WordView(new Word("Word3", WordType.NOUN), new Position(50, 70));        
        wordViewFour = new WordView(new Word("Word4", WordType.NOUN), new Position(10, 300));
        protectedAreaPosition = new Position(10, 20);
        unprotectedAreaPosition = new Position(30, 300);
        mainView.addProtectedAbstractWordView(wordViewOne);
        gameState.getProtectedArea().addAbstractWord(wordViewOne.getWord());
        mainView.addProtectedAbstractWordView(wordViewTwo);
        gameState.getProtectedArea().addAbstractWord(wordViewTwo.getWord());
        mainView.addUnprotectedAbstractWordView(wordViewFour);
        gameState.getUnprotectedArea().addAbstractWord(wordViewFour.getWord());
        moveWordController = new MoveWordController(mainView, gameState);
    }
    
    @Test
    public void testMoveWord() {
    	//test move word from protected area to protected area
    	moveWordController.moveWord(wordViewOne, wordViewOne.getPosition(), protectedAreaPosition);
    	assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordViewOne.getWord()));
    	assertTrue(mainView.getProtectedAreaWords().contains(wordViewOne));
    	assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection().contains(wordViewOne.getWord()));
		assertFalse(mainView.getUnprotectedAreaWords().contains(wordViewOne));
    	
    	//test move word from unprotected area to unprotected area
    	moveWordController.moveWord(wordViewFour, wordViewFour.getPosition(), unprotectedAreaPosition);
    	assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordViewFour.getWord()));
    	assertFalse(mainView.getProtectedAreaWords().contains(wordViewFour));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection().contains(wordViewFour.getWord()));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewFour));
    	
    	// test move word from unprotected area to protected area
    	moveWordController.moveWord(wordViewFour, wordViewFour.getPosition(), protectedAreaPosition);
    	assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordViewFour.getWord()));
		assertTrue(mainView.getProtectedAreaWords().contains(wordViewFour));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection().contains(wordViewFour.getWord()));
		assertFalse(mainView.getUnprotectedAreaWords().contains(wordViewFour));
		
		// test move word from protected area to unprotected area
    	moveWordController.moveWord(wordViewOne, wordViewOne.getPosition(), unprotectedAreaPosition);
    	assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordViewOne.getWord()));
    	assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
    	assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection().contains(wordViewOne.getWord()));
    	assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewOne));
    }

	@Test
	public void testProtectWord() {	
		moveWordController.protectWord(wordViewFour);
		assertNotNull(moveWordController);
		assertTrue(gameState.getProtectedArea().getAbstractWordCollection().contains(wordViewFour.getWord()));
		assertTrue(mainView.getProtectedAreaWords().contains(wordViewFour));
		assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection().contains(wordViewFour.getWord()));
		assertFalse(mainView.getUnprotectedAreaWords().contains(wordViewFour));
	}
	
	@Test
	public void testUnprotectWord() {	
		moveWordController.unprotectWord(wordViewOne);
		assertNotNull(moveWordController);
		assertFalse(gameState.getProtectedArea().getAbstractWordCollection().contains(wordViewOne.getWord()));
		assertFalse(mainView.getProtectedAreaWords().contains(wordViewOne));
		assertTrue(gameState.getUnprotectedArea().getAbstractWordCollection().contains(wordViewOne.getWord()));
		assertTrue(mainView.getUnprotectedAreaWords().contains(wordViewOne));
	}
	
	@Test
	public void testReleaseRow() {
		
	}
	
	@Test
	public void testReleasePoem() {
		
	}
	
	
	

}
