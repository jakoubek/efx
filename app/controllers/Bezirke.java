package controllers;

import play.*;
import play.mvc.*;
import play.cache.Cache;

import models.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;

public class Bezirke extends Controller {

    public static void listOffene(String betreuer, Date aktionstag) {

        models.touren.OffeneBezirke offenebezirke = new models.touren.OffeneBezirke();

        models.Betreuers bl = new models.Betreuers();
        ArrayList<models.Betreuer> betreuerliste = Cache.get("betreuerliste", ArrayList.class);
        if(betreuerliste == null) {
            betreuerliste = bl.getBetreuerWithId();
            Cache.set("betreuerliste", betreuerliste, "30mn");
        }
        ArrayList<models.Aktionstag> aktionstage = new ArrayList();

    if (betreuer == null) {

        render(betreuerliste);

    } else if (betreuer != null && aktionstag == null) {

        /*Connection conn;
        ResultSet rs;

        try {

            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT t.aktionstag, COUNT(t.id) FROM touren2011 AS t INNER JOIN bezirk AS b ON b.regionalschluessel = t.bezirk WHERE t.aktionstag >= ( CURRENT DATE - 30 ) AND t.zusteller IS NULL AND b.code1 = ? GROUP BY t.aktionstag ORDER BY t.aktionstag");
            stmt.setString(1, betreuer);
            rs = stmt.executeQuery();

            while ( rs.next() ){
                models.Aktionstag tag = new models.Aktionstag();
                tag.aktionstag = rs.getDate(1);
                tag.anzahl = rs.getInt(2);
                aktionstage.add(tag);
            }

            rs.close();
            conn.close();
        }

        catch ( SQLException e ) {
            e.printStackTrace();
            System.exit(1);
        }   */

        aktionstage = Cache.get("offenebezirke_aktionstage_" + betreuer, ArrayList.class);
        if (aktionstage == null) {
            aktionstage = offenebezirke.getAktionstageForBetreuer(betreuer);
            Cache.set("offenebezirke_aktionstage_" + betreuer, aktionstage, "30mn");
        }

        render(betreuerliste, betreuer, aktionstage);

    } else {

        Connection conn;
        ResultSet rs;
        ArrayList<models.TourBezirk> bezirke = new ArrayList();

        try {

            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT t.id, t.aktionstag, t.bezirk, b.bezeichnung, ISNULL(b.code1, '<kein>'), (SELECT COUNT(*) FROM tourenposi4posi2011 WHERE posi = t.id) FROM touren2011 AS t INNER JOIN bezirk AS b ON b.regionalschluessel = t.bezirk WHERE t.aktionstag >= ( CURRENT DATE - 30 ) AND t.zusteller IS NULL AND b.code1 = ? AND t.aktionstag = ? ORDER BY t.aktionstag, b.bezeichnung");
            stmt.setString(1, betreuer);
//            stmt.setString(2, aktionstag);
            stmt.setString(2, aktionstag.toString());
            rs = stmt.executeQuery();

            while ( rs.next() ){
                TourBezirk bezirk = new TourBezirk();
                bezirk.tourposid = rs.getString(1);
                bezirk.aktionstag = rs.getDate(2);
                bezirk.regionalschluessel = rs.getString(3);
                bezirk.bezirk = rs.getString(4);
                bezirk.betreuer = rs.getString(5);
                bezirk.anzahlWuerfe = rs.getInt(6);
                bezirke.add(bezirk);
            }

            rs.close();
            conn.close();
        }

        catch ( SQLException e ){
            e.printStackTrace();
            System.exit(1);
        }   

            aktionstage = Cache.get("offenebezirke_aktionstage_" + betreuer, ArrayList.class);
            if (aktionstage == null) {
                aktionstage = offenebezirke.getAktionstageForBetreuer(betreuer);
                Cache.set("offenebezirke_aktionstage_" + betreuer, aktionstage, "30mn");
            }

            render(betreuerliste, betreuer, aktionstage, aktionstag, bezirke);
        }

    }

}
