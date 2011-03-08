package controllers;

import models.TourBezirk;
import play.mvc.Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Bezirke extends Controller {

    public static void listOffene() {

    Connection conn;
    Statement stmt;
    ResultSet rs;
    ArrayList<models.TourBezirk> bezirke = new ArrayList();

    try {

    conn = play.db.DB.getConnection();
    stmt = conn.createStatement();
    rs = stmt.executeQuery("SELECT t.id, t.aktionstag, t.bezirk, b.bezeichnung, ISNULL(b.code1, '<kein>'), (SELECT COUNT(*) FROM tourenposi4posi2011 WHERE posi = t.id) FROM touren2011 AS t INNER JOIN bezirk AS b ON b.regionalschluessel = t.bezirk WHERE t.aktionstag >= ( CURRENT DATE - 30 ) AND t.zusteller IS NULL ORDER BY t.aktionstag, b.bezeichnung");

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

        render(bezirke);
    }


}
