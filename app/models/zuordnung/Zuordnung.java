package models.zuordnung;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Date;

public class Zuordnung {

    public String id;
    public String adrinfokey;
    public String regionalschluessel;
    public String bezirk;
    public String satztyp;
    public Date gueltigab;
    public Date gueltigbis;
    private Connection conn;

    public Zuordnung(String id) {
        this.id = id;
        ResultSet rs;

        try {

            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT zuo.id, zuo.adrinfokey, zuo.regionalschluessel, b.bezeichnung, zuo.satztyp, zuo.gueltigab, zuo.gueltigbis FROM zuordnungzustbezirk AS zuo INNER JOIN bezirk AS b ON b.regionalschluessel = zuo.regionalschluessel WHERE zuo.id = ?");
            stmt.setString(1, this.id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                this.id = rs.getString(1);
                this.adrinfokey = rs.getString(2);
                this.regionalschluessel = rs.getString(3);
                this.bezirk = rs.getString(4);
                this.satztyp = rs.getString(5);
                this.gueltigab = rs.getDate(6);
                this.gueltigbis = rs.getDate(7);
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
        return this.id;
    }

} 