package controllers;

import models.brd.HaushaltszahlenNachPLZ_Entry;
import play.mvc.*;

import models.*;

import java.util.ArrayList;
import java.util.List;

@With(Secure.class)
public class HaushaltszahlenNachPLZ extends Controller {

    public static void index(String plz) {
        UseStat usestat = new UseStat(Security.connected(), "show", 12);
        List<Function> funktionen = new models.FunctionList().funktionen();
        models.brd.HaushaltszahlenNachPLZ hhznachplz = new models.brd.HaushaltszahlenNachPLZ();
        ArrayList<HaushaltszahlenNachPLZ_Entry> haushaltszahlen = hhznachplz.liste(plz);
        render(funktionen, plz, haushaltszahlen);
    }

}
