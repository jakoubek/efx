package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
public class Betreuer {
 
    public String adrinfokey;
    public String nachname;
    public String vorname;
    public String userid;

    public String getFullname() {
        if (this.vorname != null) {
            return this.nachname + ", " + this.vorname;
        } else {
            return this.nachname;
        }
    }

}
