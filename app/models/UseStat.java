package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

import models.Function;

@Entity
@Table(name = "USREFXUSESTAT")
public class UseStat extends Model {

    public String userid;
    public Date timestamp;
    public long function;
    public String viewmode;

    public UseStat(String userid, String viewmode, String controllerName, String actionName) {
        Function function = Function.find("controllerName = ? and actionName = ?", controllerName, actionName).first();

        this.userid = userid;
        this.timestamp = new Date();
        this.function = function.getId();
        this.viewmode = viewmode;
        this.save();
    }

    public UseStat(String userid, String viewmode, long functionId) {
        this.userid = userid;
        this.timestamp = new Date();
        this.function = functionId;
        this.viewmode = viewmode;
        this.save();
    }

}