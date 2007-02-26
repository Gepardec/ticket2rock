package de.ejb3buch.ticket2rock.session.crud;

import java.util.Collection;

import javax.ejb.Local;

import de.ejb3buch.ticket2rock.entity.Konzert;

@Local
public interface KonzertVerwaltungLocal {

    /**
     * Legt eine Konzert-Entit�t in der Persistenzschicht an
     * @param konzert pojo mit den Attributen des anzulegenden Konzerts
     */
	public void createConcert(Konzert konzert);
	

	/**
	 * Aktualisiert eine Konzert-Entit�t in der Persistenzschicht
	 * @param konzert Konzert, das persistiert werden soll
	 */
	public void updateConcert(Konzert konzert);

	/**
	 * L�scht eine Konzert-Entit�t aus der Persistenzschicht
	 * @param konzertId id des zu l�schenden Konzerts
	 */
	public void deleteConcert(Integer konzertId);

	
	/**
	 * selektiert alle Konzert-Entit�ten
	 * @return Konzert-Entit�ten
	 */
	public Collection<Konzert> getConcerts();
	
	/**
	 * Selektiert ein Konzert f�r eine gegebene Id
	 * @param konzertId
	 * @return Konzert Entit�t, null fallse keine Konzert-Entit�t
	 * mit dieser id existiert
	 */
	public Konzert getConcertById(Integer konzertId);
    

}
