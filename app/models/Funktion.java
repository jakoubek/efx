package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
@Table(name = "USREFXFUNC")
public class Funktion extends Model {
 
    public String funcname;
    public String controllerName;

}
