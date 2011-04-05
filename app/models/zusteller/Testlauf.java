package models.zusteller;

import models.zuordnung.Zuordnung;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Testlauf {

    public ArrayList<TestlaufEintrag> eintraege;
    private Connection conn;

    public Testlauf() {
        this.eintraege = new ArrayList();
    }

    public ArrayList<TestlaufEintrag> EintraegeFuerZusteller(String adrinfokey) {
        ResultSet rs;

        try {
                    conn = play.db.DB.getConnection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT recordid FROM usrtestlauf WHERE adrinfokey = ?");
                    stmt.setString(1, adrinfokey);
                    rs = stmt.executeQuery();

                    while ( rs.next() ){
                        TestlaufEintrag e = new TestlaufEintrag(rs.getString(1));
                        this.eintraege.add(e);
                    }

                    rs.close();
                    conn.close();
                }

                catch ( SQLException e ) {
                    e.printStackTrace();
                    System.exit(1);
                }

                return this.eintraege;
    }

} 