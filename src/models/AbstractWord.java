package models;

import java.util.UUID;

public abstract class AbstractWord {

    long id;

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
	 * @return the id
	 */
	public long getId() {
		return id;
	}
    
}
