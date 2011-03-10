package models;
 
import play.*;

import java.util.*;
import java.sql.*;

public class Betreuers {
 
    public static ArrayList<models.Betreuer> getBetreuerWithId() {

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

        return betreuerliste;

    }
 
}
