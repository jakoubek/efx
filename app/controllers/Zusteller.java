package controllers;

import models.zusteller.Zustellersuche;
import play.mvc.*;

import models.*;

import java.util.ArrayList;

@With(Secure.class)
public class Zusteller extends Controller {

    public static void index() {
        UseStat usestat = new UseStat(Security.connected(), "Zusteller", "index", "show");
        render();
    }

    public static void getZusteller(int id) {
        UseStat usestat = new UseStat(Security.connected(), "Zusteller", "getZusteller", "show");
        models.zusteller.Zusteller zusteller = new models.zusteller.Zusteller(id);
        renderTemplate("Zusteller/show.html", zusteller);
    }

    public static void suche(String q) {
        UseStat usestat = new UseStat(Security.connected(), "Zusteller", "suche", "show");
        if (!q.equals("")) {
            String suchstring = q;
            Zustellersuche zs = new Zustellersuche();
            ArrayList<models.zusteller.Zusteller> zustellerliste = zs.sucheZusteller(q);
            renderTemplate("Zusteller/index.html", suchstring ,zustellerliste);
        } else {
            renderTemplate("Zusteller/index.html");
        }
    }

}
