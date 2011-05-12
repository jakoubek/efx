package controllers;

import models.zusteller.ErsatzzustellerSuche;
import play.mvc.*;

import models.*;

import java.util.ArrayList;
import java.util.List;

@With(Secure.class)
public class Ersatzzusteller extends Controller {

    public static void index() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Ersatzzusteller", "index");
        List<Function> funktionen = new models.FunctionList().funktionen();
        ErsatzzustellerSuche ez = new models.zusteller.ErsatzzustellerSuche();
        ArrayList<models.zusteller.Ersatzzusteller> ersatzzustellerliste = ez.liste();
        String title = "Liste Ersatzzusteller mit Bezirk";
        renderTemplate("Ersatzzusteller/index.html", funktionen, title, ersatzzustellerliste);
    }

    public static void ohneBezirk() {
        UseStat usestat = new UseStat(Security.connected(), "show", "Ersatzzusteller", "ohneBezirk");
        List<Function> funktionen = new models.FunctionList().funktionen();
        ErsatzzustellerSuche ez = new models.zusteller.ErsatzzustellerSuche();
        ArrayList<models.zusteller.Ersatzzusteller> ersatzzustellerliste = ez.ohneBezirk();
        String title = "Liste Ersatzzusteller ohne Bezirk";
        renderTemplate("Ersatzzusteller/index.html", funktionen, title, ersatzzustellerliste);
    }

}
