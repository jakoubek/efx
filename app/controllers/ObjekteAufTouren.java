package controllers;

import play.mvc.*;

import models.*;

@With(Secure.class)
public class ObjekteAufTouren extends Controller {

    public static void index() {
        UseStat usestat = new UseStat(Security.connected(), "show", 13);
        render();
    }

}