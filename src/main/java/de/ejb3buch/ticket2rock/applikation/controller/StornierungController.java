/**
 *  Ticket2Rock ist die Beispielanwendung des Buchs "EJB 3.1 professionell" (dpunkt).
 *  Es implementiert eine einfache Webanwendung zur Onlinebuchung von Tickets für
 *  Rockkonzerten. 
 *
 *  Copyright (C) 2006-2011
 *  Holisticon AG
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
package de.ejb3buch.ticket2rock.applikation.controller;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

@Named("StornierungController")
@SessionScoped
public class StornierungController implements Serializable {

	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(StornierungController.class);
	
	@Resource(mappedName="java:/queue/ticket2rock")
	private Queue queue;
	
	@Resource(mappedName="java:/ConnectionFactory")
	private QueueConnectionFactory factory;
	
	private String bestellnummer;

	public String getBestellnummer() {
		return bestellnummer;
	}

	public void setBestellnummer(String bestellnummer) {
		this.bestellnummer = bestellnummer;
	}

	public String storno() {
		QueueConnection cnn = null;
		QueueSender sender = null;
		QueueSession sess = null;

		try {
			cnn = factory.createQueueConnection();
			sess = cnn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

			TextMessage msg = sess.createTextMessage("STORNO "
					+ this.bestellnummer);
			// The sent timestamp acts as the message's ID
			long sent = System.currentTimeMillis();
			msg.setLongProperty("sent", sent);

			logger.debug("Sending message: " + msg);

			sender = sess.createSender(queue);
			sender.send(msg);
			sess.close();
			cnn.close();
		} catch (Exception e) {
			logger.error(e);
		}

		return "stornierungErgebnis";
	}
}
