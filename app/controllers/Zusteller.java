package controllers;

import models.zusteller.Zustellersuche;
import play.mvc.*;

import models.*;

import java.util.ArrayList;

@With(Secure.class)
public class Zusteller extends Controller {

    public static void index() {
        render();
    }

    public static void getZusteller(int id) {
        models.zusteller.Zusteller zusteller = new models.zusteller.Zusteller(id);
        renderTemplate("Zusteller/show.html", zusteller);
    }

    public static void suche(String q) {
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
