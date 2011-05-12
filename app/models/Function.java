package models;

import java.util.*;
import javax.persistence.*;

import play.mvc.*;
import play.db.jpa.*;

@Entity
@Table(name = "USREFXFUNC")
public class Function extends Model {

    public String funcname;
    public String controllerName;
    public String actionName;

    public String toString() {
        return this.funcname;
    }

    public String getRoute() {
        return Router.reverse(this.controllerName + "." + this.actionName).url;
    }

}