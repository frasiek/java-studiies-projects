package mf1;

public class Kwadrat implements Figura{
	protected int krawedzie = 0;
	protected int wierzcholki = 0;
	
	public Kwadrat(){
		this.krawedzie = 4;
		this.wierzcholki = 4;
	}
	
	public int iloscKrawedzi(){
		return this.krawedzie;
	}
	
	public int iloscWierzcholkow(){
		return this.wierzcholki;
	}
}
