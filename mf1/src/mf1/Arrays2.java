package mf1;

import java.util.ArrayList;
import java.util.Random;

public class Arrays2 {

	public static void main(String[] args){
		if(args.length < 1){
			System.out.println("Too few parameters.");
			return;
		}
		
		Integer maxElements = Integer.parseInt(args[0]);
		ArrayList<Integer> i = new ArrayList<Integer>();
		
		Random rand = new Random();
		
		for(int j=0; j<maxElements; j++){
			i.add(rand.nextInt(100));
		}
		
		Arrays2.printTable(i);
	}
	
	private static void printTable(ArrayList<Integer> tab){
		
		for(Integer i: tab){
			System.out.println(i);
		}
		
	}
	
}
/*
 * Jakie s¹ zalety wykorzystywania tablicy dynamicznej? 
 * Mo¿emy nie znaæ iloœci elementów na starcie i rozszerzaæ j¹ w miarê potrzeb.
 */