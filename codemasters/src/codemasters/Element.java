/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codemasters;

/**
 *
 * @author frasiek
 */
public class Element {

    private Integer index;
    private String line;
    private Integer lat;
    private Integer lng;
    
    private Integer latDiff;
    private Integer lngDiff;
    
    private int type;
    
    private static int FIRST_UP = 0;
    private static int FIRST_DOWN = 1;
    private static int SECOND_UP = 2;
    private static int SECOND_DOWN = 3;

    public Element(Integer index, String line) {
        String[] tmp = line.split(",");
        lat = Integer.parseInt(tmp[0].replace(".", ""));
        lng = Integer.parseInt(tmp[1].replace(".", ""));
    }

    public Integer getLat() {
        return lat;
    }

    public Integer getLng() {
        return lng;
    }

    public Integer subLat(Integer lat) {
        return this.latDiff = this.lat - lat;
    }

    public Integer subLng(Integer lng) {
        return this.lngDiff = this.lng - lng;
    }

    public Integer getLatDiff() {
        return latDiff;
    }

    public Integer getLngDiff() {
        return lngDiff;
    }
    
    public int countDifference(Element prev){
        this.subLat(prev.getLat());
        this.subLng(prev.getLng());
        
        if(latDiff > 0){
            type = FIRST_UP;
        }
        if(latDiff < 0){
            type = FIRST_DOWN;
        }
        if(lngDiff > 0){
            type = SECOND_UP;
        }
        if(lngDiff < 0){
            type = SECOND_DOWN;
        }
        return type;
    }

}
