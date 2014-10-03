package model;

import java.util.Observable;

/**
 * The swap model class
 *
 * Created by Yumou on 10/3/2014.
 */
public class Swap extends Observable {
	int num;
	WordType[] giveType;
	String[] giveValue;
	WordType[] getType;
	String[] getValue;
	SwapStatus status;
	
	public void Swap() {
		
	}
	
	public SwapStatus updataSwapStatus(SwapStatus status) {
		return status;
	}
	
	public void updataCurrentSwap(
	int num, WordType[] giveType, String[] giveValue, WordType[] getType, String[] getValue) 
	{
		
	}

}
