package controllers;

import play.mvc.*;

import java.util.*;
import java.sql.*;

import models.*;

@With(Secure.class)
public class Application extends Controller {

    public static void index() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "index");
        List<Function> funktionen = new models.FunctionList().funktionen();
        List<Function> myMru = UseStatHandler.getMyMru(Security.connected());
        //UseStat u = new UseStat();
        //List<Function> myMru = UseStat.getMyMru("bossmanna");
        render(funktionen, myMru);
    }

    public static void login() {
        render();
    }

    public static void logout() {
        render();
    }

    public static void funktionen() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "funktionen");
        List<Function> funktionen = new models.FunctionList().funktionen();
        render(funktionen);

    }

    public static void callFunction(Long functionid) {
        Function function = Function.findById(functionid);
        redirect(function.getRoute());
    }

    public static void dbversion() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "dbversion");
        List<Function> funktionen = new models.FunctionList().funktionen();
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

        render(funktionen, versionObject);
    }

    public static void profil() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "profil");
        List<Function> funktionen = new models.FunctionList().funktionen();
        User me = new User(Security.connected());
        render(funktionen, me);
    }

    public static void connectedUsers() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "connectedUsers");
        List<Function> funktionen = new models.FunctionList().funktionen();

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

        render(funktionen, users);
    }

    public static void sessions() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "sessions");
        List<Function> funktionen = new models.FunctionList().funktionen();
        List<efxSession> sessions = efxSession.findAll();
        render(funktionen, sessions);
    }

    public static void activeSessions() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Application", "activeSessions");
        List<Function> funktionen = new models.FunctionList().funktionen();
        List<efxSession> sessions = efxSession.find("logoutTime is null").fetch();
        renderTemplate("Application/sessions.html", funktionen, sessions);
    }

}
