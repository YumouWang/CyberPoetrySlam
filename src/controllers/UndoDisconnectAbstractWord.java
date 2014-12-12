package controllers;

import models.AbstractWord;
import models.GameState;
import models.Position;
import views.AbstractWordView;
import views.AdjacencyType;
import views.ConnectionBox;
import views.MainView;
import views.WordView;

/**
 * This is implementation about undoDisconnect && RedoConnect;
 * @author xujian
 */
public class UndoDisconnectAbstractWord extends UndoMove{

	final MainView mainView;
	final GameState gameState;
	AbstractWordView wordViewOne;
	AbstractWordView wordViewTwo;
	AbstractWordView connectTarget;
	
	Position oldp;
	Position newp;
	Position targetPosition;
	
	/**
	 * 
	 * @param wordViewOne This is the Abstractword to disconnect --> WordView
	 * @param wordViewTwo This is the one who disconnects --> RowView
	 * @param mainView
	 */
	public UndoDisconnectAbstractWord(Position targetPosition, Position oldp, Position newp,
			AbstractWordView wordViewOne,AbstractWordView wordViewTwo, MainView mainView,GameState gameState){
		this.targetPosition = targetPosition;
		this.mainView = mainView;
		this.gameState = gameState;
		this.wordViewOne = wordViewOne;
		this.wordViewTwo = wordViewTwo;
		this.oldp = oldp;
		this.newp = newp;
	}
	
	@Override
	public boolean execute() {
		//System.out.println("NJBJKBJK" + " " + connectTarget.getWord().getValue());
		//System.out.println("NJBJKBJK" + " " + wordViewOne.getWord().getValue());
		MoveWordController moveController = new MoveWordController(
				mainView, gameState);
		//if ((wordViewOne instanceof WordView) && (connectTarget instanceof WordView)) {
			AbstractWord wordOne = wordViewOne.getWord();
			AbstractWord wordTwo = connectTarget.getWord();
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
			mainView.addProtectedAbstractWordView(connectTarget);		
			// moveController.moveWord(wordViewOne,
			// wordViewOne.getPosition(),oldp);
			moveController.moveWord(wordViewOne, oldp, newp);
			//moveController.moveWord(wordViewOne, newp, oldp);
		/*}
		else {
			DisconnectController disconnectController = new DisconnectController(mainView, gameState);
			disconnectController.disconnect(wordViewOne, connectTarget);
			//moveController.moveWord(wordViewOne, newp, oldp);
			moveController.moveWord(wordViewOne, oldp, newp);
		}*/
		return true;
	}

	
	@Override
	public boolean undo() {
		
		Position test = new Position(oldp.getX(), oldp.getY());
		if (test.equals(targetPosition)){
			test.setX(oldp.getX() - 3);
			test.setY(oldp.getY() - 3);
		}
		else{
			test.setX(oldp.getX() + 3);
			test.setY(oldp.getY() + 3);
		}
		MoveWordController moveController = new MoveWordController(mainView, gameState);
        moveController.moveWord(wordViewOne,newp,test);		
        //AbstractWordView connectTarget = null;
        for (AbstractWordView word : mainView.getProtectedWordView()) {
            if (!word.equals(wordViewOne)) {
                AdjacencyType adjacencyType = wordViewOne.isAdjacentTo(word);
                if (adjacencyType != AdjacencyType.NOT_ADJACENT) {
                    this.connectTarget = word;
                }
            }
        }        
        ConnectController controller = new ConnectController(mainView, gameState);
        controller.connect(wordViewOne, this.connectTarget);  
		return true;
	}
}
