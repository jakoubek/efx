package models;

import java.util.Date;

public class Aktionstag {
 
    public Date datum;
    public Integer anzahl;
 
    public String getFullname() {
        return this.datum + " (" + this.anzahl + ")";
    }

}
