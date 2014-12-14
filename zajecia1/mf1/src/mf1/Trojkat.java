package mf1;

public class Trojkat implements Figura{
	protected int krawedzie = 0;
	protected int wierzcholki = 0;
	
	public Trojkat(){
		this.krawedzie = 3;
		this.wierzcholki = 3;
	}
	
	public int iloscKrawedzi(){
		return this.krawedzie;
	}
	
	public int iloscWierzcholkow(){
		return this.wierzcholki;
	}
	
}
