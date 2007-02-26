package de.ejb3buch.ticket2rock.session.crud;

import java.util.Collection;

import javax.ejb.Local;

import de.ejb3buch.ticket2rock.entity.Band;
import de.ejb3buch.ticket2rock.entity.Musiker;

@Local
public interface BandVerwaltungLocal {

    /**
     * Legt eine Band-Entit�t in der Persistenzschicht an
     * @param band band pojo mit den Attributen der Band
     */
	public void createBand(Band band);
	
	/**
	 * Selektiert eine Band-Entit�t mit einen gegebenen Namen
	 * @param name Name der Band, die selektiert werden soll
	 * @return Band Objekt, das den bestimmten Namen besitzt.
	 * Existiert keine Band mit diesem Namen, ist der R�ckgabewert null
	 */
	public Band getBandByName(String name);

	/**
	 * Aktualisiert eine Band-Entit�t in der Persistenzschicht
	 * @param band Band Objekt, das persistiert werden soll
	 */
	public void updateBand(Band band);

	/**
	 * L�scht eine Band-Entit�t aus der Persistenzschicht
	 * @param bandId id der zu l�schenden Band
	 */
	public void deleteBand(Integer bandId);

//TODO move the Musiker methods to the interface for MusikerVerwaltungLocal	
	/**
	 * selektiert alle Musiker-Entit�ten
	 * @return Musiker-Entit�ten
	 */
	public Collection<Musiker> getMusiker();
	
	/**
	 * selektiert alle Band-Entit�ten
	 * @return Band-Entit�ten
	 */
	public Collection<Band> getBands();
	
	/**
	 * Selektiert eine Band f�r eine gegebene Id
	 * @param bandId
	 * @return Band Entit�t, null fallse keine Band-Entit�t
	 * mit dieser id existiert
	 */
	public Band getBandById(Integer bandId);
    
	/**
	 * Selektiert einen Musiker f�r eine gegebene Id
	 * @param musikerId
	 * @return Musiker Entit�t, null falls kein Musiker-Entit�t
	 * mit dieser id existiert
	 */
	public Musiker getMusikerById(Integer musikerId);

}
