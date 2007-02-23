/**
 *  Ticket2Rock ist die Beispielanwendung des Buchs "EJB 3 professionell" (dpunkt).
 *  Es implementiert eine einfache Webanwendung zur Onlinebuchung von Tickets f�r
 *  Rockkonzerte auf Basis von EJB 3.0 und JavaServer Faces.
 *
 *  Copyright (C) 2006
 *  Dierk Harbeck, Stefan M. Heldt, Oliver Ihns, Jochen J�rg und Holger Koschek
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.ejb3buch.ticket2rock.messagedriven;

import java.util.StringTokenizer;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.mail.Message;

import org.apache.log4j.Logger;
import org.jboss.annotation.ejb.ResourceAdapter;
import org.jboss.resource.adapter.mail.inflow.MailListener;

import de.ejb3buch.ticket2rock.session.ticketbestellung.Stornator;

/**
 * ACHTUNG: Der Mail Resource Adapter funktioniert nur mit IMAP, da er nur auf Mails reagiert, die als neu markiert
 * sind. POP3 kennt diese Markierung nicht.
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "mailServer", propertyValue = "mail.messagingengine.com"),
        @ActivationConfigProperty(propertyName = "mailFolder", propertyValue = "INBOX"),
        @ActivationConfigProperty(propertyName = "storeProtocol", propertyValue = "imap"),
        @ActivationConfigProperty(propertyName = "userName", propertyValue = "ejb3buch@fastmail.fm"),
        @ActivationConfigProperty(propertyName = "password", propertyValue = "ejb3b5ch") })
@ResourceAdapter("mail-ra.rar")
public class MailStornatorBean implements MailListener {
    static Logger logger = Logger.getLogger(MailStornatorBean.class);

    @EJB
    private Stornator stornator;

    public void onMessage(Message msg) {
        try {
            String subject = msg.getSubject();
            StringTokenizer tokenizer = new StringTokenizer(subject, " ");
            String command = tokenizer.nextToken();
            if ("STORNO".equalsIgnoreCase(command)) {
                String bestellNrAsString = tokenizer.nextToken();
                long bestellnr = Long.parseLong(bestellNrAsString);
                stornator.storniereBestellung(bestellnr);
                logger.info("Stornierung von Bestellnr. " + bestellnr + " durchgefuehrt");
            } else {
                logger.info("eMail Subject " + subject + " ist nicht zu verarbeiten");
            }
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }
}
