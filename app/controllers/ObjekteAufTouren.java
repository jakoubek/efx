package controllers;

import play.mvc.*;

import models.*;

import java.util.List;

@With(Secure.class)
public class ObjekteAufTouren extends Controller {

    public static void index() {
        UseStat usestat = new UseStat(Security.connected(), "show", 13);
        List<Function> funktionen = new models.FunctionList().funktionen();
        render(funktionen);
    }

}