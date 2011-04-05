package models.zuordnung;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Zuordnung_Suche {

    public String adrinfokey;
    private ArrayList<Zuordnung> zuordnungsliste;
    private Connection conn;

    public Zuordnung_Suche() {
        this.zuordnungsliste = new ArrayList();
    }

    public ArrayList<Zuordnung> ZuordnungenFuerZusteller(String adrinfokey) {
        ResultSet rs;
        this.adrinfokey = adrinfokey;

        try {
                    conn = play.db.DB.getConnection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT id FROM zuordnungzustbezirk WHERE adrinfokey = ?");
                    stmt.setString(1, this.adrinfokey);
                    rs = stmt.executeQuery();

                    while ( rs.next() ){
                        Zuordnung z = new Zuordnung(rs.getString(1));
                        this.zuordnungsliste.add(z);
                    }

                    rs.close();
                    conn.close();
                }

                catch ( SQLException e ) {
                    e.printStackTrace();
                    System.exit(1);
                }

                return this.zuordnungsliste;

    }

} 