package de.ejb3buch.ticket2rock.applikation.servicelocator;

import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import de.ejb3buch.ticket2rock.session.AuskunftLocal;
import de.ejb3buch.ticket2rock.session.crud.BandVerwaltungLocal;
import de.ejb3buch.ticket2rock.session.crud.KonzertVerwaltungLocal;
import de.ejb3buch.ticket2rock.session.crud.MusikerVerwaltungLocal;
import de.ejb3buch.ticket2rock.session.crud.TourneeVerwaltungLocal;
import de.ejb3buch.ticket2rock.session.ticketbestellung.BestellvorgangLocal;

/**
 * ServiceLocator Implementierung. Services werden in Form von Session Bean
 * Facaden bereitgestellt. Die Klasse kapselt diese Benutzung von JNDI in Form
 * des Anlegen des InitialContext Objektes und Lookup auf ensprechende EJBs und
 * dient als Cache der Client-Seitigen EJB Objekte.
 * 
 */
public class EJB3ServiceLocatorBean implements ServiceLocator {

	static Logger logger = Logger.getLogger(EJB3ServiceLocatorBean.class);

	private BandVerwaltungLocal myBandVerwaltung;

	private MusikerVerwaltungLocal myMusikerVerwaltung;

	private TourneeVerwaltungLocal myTourneeVerwaltung;

	private KonzertVerwaltungLocal myKonzertVerwaltung;

	private AuskunftLocal myAuskunft;
	
	private InitialContext ctx;

	public EJB3ServiceLocatorBean() {
		try {
			ctx = new InitialContext();
			myBandVerwaltung = (BandVerwaltungLocal) ctx
					.lookup("ticket2rock/BandVerwaltungBean/local");
			logger.info("Service BandVerwaltungLocal steht zur Verf�gung");

			myMusikerVerwaltung = (MusikerVerwaltungLocal) ctx
					.lookup("ticket2rock/MusikerVerwaltungBean/local");
			logger.info("Service MusikerVerwaltungLocal steht zur Verf�gung");

			myTourneeVerwaltung = (TourneeVerwaltungLocal) ctx
					.lookup("ticket2rock/TourneeVerwaltungBean/local");
			logger.info("Service TourneeVerwaltungLocal steht zur Verf�gung");

			myKonzertVerwaltung = (KonzertVerwaltungLocal) ctx
					.lookup("ticket2rock/KonzertVerwaltungBean/local");
			logger.info("Service KonzertVerwaltungLocal steht zur Verf�gung");

			myAuskunft = (AuskunftLocal) ctx
					.lookup("ticket2rock/AuskunftBean/local");
			logger.info("Service AuskunftLocal steht zur Verf�gung");
		} catch (Exception e) {
		    logger.error(e);
			e.printStackTrace();
			throw new EJBException("Bei der Allokierung der ServiceBeans ist ein Fehler aufgetreten",e);
		}
	}

	public BandVerwaltungLocal getBandVerwaltung() {
		return myBandVerwaltung;
	}

	public MusikerVerwaltungLocal getMusikerVerwaltung() {
		return myMusikerVerwaltung;
	}

	public AuskunftLocal getAuskunft() {
		return myAuskunft;
	}

	public TourneeVerwaltungLocal getTourneeVerwaltung() {
		return myTourneeVerwaltung;
	}

	public KonzertVerwaltungLocal getKonzertVerwaltung() {
		return myKonzertVerwaltung;
	}
	
	public BestellvorgangLocal getWarenkorb() {
		BestellvorgangLocal einkaufskorb;
		try {
			einkaufskorb = (BestellvorgangLocal) ctx
			.lookup("ticket2rock/BestellvorgangBean/local");
		} catch (NamingException e) {
			e.printStackTrace();
			throw new EJBException("BestellvorgangLocal konnte nicht allokiert werden",e);
		}
      logger.info("Stateful Session Bean BestellvorgangLocal wurde allokiert");
      return einkaufskorb;
	}

}
