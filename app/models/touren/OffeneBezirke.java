package models.touren;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class OffeneBezirke {

    public String betreuer; 
    public String aktionstag;
    public Integer anzahl;
    private ArrayList<models.Aktionstag> aktionstage;
    private Connection conn;

    public OffeneBezirke() {
        aktionstage = new ArrayList();
    }

    public ArrayList<models.Aktionstag> getAktionstageForBetreuer() {
        ResultSet rs;

        try {

            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT t.aktionstag, COUNT(t.id) FROM touren2011 AS t INNER JOIN bezirk AS b ON b.regionalschluessel = t.bezirk WHERE t.aktionstag >= ( CURRENT DATE - 30 ) AND t.zusteller IS NULL AND b.code1 = ? GROUP BY t.aktionstag ORDER BY t.aktionstag");
            stmt.setString(1, this.betreuer);
            rs = stmt.executeQuery();

            while ( rs.next() ){
                models.Aktionstag tag = new models.Aktionstag();
                tag.datum = rs.getDate(1);
                tag.anzahl = rs.getInt(2);
                aktionstage.add(tag);
            }

            rs.close();
            conn.close();
        }

        catch ( SQLException e ) {
            e.printStackTrace();
            System.exit(1);
        }   

        this.aktionstage = aktionstage;
        return aktionstage;
    }

    public ArrayList<models.Aktionstag> getAktionstageForBetreuer(String betreuer) {
        this.betreuer = betreuer;
        return this.getAktionstageForBetreuer();
    }

}
