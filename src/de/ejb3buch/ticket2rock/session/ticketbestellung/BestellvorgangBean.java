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
package de.ejb3buch.ticket2rock.session.ticketbestellung;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import de.ejb3buch.ticket2rock.entity.Konzert;
import de.ejb3buch.ticket2rock.entity.Kunde;
import de.ejb3buch.ticket2rock.entity.Ticketbestellung;
import de.ejb3buch.ticket2rock.session.crud.KundenVerwaltungLocal;

@Stateful
@SuppressWarnings("unchecked")
public class BestellvorgangBean implements Bestellvorgang, BestellvorgangLocal {

	static Logger logger = Logger.getLogger(BestellvorgangBean.class);
	List<Ticketbestellung> ticketBestellungen = new ArrayList<Ticketbestellung>();

	@PersistenceContext
	private EntityManager em;
	
	@EJB
    private KundenVerwaltungLocal kundenverwaltung;

	/**
	 * @inheritDoc
	 */
	public void bestelleTickets(Konzert konzert, int ticketAnzahl) {
		Ticketbestellung ticketReservierung = new Ticketbestellung();
		ticketReservierung.setKonzert(konzert);
		ticketReservierung.setAnzahl(ticketAnzahl);
		ticketBestellungen.add(ticketReservierung);
	}
	
	
	/**
	 * @inheritDoc
	 */
	public Collection<Ticketbestellung> getTicketbestellungen() {
		return ticketBestellungen;
	}

	/**
	 * @inheritDoc
	 */
	@Remove
	public void verwerfeTicketbestellungen() {
		// TODO Auto-generated method stub
		
	}
	
    /**
     * @inheritDoc
     */
	 public void verwerfeTicketbestellung(Ticketbestellung bestellung) {
		 ticketBestellungen.remove(bestellung);		 
	 }
	 
	 /**
	  * @inheritDoc
	  */
	 public boolean hasBestellungen() {
		return !this.ticketBestellungen.isEmpty(); 
	 }
	

	/**
	 * @inheritDoc
	 */
	 @Remove
	public void bezahleTickets(String email) {
		
		Kunde kunde = kundenverwaltung.getKundeByEmail(email);
		if (kunde == null) {
		   kunde = new Kunde();
		   kunde.setEmail(email);
		   kunde.setBestellungen(ticketBestellungen);
		   em.persist(kunde);
		}
		else {
		   kunde.setEmail(email);
		   kunde.addBestellungen(ticketBestellungen);
		   em.merge(kunde);			
		}
		
	}
	
	// Live-Statistik zur Nutzung dieser Bean

	@PostConstruct
	public void onPostConstruct() {
		// Session-Statistik aktualisieren
		BestellvorgangSessionStatistics.totalSessions++;
		BestellvorgangSessionStatistics.activeSessions++;
	}

	@PreDestroy
	public void onPreDestroy() {
		// Session-Statistik aktualisieren
		BestellvorgangSessionStatistics.totalSessions--;
		BestellvorgangSessionStatistics.activeSessions--;
	}

	@PrePassivate
	public void onPrePassivate() {
		// Session-Statistik aktualisieren
		BestellvorgangSessionStatistics.passivatedSessions++;
		BestellvorgangSessionStatistics.activeSessions--;
	}

	@PostActivate
	public void onPostActivate() {
		// Session-Statistik aktualisieren
		BestellvorgangSessionStatistics.passivatedSessions--;
		BestellvorgangSessionStatistics.activeSessions++;
	}


}
