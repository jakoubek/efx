package controllers;

import play.*;
import play.mvc.*;
import play.cache.Cache;

import static play.modules.pdf.PDF.*;

import models.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.Date;

@With(Secure.class)
public class Bezirke extends Controller {

    public static void listOffene(String betreuer, String action) {

        models.touren.OffeneBezirke offenebezirke = new models.touren.OffeneBezirke();

        models.Betreuers bl = new models.Betreuers();
        ArrayList<models.Betreuer> betreuerliste = Cache.get("betreuerliste", ArrayList.class);
        if(betreuerliste == null) {
            betreuerliste = bl.getBetreuerWithId();
            Cache.set("betreuerliste", betreuerliste, "30mn");
        }
        ArrayList<models.Aktionstag> aktionstage = new ArrayList();

    if (betreuer == null) {

        User user = new User(Security.connected());
        if (user.isBetreuer) {
            Bezirke.listOffene(user.userid, "");
        }

        render(betreuerliste);

    } else {

        Connection conn;
        ResultSet rs;
        ArrayList<models.TourBezirk> bezirke = new ArrayList();

        try {

            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT t.id, t.aktionstag, t.bezirk, b.bezeichnung, ISNULL(b.code1, '<kein>'), (SELECT COUNT(*) FROM tourenposi4posi2011 WHERE posi = t.id) FROM touren2011 AS t INNER JOIN bezirk AS b ON b.regionalschluessel = t.bezirk WHERE t.aktionstag >= ( CURRENT DATE - 30 ) AND t.zusteller IS NULL AND b.code1 = ? ORDER BY t.aktionstag, b.bezeichnung");
            stmt.setString(1, betreuer);
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

        if (action == "pdf") {
            renderPDF(betreuerliste, betreuer, aktionstage, bezirke);
        }
            render(betreuerliste, betreuer, bezirke);
        //renderPDF(betreuerliste, betreuer, aktionstage, bezirke);
        }

    }

}
