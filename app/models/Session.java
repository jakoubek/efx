package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "USREFXSESSION")
public class Session extends Model {
 
    public String userid;
    public String userAgent;
    public String nodeAddress;
    public Date timeStamp;

    public Session(String userid, String userAgent, String nodeAddress) {
        this.userid = userid.toUpperCase();
        this.userAgent = userAgent;
        this.nodeAddress = nodeAddress;
        this.timeStamp = new Date();
    }

}
