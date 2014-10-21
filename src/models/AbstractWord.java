package models;

import java.util.UUID;

/**
 * The AbstractWord model class. Acts as a base class for Words, Rows, and Poems.
 * All other objects refer to a collection of AbstractWords and don't distinguish
 * between Words, Rows, and Poems.
 */
public abstract class AbstractWord {

    long id;

    /**
     * Constructor
     */
    public AbstractWord() {
        this.id = UUID.randomUUID().getMostSignificantBits();
    }

    /**
     * getValue returns the value of the AbstractWord as a String
     *
     * @return String The value of the AbstractWord
     */
    public abstract String getValue();

    /**
     * Check whether this abstract word is or contains another given abstract word
     * @param otherWord The word to check for
     * @return boolean Returns whether the other word is in this one
     */
    public abstract boolean contains(AbstractWord otherWord);

	/**
	 * @return long The id
	 */
	public long getId() {
		return id;
	}
}
