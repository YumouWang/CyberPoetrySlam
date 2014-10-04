package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * The swap model class
 *
 * Created by Yumou on 10/3/2014.
 */
public class Swap extends Observable {
	int num;
	List<WordType> giveType;
    List<String> giveValue;
    List<WordType> getType;
    List<String> getValue;
	SwapStatus status;
	
	public Swap() {
        num = 0;
        giveType = new ArrayList<WordType>();
        giveValue = new ArrayList<String>();
        getType = new ArrayList<WordType>();
        getValue = new ArrayList<String>();
		status = SwapStatus.COMPLETED;
	}
	
	public void updateSwapStatus(SwapStatus status) {
		this.status = status;
	}
	
	public void updateCurrentSwap(int num, List<WordType> giveType, List<String> giveValue, List<WordType> getType, List<String> getValue) {
		this.num = num;
        this.giveType.addAll(giveType);
        this.giveValue.addAll(giveValue);
        this.getType.addAll(getType);
        this.getValue.addAll(getValue);
	}

}
