package controllers;



import models.AbstractWord;
import models.GameState;
import models.Position;
import views.AbstractWordView;
import views.AdjacencyType;
import views.MainView;
import views.WordView;

/**
 * Real implementation of undo connect abstractword
 * @author xujian Created 11/17
 *
 */
public class UndoConnectAbstractWord extends UndoMove{

	final MainView mainView;
	final GameState gameState;
	AbstractWordView wordViewOne;
	AbstractWordView wordViewTwo;
	Position oldp;
	Position newp;
	Position targetPosition;
	
	/**
	 * 
	 * @param wordViewOne This is the one connects to wordViewTwo (removed)
	 * @param wordViewTwo This is the one named ConnectTarget
	 * @param mainView 
	 */
	public UndoConnectAbstractWord(Position targetPisition, Position oldp,Position newp,AbstractWordView wordViewOne, 
			AbstractWordView wordViewTwo, MainView mainView, GameState gameState){
		this.targetPosition = targetPisition;
		this.oldp = oldp;
		this.newp = newp;
		this.mainView = mainView;
		this.wordViewOne = wordViewOne;
		this.wordViewTwo = wordViewTwo;
		this.gameState = gameState;
	}
	
	@Override
	public boolean execute() {
		//System.out.println("Debugging with RedoConnect " + oldp.getX() + "   " + oldp.getY());
		//System.out.println("Debugging with RedoConnect " + newp.getX() + "   " + newp.getY());
		if(newp.getX() < targetPosition.getX()) {
			newp.setX(newp.getX() - 3);
		}
		else {
			newp.setX(newp.getX() + 3);
		}
		if(newp.getY() > targetPosition.getY()) {
			newp.setY(newp.getY() + 3);
		}
		else {
			newp.setY(newp.getY() - 3);
		}
		
		//System.out.println(wordViewOne.getPosition().getX() + "  "+ wordViewOne.getPosition().getY());
		MoveWordController moveController = new MoveWordController(mainView, gameState);
        moveController.moveWord(wordViewOne,oldp,newp);	
        //System.out.println("HHHHHH " + wordViewOne.getPosition().getX()+ "  " + wordViewOne.getPosition().getY());
        AbstractWordView connectTarget = null;
        for (AbstractWordView word : mainView.getProtectedWordView()) {
            if (!word.equals(wordViewOne)) {
                AdjacencyType adjacencyType = wordViewOne.isAdjacentTo(word);
                if (adjacencyType != AdjacencyType.NOT_ADJACENT) {
                    connectTarget = word;
                }
            }
        }        
        ConnectController controller = new ConnectController(mainView, gameState);
        controller.connect(wordViewOne, wordViewTwo);  
		return true; 		
	}
	
	@Override
	public boolean undo() {
		System.out.println("Debugging with UndoConnect " + oldp.getX() + "   " + oldp.getY());
		System.out.println("Debugging with UndoConnect " + newp.getX() + "   " + newp.getY());
		// This is not for regular disconnect function. This disconnection only works for undo/redo
		// wordOne  == wordViewToDisconnect
		MoveWordController moveController = new MoveWordController(
				mainView, gameState);
		if ((wordViewOne instanceof WordView) && (wordViewTwo instanceof WordView)) {
			AbstractWord wordOne = wordViewOne.getWord();
			AbstractWord wordTwo = wordViewTwo.getWord();
			//System.out.println("NJBJKBJK" + " " + wordTwo.getValue());
			//boolean result = true;
			for (AbstractWord abstractWord : gameState.getProtectedArea()
					.getAbstractWordCollection()) {
				// System.out.println(abstractWord.getValue());
				if (abstractWord.contains(wordOne)) {
					gameState.getProtectedArea().removeAbstractWord(
							abstractWord);
					break;
				}
			}
			gameState.getProtectedArea().addAbstractWord(wordOne);
			gameState.getProtectedArea().addAbstractWord(wordTwo);
			for (AbstractWordView abstractWordView : mainView
					.getProtectedWordView()) {
				if (abstractWordView.contains(wordViewOne)) {
					mainView.removeProtectedAbstractWordView(abstractWordView);
					break;
				}
			}
			mainView.addProtectedAbstractWordView(wordViewOne);
			mainView.addProtectedAbstractWordView(wordViewTwo);		
			// moveController.moveWord(wordViewOne,
			// wordViewOne.getPosition(),oldp);
			moveController.moveWord(wordViewOne, newp, oldp);
		}
		else {
			DisconnectController disconnectController = new DisconnectController(mainView, gameState);
			disconnectController.disconnect(wordViewOne, wordViewTwo);
			moveController.moveWord(wordViewOne, newp, oldp);
		}
		return true;
	}
}
