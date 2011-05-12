package controllers;

import models.zusteller.Zustellersuche;
import play.mvc.*;

import models.*;

import java.util.ArrayList;
import java.util.List;

@With(Secure.class)
public class Zusteller extends Controller {

    public static void index() {
        UseStat usestat = new UseStat(Security.connected(), "show", 14);
        List<Function> funktionen = new models.FunctionList().funktionen();
        render(funktionen);
    }

    public static void getZusteller(int id) {
        UseStat usestat = new UseStat(Security.connected(), "show", 15);
        List<Function> funktionen = new models.FunctionList().funktionen();
        models.zusteller.Zusteller zusteller = new models.zusteller.Zusteller(id);
        renderTemplate("Zusteller/show.html", funktionen, zusteller);
    }

    public static void suche(String q) {
        UseStat usestat = new UseStat(Security.connected(), "show", 16);
        List<Function> funktionen = new models.FunctionList().funktionen();
        if (!q.equals("")) {
            String suchstring = q;
            Zustellersuche zs = new Zustellersuche();
            ArrayList<models.zusteller.Zusteller> zustellerliste = zs.sucheZusteller(q);
            renderTemplate("Zusteller/index.html", funktionen, suchstring ,zustellerliste);
        } else {
            renderTemplate("Zusteller/index.html");
        }
    }

}
