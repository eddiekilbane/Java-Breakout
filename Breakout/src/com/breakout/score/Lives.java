package com.breakout.score;

public class Lives {
	
	public static final int NUMBER_OF_LIVES = 3;
	
	private int REMAINING_LIVES = 0;
	
	public Lives(){
		
	}

	public int getRemainingLives() {
		return REMAINING_LIVES;
	}

	public void setRemainingLives(int remaining) {
		REMAINING_LIVES = remaining;
	}

}
