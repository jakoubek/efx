package controllers;

import models.zusteller.TestlaufEintrag;
import play.*;
import play.mvc.*;
import play.cache.Cache;

import models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@With(Secure.class)
public class ZustellerdatenTestlauf extends Controller {

    public static void index() {
        UseStat usestat = new UseStat(Security.connected(), "show", 17);
        List<Function> funktionen = new models.FunctionList().funktionen();

        models.Betreuers bl = new models.Betreuers();
        ArrayList<models.Betreuer> betreuerliste = Cache.get("betreuerliste", ArrayList.class);
        if(betreuerliste == null) {
            betreuerliste = bl.getBetreuerWithId();
            Cache.set("betreuerliste", betreuerliste, "30mn");
        }

        render(funktionen, betreuerliste);
    }

    public static void list(String betreuer) {
        UseStat usestat = new UseStat(Security.connected(), "show", 18);
        List<Function> funktionen = new models.FunctionList().funktionen();

        models.Betreuers bl = new models.Betreuers();
        ArrayList<models.Betreuer> betreuerliste = Cache.get("betreuerliste", ArrayList.class);
        if(betreuerliste == null) {
            betreuerliste = bl.getBetreuerWithId();
            Cache.set("betreuerliste", betreuerliste, "30mn");
        }

        Connection conn;
        ResultSet rs;
        ArrayList<TestlaufEintrag> eintraege = new ArrayList();

        try {

            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT tl.recordid, tl.adrinfokey, tl.problemid, p.problembereich, p.bezeichnung, p.sortierung, tl.xabrrel, tl.matchcode, tl.betreuer, tl.eintritt, tl.austritt, tl.firma, tl.kostenstelle, tl.listenid, ze.farbcode FROM usrtestlauf AS tl INNER JOIN usrtestlaufproblem AS p ON p.problemid = tl.problemid INNER JOIN zusteller_erweiter AS ze ON ze.adrinfokey = tl.adrinfokey WHERE tl.betreuer = ? ORDER BY tl.matchcode, p.sortierung");
            stmt.setString(1, betreuer);
            rs = stmt.executeQuery();

            String old_adrinfokey = "";

            while ( rs.next() ){
                TestlaufEintrag eintrag = new TestlaufEintrag();
                eintrag.recordid = rs.getString(1);
                eintrag.adrinfokey = rs.getString(2);
                eintrag.zustellerid = rs.getInt(15);
                eintrag.problemid = rs.getString(3);
                eintrag.problembereich = rs.getString(4);
                eintrag.problembezeichnung = rs.getString(5);
                eintrag.sortierung = rs.getInt(6);
                eintrag.abrechnungsrelevant = rs.getString(7);
                if (! old_adrinfokey.equals(rs.getString(2))) {
                    eintrag.zustellermatchcode = rs.getString(8);
                } else {
                    eintrag.zustellermatchcode = "";
                }
                eintrag.betreuer = rs.getString(9);
                eintrag.eintritt = rs.getDate(10);
                eintrag.austritt = rs.getDate(11);
                eintrag.firma = rs.getString(12);
                eintrag.kostenstelle = rs.getInt(13);
                eintrag.listenid = rs.getInt(14);
                eintraege.add(eintrag);
                old_adrinfokey = eintrag.adrinfokey;
            }

            rs.close();
            conn.close();
        }

        catch ( SQLException e ){
            e.printStackTrace();
            System.exit(1);
        }

        renderTemplate("ZustellerdatenTestlauf/index.html", funktionen, betreuerliste, eintraege);

    }

}
