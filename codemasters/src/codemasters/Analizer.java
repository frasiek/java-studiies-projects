/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codemasters;

import java.util.ArrayList;

/**
 *
 * @author frasiek
 */
public class Analizer {

    private ArrayList<Element> list = new ArrayList<>();
    private ArrayList<Integer> types = new ArrayList<>();

    public Analizer(ArrayList<Element> list) {
        this.list = list;
    }

    public void analize() {
        this.countElements();
        this.findLargestSet();
    }

    private void countElements() {
        boolean first = true;
        Element prev = null;
        for (Element el : list) {
            if (first) {
                prev = el;
                first = false;
                continue;
            }

            this.types.add(el.countDifference(prev));

            prev = el;
        }
    }

    private void findLargestSet() {
        ArrayList<Integer> subset = new ArrayList<>();
        int startChars = 1;
        for(int i = 0; i < startChars; i++){
            subset.add(types.get(i));
        }
        boolean ok = true;
        Integer index = startChars;
        do {
            ok = true;
            
            for (int i = 0; i < types.size(); i += subset.size()) {
                try {
                    Integer max = i + subset.size();
                    Integer toIndex = subset.size();
                    if(max > types.size()){
                        max = types.size();
                        toIndex = max-i;
                    }
                    if (subset.subList(0, toIndex).equals(types.subList(i, max)) == false) {
                        ok = false;
                        break;
                    }

                } catch (IndexOutOfBoundsException e) {
                    ok = false;
                    break;
                }
            }
            if (ok == false) {
                try {
                    subset.add(types.get(index));
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
                index++;
            } else {
                if(subset.size() == types.size()){
                    ok = false;
                    break;
                }
                for (Integer s : subset) {
                    System.out.println(s.toString());
                }
                break;
            }

        } while (ok != true);
        if (!ok) {
            System.out.println("\r\nNot found;");
        }

    }
}
