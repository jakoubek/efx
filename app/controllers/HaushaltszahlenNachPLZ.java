package controllers;

import models.brd.HaushaltszahlenNachPLZ_Entry;
import play.mvc.*;

import models.*;

import java.util.ArrayList;

@With(Secure.class)
public class HaushaltszahlenNachPLZ extends Controller {

    public static void index(String plz) {
        models.brd.HaushaltszahlenNachPLZ hhznachplz = new models.brd.HaushaltszahlenNachPLZ();
        ArrayList<HaushaltszahlenNachPLZ_Entry> haushaltszahlen = hhznachplz.liste(plz);
        render(plz, haushaltszahlen);
    }

}
