package controllers;

import play.Logger;
import play.mvc.*;

import models.*;

import java.util.List;

public class Security extends Secure.Security {

    efxSession userSession = null;

    static boolean authenticate(String username, String password) {
        String nodeAddress = Http.Request.current().remoteAddress;
        String userAgent = Http.Request.current().headers.get("user-agent").value();
        Logger.debug("Security: neuer Loginversuch von %s (Benutzer: %s / Passwort: %s)", nodeAddress, username, password);
        User user = new User(username, password);
        user.checkBetreuer();
        boolean isAuthenticated = user.doLogin();
        if (isAuthenticated == true) {
            Logger.debug("Login erfolgreich - Benutzer: %s", user.userid);
            efxSession userSession = new efxSession(user.userid, nodeAddress, userAgent, Scope.Session.current().getId());
            userSession.save();
            return true;
        } else {
            return false;
        }
    }

    static void onDisconnect() {
        Logger.debug("Session-ID: %s", Scope.Session.current().getId());
        Logger.debug("Abgemeldeter Benutzer: %s", Security.connected());
        efxSession userSession = efxSession.find("bySessionid", Scope.Session.current().getId()).first();
        userSession.doLogout();
    }

    static void onDisconnected() {
        Logger.debug("Session ausgeloggt");
    }

}
