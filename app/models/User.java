package models;

import play.Logger;

import play.*;
import play.cache.Cache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class User {

    public String userid;
    public String password;
    public boolean isBetreuer = false;

    public User(String userid) {
        this.userid = userid;
        this.checkBetreuer();
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

}
