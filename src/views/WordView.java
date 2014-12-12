package views;

import controllers.AbstractWordViewVisitor;
import models.Position;
import models.Word;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * A view for representing words on the screen
 *
 * @author Nathan
 * @version 10/9/2014
 */
public class WordView extends AbstractWordView implements Serializable,Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2583242419164210132L;
	public JLabel label;

	/**
	 * Constructor
	 *
	 * @param word
	 *            The word that this view represents
	 * @param position
	 *            The position of this word
	 */
	public WordView(Word word, Position position) {
		super(word, position);
		setSize(word.getValue().length() * 10, 20);
		label = new JLabel(word.getValue(), SwingConstants.CENTER);
		updateView();
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		label.setBackground(Color.LIGHT_GRAY);
		label.setOpaque(true);
	}

	/**
	 * Sets the background color of this word view
	 * 
	 * @param color
	 *            The color to set the background to
	 */
	public void setBackground(Color color) {
		label.setBackground(color);
	}

	/**
	 * Updates the view
	 */
	private void updateView() {
		label.setBounds(position.getX(), position.getY(), width, height);
	}

	/**
	 * Moves the word to the specified position
	 * 
	 * @param toPosition
	 *            The position to move to
	 * @return Returns whether the move was successful
	 */
	public boolean moveTo(Position toPosition) {
		super.position = toPosition;
		updateView();
		return true;
	}

	public AbstractWordView getSelectedElement(ConnectionBox box) {
		AbstractWordView selected = null;
		if (isOverlapping(box)) {
			selected = this;
		}
		return selected;
	}

	public boolean contains(AbstractWordView otherWord) {
		return this.equals(otherWord);
	}

	public boolean acceptVisitor(AbstractWordViewVisitor visitor,
			AbstractWordView otherView) {
		return otherView.acceptVisitor(visitor, this);
	}

	public boolean acceptVisitor(AbstractWordViewVisitor visitor,
			WordView wordView) {
		return visitor.visit(wordView, this);
	}

	public boolean acceptVisitor(AbstractWordViewVisitor visitor,
			RowView rowView) {
		return visitor.visit(rowView, this);
	}

	public boolean acceptVisitor(AbstractWordViewVisitor visitor,
			PoemView poemView) {
		return visitor.visit(poemView, this);
	}

	public Word getWord() {
		return (Word) word;
	}
	
	@Override
	public Object clone() {
		WordView wordView = null;
		wordView = (WordView) super.clone();
		return wordView;
	}
}
