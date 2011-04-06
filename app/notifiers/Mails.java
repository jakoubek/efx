package notifiers;
 
import play.*;
import play.mvc.*;
import java.util.*;

import play.libs.Mail;

public class Mails extends Mailer {
 
   public static void testnachricht(String recipientName, String recipientAddress, String subject) {
       setSubject(subject);
       addRecipient(recipientName + " <" + recipientAddress + ">");
       setFrom("Erweiterte Funktionen <efx+dkz@jakoubek.net>");
       send();
   }

}
