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
    
    

}
