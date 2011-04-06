package controllers;

import play.mvc.*;

import notifiers.*;

@With(Secure.class)
public class EmailTest extends Controller {

    public static void index() {
        render();
    }

    public static void sendmail(String recipientName, String recipientAddress, String subject) {
        Mails m = new Mails();
        m.testnachricht(recipientName, recipientAddress, subject);
        index();
    }

}
