package controllers;

import models.zusteller.ErsatzzustellerSuche;
import play.mvc.*;

import models.*;

import java.util.ArrayList;

@With(Secure.class)
public class Ersatzzusteller extends Controller {

    public static void index() {
        UseStat usestat = new UseStat(Security.connected(), "Ersatzzusteller", "index", "show");
        ErsatzzustellerSuche ez = new models.zusteller.ErsatzzustellerSuche();
        ArrayList<models.zusteller.Ersatzzusteller> ersatzzustellerliste = ez.liste();
        String title = "Liste Ersatzzusteller mit Bezirk";
        renderTemplate("Ersatzzusteller/index.html", title, ersatzzustellerliste);
    }

    public static void ohneBezirk() {
        UseStat usestat = new UseStat(Security.connected(), "Ersatzzusteller", "ohneBezirk", "show");
        ErsatzzustellerSuche ez = new models.zusteller.ErsatzzustellerSuche();
        ArrayList<models.zusteller.Ersatzzusteller> ersatzzustellerliste = ez.ohneBezirk();
        String title = "Liste Ersatzzusteller ohne Bezirk";
        renderTemplate("Ersatzzusteller/index.html", title, ersatzzustellerliste);
    }

}
