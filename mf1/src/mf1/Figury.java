package mf1;

public class Figury {
	
	public static void main(String[] args){
		Figura f0 = new Kwadrat();
		Figura f1 = new Trojkat();
		Kwadrat f2 = new Kwadrat();
		Trojkat t1 = new Trojkat();

		System.out.println(f0.getClass()+" "+f0.iloscKrawedzi());
		System.out.println(f1.getClass()+" "+f1.iloscKrawedzi());
		System.out.println(f2.getClass()+" "+f2.iloscKrawedzi());
		System.out.println(t1.getClass()+" "+t1.iloscKrawedzi());
		
		System.out.println(f0.getClass()+" "+f0.iloscWierzcholkow());
		System.out.println(f1.getClass()+" "+f1.iloscWierzcholkow());
		System.out.println(f2.getClass()+" "+f2.iloscWierzcholkow());
		System.out.println(t1.getClass()+" "+t1.iloscWierzcholkow());
		
	}
	
}
