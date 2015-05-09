/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternfinder;

import java.util.ArrayList;

/**
 *
 * @author frasiek
 */
public class PatternFinder {

    private static long seed = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Worker> list = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            Worker w = new Worker();
            list.add(w);
            w.start();
        }
    }

    public static synchronized long getSeed() {
        if (PatternFinder.seed % 10000000 == 0) {
            System.out.println("Testing seed " + Long.toString(PatternFinder.seed));
        }
        return PatternFinder.seed++;
    }

}
