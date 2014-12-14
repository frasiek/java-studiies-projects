package mf1;

import java.util.Random;

public class Arrays1 {

	public static void main(String[] args){
		if(args.length < 1){
			System.out.println("Too few parameters.");
			return;
		}
		
		Integer maxElements = Integer.parseInt(args[0]);
		Integer[] i = new Integer[ maxElements ];
		
		Random rand = new Random();
		
		for(int j=0; j<maxElements; j++){
			i[j] = rand.nextInt(100);
		}
		
		Arrays1.printTable(i);
	}
	
	private static void printTable(Integer [] tab){
		
		for(Integer i: tab){
			System.out.println(i);
		}
		
	}
	
}
