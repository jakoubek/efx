package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
@Table(name = "USREFXUSESTAT")
public class UseStat extends Model {

    public String userid;
    public Date timestamp;
    public String controller;
    public String action;
    public String viewmode;

    public UseStat(String userid, String controller, String action, String viewmode) {
        this.userid = userid;
        this.timestamp = new Date();
        this.controller = controller;
        this.action = action;
        this.viewmode = viewmode;
        this.save();
    }

} 