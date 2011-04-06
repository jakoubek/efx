package models.zusteller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Zustellersuche {

    public String suchstring;
    private ArrayList<Zusteller> zustellerliste;
    private Connection conn;

    public Zustellersuche() {
        this.zustellerliste = new ArrayList();
    }

    public ArrayList<Zusteller> sucheZusteller(String suchstring) {
        ResultSet rs;
        this.suchstring = "%" + suchstring + "%";

        try {
            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT ze.farbcode, ze.adrinfokey, ba.nachname, ba.vorname, TRIM(ba.kontroll_strasse) || ' ' || ba.hsnr || ba.hsnrzusatz, ba.kontroll_plz, ba.kontroll_ort, ba.kontroll_ortsteil FROM zusteller_erweiter AS ze INNER JOIN basisadresse AS ba ON ba.adrinfokey = ze.adrinfokey WHERE (ba.nachname LIKE ? OR ba.vorname LIKE ? OR ba.firmenkey1 LIKE ?) ORDER BY ba.nachname, ba.vorname");
            stmt.setString(1, this.suchstring);
            stmt.setString(2, this.suchstring);
            stmt.setString(3, this.suchstring);
            rs = stmt.executeQuery();

            while ( rs.next() ){
                Zusteller z = new Zusteller(rs.getInt(1));
                this.zustellerliste.add(z);
            }

            rs.close();
            conn.close();
        }

        catch ( SQLException e ) {
            e.printStackTrace();
            System.exit(1);
        }

        return this.zustellerliste;

    }

} 