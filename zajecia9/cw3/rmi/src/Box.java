
import java.io.Serializable;

public class Box implements Serializable {

    private int value = 0;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
