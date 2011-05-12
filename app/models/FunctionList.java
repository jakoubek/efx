package models;

import play.cache.Cache;

import java.util.List;

public class FunctionList {

    public List<Function> funktionen() {

        List<Function> funktionenliste = Cache.get("funktionenliste", List.class);
        if(funktionenliste == null) {
            funktionenliste = Function.findAll();
            Cache.set("funktionenliste", funktionenliste, "30mn");
        }

        return funktionenliste;

    }

} 