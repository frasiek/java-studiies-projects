package mf1;

public class SimpleTypes {
	
	public static void main(String[] args) {
		SimpleTypes st = new SimpleTypes();
		st.simple_types();
		st.strings();
		st.loops(args);
		st.printArithmeticWords(3);
		st.printArithmeticWords(7);
		
		
	}
	
	private void simple_types(){
		System.out.println("---TEST--- 1");
		
		int a = 5;
		Integer b = new Integer(10);

		System.out.println(a+b);
		System.out.println(a-b);
		System.out.println(b/a);
		System.out.println(a*b);
	}
	
	private void strings(){
		System.out.println("---TEST--- 2");
		
		String s = "15";
		Integer i = new Integer(100);
		
		System.out.println(Integer.parseInt(s) + i);
		System.out.println(Integer.parseInt(s) - i);
		System.out.println(Integer.parseInt(s) * i);
		System.out.println(Integer.parseInt(s) / i);
	}
	
	private void loops(String[] arguments){
		System.out.println("---TEST--- 3");
		
		for(String arg: arguments){
			System.out.println(arg);
		}
	}
	
	private void printArithmeticWords(int count){
		System.out.println("---TEST--- 4");
		int i = 0;
		
		for(int j = 0; j<count; j++){
			System.out.print(i);
			if(j+1 < count){
				System.out.print(", ");
			}
			i+=3;
		}
		
		System.out.println();
		
	}

	
}
