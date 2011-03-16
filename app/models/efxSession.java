package models;

import play.Logger;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "USREFXSESSION")
public class efxSession extends Model {
 
    public String userid;
    public String userAgent;
    public String nodeAddress;
    public Date loginTime;
    public Date logoutTime;
    public String sessionId;

    public efxSession(String userid, String nodeAddress, String userAgent, String sessionId) {
        this.userid = userid;
        this.userAgent = userAgent;
        this.nodeAddress = nodeAddress;
        this.loginTime = new Date();
        this.sessionId = sessionId;
        Logger.debug("neue efxSession: %s / %s", this.userid, this.loginTime.toString());
    }

    public void doLogout() {
        this.logoutTime = new Date();
        this.save();
    }

}
