package controllers;

import models.*;

public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        Session session = new Session(username, "Mozilla", "10.130.3.40");
        //session.save();
        return true;
    }

    static void onAuthenticated() {
        Application.index();
    }

}
