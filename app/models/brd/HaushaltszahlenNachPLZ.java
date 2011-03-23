package models.brd;

import org.apache.commons.collections.iterators.ArrayListIterator;
import play.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HaushaltszahlenNachPLZ {

    private ArrayList<HaushaltszahlenNachPLZ_Entry> liste = new ArrayList();
    private Connection conn;

    public ArrayList<HaushaltszahlenNachPLZ_Entry> liste(String plz) {
        ResultSet rs;

        try {

            conn = play.db.DB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT plz, gemeinde, ortsteil, bruttohhz, nettohhz, anzblatthhz FROM V_Usr_BrdPlzHhz WHERE plz = ? ORDER BY plz, gemeinde, ortsteil");
            stmt.setString(1, plz);
            rs = stmt.executeQuery();

            while ( rs.next() ){
                HaushaltszahlenNachPLZ_Entry entry = new HaushaltszahlenNachPLZ_Entry();
                entry.plz = rs.getString(1);
                entry.gemeinde = rs.getString(2);
                entry.ortsteil = rs.getString(3);
                entry.bruttohhz = rs.getInt(4);
                entry.nettohhz = rs.getInt(5);
                entry.anzblatthhz = rs.getInt(6);
                liste.add(entry);
            }

            rs.close();
            conn.close();
        }

        catch ( SQLException e ) {
            e.printStackTrace();
            System.exit(1);
        }

        return liste;

    }

}
