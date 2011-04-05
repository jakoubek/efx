package models.zusteller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TestlaufEintrag {

    public String recordid;
    public String adrinfokey;
    public int zustellerid;
    public String problemid;
    public String problembereich;
    public String problembezeichnung;
    public int sortierung;
    public String abrechnungsrelevant;
    public String zustellermatchcode;
    public String betreuer;
    public Date eintritt;
    public Date austritt;
    public String firma;
    public int kostenstelle;
    public int listenid;
    private Connection conn;

    public TestlaufEintrag() {
    }

    public TestlaufEintrag(String recordid) {
        this.recordid = recordid;
        ResultSet rs;

        try {

            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT tl.recordid, tl.adrinfokey, tl.problemid, p.problembereich, p.bezeichnung, p.sortierung, tl.xabrrel, tl.matchcode, tl.betreuer, tl.eintritt, tl.austritt, tl.firma, tl.kostenstelle, tl.listenid, ze.farbcode FROM usrtestlauf AS tl INNER JOIN usrtestlaufproblem AS p ON p.problemid = tl.problemid INNER JOIN zusteller_erweiter AS ze ON ze.adrinfokey = tl.adrinfokey WHERE tl.recordid = ?");
            stmt.setString(1, this.recordid);
            rs = stmt.executeQuery();

            if (rs.next()) {
                this.recordid = rs.getString(1);
                this.adrinfokey = rs.getString(2);
                this.zustellerid = rs.getInt(15);
                this.problemid = rs.getString(3);
                this.problembereich = rs.getString(4);
                this.problembezeichnung = rs.getString(5);
                this.sortierung = rs.getInt(6);
                this.abrechnungsrelevant = rs.getString(7);
                this.zustellermatchcode = rs.getString(8);
                this.betreuer = rs.getString(9);
                this.eintritt = rs.getDate(10);
                this.austritt = rs.getDate(11);
                this.firma = rs.getString(12);
                this.kostenstelle = rs.getInt(13);
                this.listenid = rs.getInt(14);
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
        return this.zustellermatchcode + " (" + this.problembezeichnung + ")";
    }

} 