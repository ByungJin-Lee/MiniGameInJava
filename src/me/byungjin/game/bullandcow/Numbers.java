package me.byungjin.game.bullandcow;

import java.util.HashSet;
import java.util.Set;

public class Numbers {	
	String value;	
	int len;
	
	private Set<Character> valueSet;
	
	public Numbers(String val) {
		value = val;
		len = val.length();
		valueSet = new HashSet<Character>();
		for(char c : val.toCharArray()) {
			valueSet.add(c);
		}
	}
	
	public static boolean isVaild(String val) {
		Set<Character> vSets = new HashSet<>();
		for(char c : val.toCharArray()) {
			if(!vSets.add(c))
				return false;
		}				
		return true;
	}
	
	public NumberResult judgeNumbers(String val) {
		int count_ball = 0;
		int count_strike = 0;
		
		int index = 0;
		
		for(char c : val.toCharArray()) {			
			if(valueSet.contains(c)) {
				//Check ball or strikes
				if(value.charAt(index) == c)
					count_strike++;
				else
					count_ball++;
			}
			index++;
		}
		return new NumberResult(count_ball, count_strike);
	}
	
	public class NumberResult{
		int balls, strlikes;
		public NumberResult(int b, int s) {
			balls = b;
			strlikes = s;
		}
		public int getBall() {
			return balls;
		}
		public int getStrlike() {
			return strlikes;
		}
		@Override
		public String toString() {		
			return balls + " " + strlikes;
		}
	}
}
