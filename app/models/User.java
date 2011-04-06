package models;

import models.zuordnung.Zuordnung_Suche;
import models.zusteller.Testlauf;
import play.Logger;

import play.*;
import play.cache.Cache;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class User {

    public String userid;
    public String password;
    public boolean isBetreuer = false;
    public String emailadresse;
    private Connection conn;

    public User(String userid) {
        this.userid = userid;
        this.checkBetreuer();
        this.readEmailadresse();
    }

    public User(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    public boolean doLogin() {
        Connection conn = null;
        String dbUrl = play.Play.configuration.getProperty("db.url");
        if (this.userid != null && this.password != null && this.password.length() > 0) {
            try {
                conn = DriverManager.getConnection(dbUrl, this.userid, this.password);
                Logger.debug("Authentifizierungsverbindung zu %s erfolgreich", dbUrl);
            } catch ( SQLException e ) {
                e.printStackTrace();
                return false;
            } finally {
              if ( conn != null )
                try { conn.close(); } catch ( SQLException e ) { e.printStackTrace(); }
            }
            return true;
        }
        return false;
    }

    public boolean checkBetreuer() {
        models.Betreuers bl = new models.Betreuers();
        ArrayList<Betreuer> betreuerliste = Cache.get("betreuerliste", ArrayList.class);
        if(betreuerliste == null) {
            betreuerliste = bl.getBetreuerWithId();
            Cache.set("betreuerliste", betreuerliste, "30mn");
        }
        for ( Iterator<Betreuer> betreuer = betreuerliste.iterator(); betreuer.hasNext(); ) {
            if (betreuer.next().userid == this.userid) {
                this.isBetreuer = true;
                return true;
            }
        }
        return false;
    }

    public void readEmailadresse() {
        ResultSet rs;

        try {

            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select doc_email from mitarbeiter_erweiterung where user_name = ?");
            stmt.setString(1, this.userid);
            rs = stmt.executeQuery();

            if (rs.next()) {
                this.emailadresse = rs.getString(1).toLowerCase();
            }

            rs.close();
            conn.close();
        }

        catch ( SQLException e ) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
