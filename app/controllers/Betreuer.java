package controllers;

import play.*;
import play.mvc.*;
import play.cache.Cache;

import java.util.*;
import java.sql.*;

import models.*;

@With(Secure.class)
public class Betreuer extends Controller {

    public static void list() {
        UseStat usestat = new UseStat(Security.connected(), "Betreuer", "list", "show");
        models.Betreuers bl = new models.Betreuers();

        ArrayList<models.Betreuer> betreuerliste = Cache.get("betreuerliste", ArrayList.class);
        if(betreuerliste == null) {
            betreuerliste = bl.getBetreuerWithId();
            Cache.set("betreuerliste", betreuerliste, "30mn");
        }
        
        render(betreuerliste);

    }

    public static void list2() {
        UseStat usestat = new UseStat(Security.connected(), "Betreuer", "list2", "show");
    Connection conn;
    Statement stmt;
    ResultSet rs;
    ArrayList<models.Betreuer> betreuerliste = new ArrayList();

    try {

    conn = play.db.DB.getConnection();
    stmt = conn.createStatement();
    rs = stmt.executeQuery("SELECT be.adrinfokey, ba.nachname, ba.vorname, ba.firmenkey1 FROM betreuer_erweiterung AS be INNER JOIN basisadresse AS ba ON ba.adrinfokey = be.adrinfokey ORDER BY nachname, vorname");

        while ( rs.next() ){
            models.Betreuer betreuer = new models.Betreuer();
            betreuer.adrinfokey = rs.getString(1);
            betreuer.nachname = rs.getString(2);
            betreuer.vorname = rs.getString(3);
            betreuer.userid = rs.getString(4);
            betreuerliste.add(betreuer);
        }

        rs.close();
        conn.close();
    }

    catch ( SQLException e ){
        e.printStackTrace();
        System.exit(1);
    }   

        render(betreuerliste);
    }


}
