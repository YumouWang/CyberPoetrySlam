package views;

import controllers.AbstractWordViewVisitor;
import models.Poem;
import models.Position;
import models.Row;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A view to represent the poem model
 *
 * @author Nathan
 * @author YangWang
 * @version 10/10/2014
 */
public class PoemView extends AbstractWordView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4290728328082346968L;
	List<RowView> rowViews;
	List<Integer> rowoffset;
	/**
	 * Constructor
	 *
	 * @param poem
	 *            The poem that this view represents
	 * @param position
	 *            The position of this poem
	 */
	public PoemView(Poem poem, Position position, MainView mainView) {
		super(poem, position);
		List<Row> rows = poem.getRows();
		rowViews = new ArrayList<RowView>();
		rowoffset = new ArrayList<Integer>();
		
		// Find all wordViews
		for (Row row : rows) {
			// Find the rowView representing each row
			RowView view = (RowView) mainView.getProtectedAbstractWordById(row
					.getId());
			if (view == null) {
				// If a rowView doesn't exist for this row,
				// then create one at the position of the first word in the row
				long id = row.getWords().get(0).getId();
				Position rowPosition = mainView
						.getProtectedAbstractWordById(id).getPosition();
				view = new RowView(row, rowPosition, mainView);
			}
			rowViews.add(view);
			rowoffset.add(0);
		}

		
		calculateDimensions();
		moveTo(position);
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
		boolean successful = true;
		// Move all words to the appropriate locations
		int currentHeightOffset = 0;
		int currentOffset = 0;
		for (int i = 0; i < rowViews.size() ;i++) {
			RowView view = rowViews.get(i);
			currentOffset  = rowoffset.get(i);
			successful = successful
					&& view.moveTo(new Position(position.getX()+currentOffset, position
							.getY() + currentHeightOffset));
			currentHeightOffset += view.height;
		}
		return successful;
	}

	/**
	 * Sets the background color of this word view
	 * 
	 * @param color
	 *            The color to set the background to
	 */
	public void setBackground(Color color) {
		for (RowView view : rowViews) {
			view.setBackground(color);
		}
	}

	public void addRow(RowView rowView) {
		rowViews.add(rowView);
		rowoffset.add(0);
		moveTo(rowViews.get(0).getPosition());
		calculateDimensions();
	}

	public void addRowToTop(RowView rowView) {
		rowViews.add(0, rowView);
		rowoffset.add(0,0);
		moveTo(rowViews.get(0).getPosition());
		calculateDimensions();
	}

	public void addPoem(PoemView poemView) {
		rowViews.addAll(poemView.getRowViews());
		rowoffset.addAll(poemView.getRowOffset());
		moveTo(rowViews.get(0).getPosition());
		calculateDimensions();
	}

	public boolean removeRowView(RowView otherRow) {
		int index = rowViews.indexOf(otherRow);
		boolean successful = false;
		// If the word is first or last, remove it and we're done
		if (index == 0 || index == rowViews.size() - 1) {
			successful = rowViews.remove(otherRow);
			rowoffset.remove(index);
		}
		calculateDimensions();
		return successful;
	}

	public RowView getEmptyRowView() {
		RowView result = null;
		for (RowView rowView : rowViews) {
			if (rowView.getWordViews().size() == 0) {
				result = rowView;
			}
		}
		return result;
	}

	/**
	 * Disconnects a word from one of the rows in this poem if the word is an
	 * edge word and is in the poem
	 * 
	 * @param wordView
	 *            The word to disconnect
	 * @return Returns whether the disconnect was successful. Returns false if
	 *         the word is not an edge word or is not in the poem
	 */
	public boolean removeEdgeWordView(WordView wordView) {
		boolean successful = false;
		for (RowView rowView : rowViews) {
			for (WordView rowWordView : rowView.getWordViews()) {
				if (wordView.equals(rowWordView)) {
					successful = rowView.removeWordView(rowWordView);
					break;
				}
			}
			if (successful) {
				break;
			}
		}
		calculateDimensions();
		return successful;
	}

	public List<RowView> getRowViews() {
		return rowViews;
	}

	public List<Integer> getRowOffset() {
		return rowoffset;
	}
	
	public void shiftRow(AbstractWordView  row, int shiftoffset) {
		int index = rowViews.indexOf(row);
		int offset = rowoffset.get(index);
		
		rowoffset.set(index,offset+shiftoffset);
		moveTo(position);
	}
	
	private void calculateDimensions() {
		// Find all wordViews, and calculate total Width and height
		if (rowViews.size() < 1) {
			setSize(0, 0);
			return;
		}
		position = rowViews.get(0).getPosition();
		int totalHeight = 0;
		int widest = 0;
		int min = getPosition().getX();
		int max = getPosition().getX() + rowViews.get(0).getWidth();
		for(RowView row:rowViews) {
			if(row.getPosition().getX() < min) {
				min = row.getPosition().getX();
			}
			if(row.getPosition().getX() + row.getWidth()> max) {
				max = row.getPosition().getX()+ row.getWidth();
			}
		}
		widest = max - min;
		for (int i = 0;i < rowViews.size(); i ++) {
			RowView row = rowViews.get(i);
			
			totalHeight += row.height;
		}
		
		setSize(widest, totalHeight);
		System.out.println(totalHeight);
		System.out.println(widest);
	}

	public AbstractWordView getSelectedElement(ConnectionBox box) {
		AbstractWordView selected = null;
		for (RowView row : rowViews) {
			AbstractWordView selectedElement = row.getSelectedElement(box);
			if (selectedElement != null) {
				// If we have not yet selected a word or row in this poem,
				// The selected item is the currently selected element
				if (selected == null) {
					selected = selectedElement;
				} else {
					// If we have already selected a word or row from this poem,
					// Select the entire poem (Cannot select more than one word
					// without selecting the whole poem)
					selected = this;
					break;
				}
			}
		}
		return selected;
	}

	public boolean contains(AbstractWordView otherWord) {
		boolean containsWord = this.equals(otherWord);
		if (!containsWord) {
			for (RowView row : rowViews) {
				if (row.contains(otherWord)) {
					containsWord = true;
					break;
				}
			}
		}
		return containsWord;
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

	public Poem getWord() {
		return (Poem) word;
	}
}
