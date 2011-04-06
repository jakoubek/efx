package models.zusteller;

import java.util.Date;

public class Ersatzzusteller {

    public String adrinfokey;
    public String nachname;
    public String vorname;
    public String strasse;
    public String plz;
    public String hauptort;
    public String ortsteil;
    public Date geburtsdatum;
    public String geschlecht;
    public String regionalschluessel;
    public String bezirk;

    public String toString() {
        return this.nachname + " " + this.vorname;
    }

}