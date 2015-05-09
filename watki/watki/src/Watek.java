
public class Watek extends Thread{

    protected Integer nr; //pole do przechowania nr watku

    public Watek(Integer nr) { //konstruktor wpisujacy nr watku do pola w obiekcie
        this.nr = nr; //przypisanie nr watku
    }
    
    
    public void run() {
        while(true){ //nieskonczona petla wypisujaca co to za watek
            System.out.println("Jestem watek "+nr.toString()); //wypisanie na ekran informacji Jestem watek i nr watku
        }
    }
    
    
    
}
