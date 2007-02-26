package de.ejb3buch.ticket2rock.session.crud;

import java.util.Collection;

import javax.ejb.Local;

import de.ejb3buch.ticket2rock.entity.Tournee;

@Local
public interface TourneeVerwaltungLocal {

    /**
     * Legt eine Tournee-Entit�t in der Persistenzschicht an
     * @param tournee pojo mit den Attributen der Tournee
     */
	public void createTournee(Tournee tournee);
	
	/**
	 * Selektiert eine Tournee-Entit�t mit einen gegebenen Namen
	 * @param name Name der Tournee, die selektiert werden soll
	 * @return Tournee Objekt, das den bestimmten Namen besitzt.
	 * Existiert keine Tournee mit diesem Namen, ist der R�ckgabewert null
	 */
	public Tournee getTourneeByName(String name);

	/**
	 * Aktualisiert eine Tournee-Entit�t in der Persistenzschicht
	 * @param tournee Tournee Objekt, das persistiert werden soll
	 */
	public void updateTournee(Tournee tournee);

	/**
	 * L�scht eine Tournee-Entit�t aus der Persistenzschicht
	 * @param tourneeId id der zu l�schenden Tournee
	 */
	public void deleteTournee(Integer tourneeId);

	/**
	 * selektiert alle Tournee-Entit�ten
	 * @return Tournee-Entit�ten
	 */
	public Collection<Tournee> getTourneen();
	
	/**
	 * Selektiert eine Tournee f�r eine gegebene Id
	 * @param tourneeId
	 * @return Tournee Entit�t, null fallse keine Tournee-Entit�t
	 * mit dieser id existiert
	 */
	public Tournee getTourneeById(Integer tourneeId);
}
