package models.zusteller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ErsatzzustellerSuche {

    public String suchstring;
    private ArrayList<Ersatzzusteller> ersatzzustellerliste;
    private Connection conn;

    public ErsatzzustellerSuche() {
        this.ersatzzustellerliste = new ArrayList();
    }

    public ArrayList<Ersatzzusteller> liste() {
        ResultSet rs;

        try {
            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select mv.adrinfokey, ba.nachname, ba.vorname, TRIM(ba.kontroll_strasse) || ' ' || ba.hsnr || ba.hsnrzusatz, ba.kontroll_plz, ba.kontroll_ort, ba.kontroll_ortsteil, GiveGeburtstag(mv.adrinfokey), (CASE ba.set_adressart WHEN '19993K020834.0154wawi4:102:55:113' THEN 'M' WHEN '19993K020834.0155wawi4:102:55:113' THEN 'W' ELSE '?' END), mv.regionalschluessel, b.bezeichnung from mv_erweiterung as mv inner join basisadresse as ba on ba.adrinfokey = mv.adrinfokey INNER JOIN bezirk AS b ON b.regionalschluessel = mv.regionalschluessel");
            rs = stmt.executeQuery();

            while ( rs.next() ){
                Ersatzzusteller ez = new Ersatzzusteller();
                ez.adrinfokey = rs.getString(1);
                ez.nachname = rs.getString(2);
                ez.vorname = rs.getString(3);
                ez.strasse = rs.getString(4);
                ez.plz = rs.getString(5);
                ez.hauptort = rs.getString(6);
                ez.ortsteil = rs.getString(7);
                ez.geburtsdatum = rs.getDate(8);
                ez.geschlecht = rs.getString(9);
                ez.regionalschluessel = rs.getString(10);
                ez.bezirk = rs.getString(11);
                this.ersatzzustellerliste.add(ez);
            }

            rs.close();
            conn.close();
        }

        catch ( SQLException e ) {
            e.printStackTrace();
            System.exit(1);
        }

        return this.ersatzzustellerliste;

    }

    public ArrayList<Ersatzzusteller> ohneBezirk() {
        ResultSet rs;

        try {
            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select mv.adrinfokey, ba.nachname, ba.vorname, TRIM(ba.kontroll_strasse) || ' ' || ba.hsnr || ba.hsnrzusatz, ba.kontroll_plz, ba.kontroll_ort, ba.kontroll_ortsteil, GiveGeburtstag(mv.adrinfokey), (CASE ba.set_adressart WHEN '19993K020834.0154wawi4:102:55:113' THEN 'M' WHEN '19993K020834.0155wawi4:102:55:113' THEN 'W' ELSE '?' END) from mv_erweiterung as mv inner join basisadresse as ba on ba.adrinfokey = mv.adrinfokey where regionalschluessel IS NULL");
            rs = stmt.executeQuery();

            while ( rs.next() ){
                Ersatzzusteller ez = new Ersatzzusteller();
                ez.adrinfokey = rs.getString(1);
                ez.nachname = rs.getString(2);
                ez.vorname = rs.getString(3);
                ez.strasse = rs.getString(4);
                ez.plz = rs.getString(5);
                ez.hauptort = rs.getString(6);
                ez.ortsteil = rs.getString(7);
                ez.geburtsdatum = rs.getDate(8);
                ez.geschlecht = rs.getString(9);
                this.ersatzzustellerliste.add(ez);
            }

            rs.close();
            conn.close();
        }

        catch ( SQLException e ) {
            e.printStackTrace();
            System.exit(1);
        }

        return this.ersatzzustellerliste;

    }

} 