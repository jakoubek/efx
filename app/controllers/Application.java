package controllers;

import play.mvc.*;

import java.util.*;
import java.sql.*;

import models.*;

@With(Secure.class)
public class Application extends Controller {

    public static void index() {
        UseStat usestat = new UseStat(Security.connected(), "Application", "index", "show");
        render();
    }

    public static void login() {
        render();
    }

    public static void logout() {
        render();
    }

    public static void funktionen() {
        UseStat usestat = new UseStat(Security.connected(), "Application", "funktionen", "show");
        List<Funktion> funktionen = Funktion.findAll();
        render(funktionen);

    }

    public static void dbversion() {
        UseStat usestat = new UseStat(Security.connected(), "Application", "dbversion", "show");
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



    public static void connectedUsers() {
        UseStat usestat = new UseStat(Security.connected(), "Application", "connectedUsers", "show");
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

}
