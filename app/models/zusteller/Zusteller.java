package models.zusteller;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Date;

import models.zuordnung.Zuordnung;
import models.zuordnung.Zuordnung_Suche;

public class Zusteller {

    public int id;
    public String adrinfokey;
    public String personalnr;
    public String nachname;
    public String vorname;
    public String strasse;
    public String plz;
    public String hauptort;
    public String ortsteil;
    public Date geburtsdatum;
    public String geschlecht;
    public String aktiv;
    public String betreuer;
    public Date eintritt;
    public Date austritt;
    public String urlaubsanspruch;
    public String einstellungsverbot;
    public ArrayList<Zuordnung> zuordnungen;
    public ArrayList<TestlaufEintrag> probleme;
    private Connection conn;

    public Zusteller(int id) {
        this.id = id;
        ResultSet rs;

        try {

            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT ze.farbcode, ze.adrinfokey, ba.firmenkey1, ba.nachname, ba.vorname, TRIM(ba.kontroll_strasse) || ' ' || ba.hsnr || ba.hsnrzusatz, ba.kontroll_plz, ba.kontroll_ort, ba.kontroll_ortsteil, ze.urlaub_anspruch, ze.einstellungsverbot, GiveGeburtstag(ze.adrinfokey), ze.aktiv, (CASE ba.set_adressart WHEN '19993K020834.0154wawi4:102:55:113' THEN 'M' WHEN '19993K020834.0155wawi4:102:55:113' THEN 'W' ELSE '?' END), (SELECT firmenkey1 FROM basisadresse WHERE adrinfokey = ze.betreuerkey), mea.eintritt, mea.austritt FROM zusteller_erweiter AS ze INNER JOIN basisadresse AS ba ON ba.adrinfokey = ze.adrinfokey LEFT OUTER JOIN mitarbeitereintrittaustritt AS mea ON mea.adrinfokey = ze.adrinfokey AND mea.id = GiveId4MitarbeitereintrittaustrittLast(ze.adrinfokey) WHERE ze.farbcode = ?");
            stmt.setInt(1, this.id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt(1);
                this.adrinfokey = rs.getString(2);
                this.personalnr = rs.getString(3);
                this.nachname = rs.getString(4);
                this.vorname = rs.getString(5);
                this.strasse = rs.getString(6);
                this.plz = rs.getString(7);
                this.hauptort = rs.getString(8);
                this.ortsteil = rs.getString(9);
                this.urlaubsanspruch = rs.getString(10);
                this.einstellungsverbot = rs.getString(11);
                this.geburtsdatum = rs.getDate(12);
                this.aktiv = rs.getString(13);
                this.geschlecht = rs.getString(14);
                this.betreuer = rs.getString(15);
                this.eintritt = rs.getDate(16);
                this.austritt = rs.getDate(17);
                Zuordnung_Suche zs = new Zuordnung_Suche();
                this.zuordnungen = zs.ZuordnungenFuerZusteller(this.adrinfokey);
                Testlauf tl = new Testlauf();
                this.probleme = tl.EintraegeFuerZusteller(this.adrinfokey);
            }

            rs.close();
            conn.close();
        }

        catch ( SQLException e ) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public Zusteller(String adrinfokey) {
        ResultSet rs;

        try {

            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT ze.farbcode, ze.adrinfokey, ba.firmenkey1, ba.nachname, ba.vorname, TRIM(ba.kontroll_strasse) || ' ' || ba.hsnr || ba.hsnrzusatz, ba.kontroll_plz, ba.kontroll_ort, ba.kontroll_ortsteil, ze.urlaub_anspruch, ze.einstellungsverbot, GiveGeburtstag(ze.adrinfokey), ze.aktiv, (CASE ba.set_adressart WHEN '19993K020834.0154wawi4:102:55:113' THEN 'M' WHEN '19993K020834.0155wawi4:102:55:113' THEN 'W' ELSE '?' END), (SELECT firmenkey1 FROM basisadresse WHERE adrinfokey = ze.betreuerkey), mea.eintritt, mea.austritt FROM zusteller_erweiter AS ze INNER JOIN basisadresse AS ba ON ba.adrinfokey = ze.adrinfokey LEFT OUTER JOIN mitarbeitereintrittaustritt AS mea ON mea.adrinfokey = ze.adrinfokey AND mea.id = GiveId4MitarbeitereintrittaustrittLast(ze.adrinfokey) WHERE ze.adrinfokey = ?");
            stmt.setString(1, adrinfokey);
            rs = stmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt(1);
                this.adrinfokey = rs.getString(2);
                this.personalnr = rs.getString(3);
                this.nachname = rs.getString(4);
                this.vorname = rs.getString(5);
                this.strasse = rs.getString(6);
                this.plz = rs.getString(7);
                this.hauptort = rs.getString(8);
                this.ortsteil = rs.getString(9);
                this.urlaubsanspruch = rs.getString(10);
                this.einstellungsverbot = rs.getString(11);
                this.geburtsdatum = rs.getDate(12);
                this.aktiv = rs.getString(13);
                this.geschlecht = rs.getString(14);
                this.betreuer = rs.getString(15);
                this.eintritt = rs.getDate(16);
                this.austritt = rs.getDate(17);
                Zuordnung_Suche zs = new Zuordnung_Suche();
                this.zuordnungen = zs.ZuordnungenFuerZusteller(this.adrinfokey);
                Testlauf tl = new Testlauf();
                this.probleme = tl.EintraegeFuerZusteller(this.adrinfokey);
            }

            rs.close();
            conn.close();
        }

        catch ( SQLException e ) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public String toString() {
        return this.nachname + " " + this.vorname;
    }

} 