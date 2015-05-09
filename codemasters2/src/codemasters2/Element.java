/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codemasters2;

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
    
    public Element(Integer lat, Integer lng){
        this.lat = lat;
        this.lng = lng;
    }
    
    public Element(Element el, Integer lat, Integer lng){
        this.lat = el.getLat() + lat;
        this.lng = el.getLng() + lng;
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
    
    public String toString(){
        String lat =  this.lat.toString().substring(0, 2) + "." + this.lat.toString().substring(2, this.lat.toString().length());
        String lng = this.lng.toString().substring(0, 2) + "." + this.lng.toString().substring(2, this.lng.toString().length());
        
        if(this.lat<0){
            lat =  this.lat.toString().substring(0, 3) + "." + this.lat.toString().substring(3, this.lat.toString().length());
        }
        if(this.lng<0){
            lng = this.lng.toString().substring(0, 3) + "." + this.lng.toString().substring(3, this.lng.toString().length());
        }
        
        return lat + "," + lng + "\r\n";
    }

}
