package controllers;

import play.mvc.*;

import models.*;

@With(Secure.class)
public class ObjekteAufTouren extends Controller {

    public static void index() {
        render();
    }

}