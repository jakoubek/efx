package controllers;

import play.mvc.*;

import java.util.*;
import java.sql.*;

import models.*;

@With(Secure.class)
public class Application extends Controller {

    public static void index() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "index");
        List<Function> myMru = UseStatHandler.getMyMru(Security.connected());
        //UseStat u = new UseStat();
        //List<Function> myMru = UseStat.getMyMru("bossmanna");
        render(myMru);
    }

    public static void login() {
        render();
    }

    public static void logout() {
        render();
    }

    public static void funktionen() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "funktionen");
        List<Function> funktionen = Function.findAll();
        render(funktionen);

    }

    public static void dbversion() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "dbversion");
        Connection conn;
    Statement stmt;
    ResultSet rs;
    Version versionObject = new Version();

    try {

    conn = play.db.DB.getConnection();
    stmt = conn.createStatement();
    rs = stmt.executeQuery("select * from version");

        while ( rs.next() ){
            versionObject.version = rs.getString(1);
            versionObject.aenderung = rs.getString(2);
        }

        rs.close();
        conn.close();
    }

    catch ( SQLException e ){
        e.printStackTrace();
        System.exit(1);
    }   

        render(versionObject);
    }

    public static void profil() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "profil");
        User me = new User(Security.connected());
        render(me);
    }

    public static void connectedUsers() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "connectedUsers");

    Connection conn;
    Statement stmt;
    ResultSet rs;
    ArrayList<ConnectedUser> users = new ArrayList();

    try {

    conn = play.db.DB.getConnection();
    stmt = conn.createStatement();
    rs = stmt.executeQuery("CALL sa_conn_info");

        while ( rs.next() ){
            ConnectedUser user = new ConnectedUser();
            user.connNumber = rs.getInt(1);
            user.userId = rs.getString(3);
            user.commLink = rs.getString(9);
            user.nodeAddr = rs.getString(10);
            users.add(user);
        }

        rs.close();
        conn.close();
    }

    catch ( SQLException e ){
        e.printStackTrace();
        System.exit(1);
    }   

        render(users);
    }

    public static void sessions() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "sessions");
        List<efxSession> sessions = efxSession.findAll();
        render(sessions);
    }

    public static void activeSessions() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "activeSessions");
        List<efxSession> sessions = efxSession.find("logoutTime is null").fetch();
        renderTemplate("Application/sessions.html", sessions);
    }

}
