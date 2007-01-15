package de.ejb3buch.ticket2rock.session.crud;

import java.util.Collection;

import javax.ejb.Local;

import de.ejb3buch.ticket2rock.entity.Band;
import de.ejb3buch.ticket2rock.entity.Musiker;

@Local
public interface MusikerVerwaltung {

    /**
     * Legt eine Musiker-Entit�t in der Persistenzschicht an
     * @param musiker Musiker-POJO mit den Attributen der Band
     */
	public void createMusiker(Musiker musiker);
	
	/**
	 * Selektiert eine Musiker-Entit�t mit einen gegebenen Namen
	 * @param name Name der Musiker, die selektiert werden soll
	 * @return Musiker Objekt, das den bestimmten Namen besitzt.
	 * Existiert kein Musiker mit diesem Namen, ist der R�ckgabewert null
	 */
	public Musiker getMusikerByName(String name);

	/**
	 * Aktualisiert eine Musiker-Entit�t in der Persistenzschicht
	 * @param Musiker Musiker Objekt, das persistiert werden soll
	 */
	public void updateMusiker(Musiker musiker);
	/**
	 * L�scht eine Musiker-Entit�t aus der Persistenzschicht
	 * @param musiker id des zu l�schenden Musikers
	 */
	public void deleteMusiker(Integer musikerId);

//TODO move the band-methods to the interface for bandverwaltung
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
