package mf1;

import java.util.Random;

public class ExceptionTest {
	
	public static void main(String[] args){
		Random rand = new Random();
		int throwNo = rand.nextInt(100);
		
		try{
			for(int i = 1; i<=100; i++){
				if(i == throwNo){
					throw new MainException();
				}
				System.out.println(i);
			}
		} catch (MainException e){
			
		} finally {
			System.out.println("Sprz¹tam...");
		}
		
	}
	
	
}

final class MainException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4976960713599091110L;

}
