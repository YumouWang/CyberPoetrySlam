package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * The swap model class. Encapsulates data about the current swap.
 *
 * @author Yumou
 * @version 10/3/2014
 */
public class Swap extends Observable {
	int num;
	List<WordType> giveType;
    List<String> giveValue;
    List<WordType> getType;
    List<String> getValue;
	SwapStatus status;

    /**
     * Constructor
     */
	public Swap() {
        num = 0;
        giveType = new ArrayList<WordType>();
        giveValue = new ArrayList<String>();
        getType = new ArrayList<WordType>();
        getValue = new ArrayList<String>();
		status = SwapStatus.COMPLETED;
	}

    /**
     * Updates the status of the swap
     * @param status The status to update to
     */
	public void updateSwapStatus(SwapStatus status) {
		this.status = status;
	}

    /**
     * Updates the current swap to the specified values
     * @param num The number of words to swap
     * @param giveType A list of length num of the types of words we would like to give in this swap
     * @param giveValue A list of length num of the words we would like to give in this swap
     * @param getType A list of length num of the types of words we would like to get in this swap
     * @param getValue A list of length num of the words we would like to get in this swap
     */
	public void updateCurrentSwap(int num, List<WordType> giveType, List<String> giveValue, List<WordType> getType, List<String> getValue) {
		this.num = num;
        this.giveType.addAll(giveType);
        this.giveValue.addAll(giveValue);
        this.getType.addAll(getType);
        this.getValue.addAll(getValue);
	}

}
