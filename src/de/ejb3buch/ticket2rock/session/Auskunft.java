package de.ejb3buch.ticket2rock.session;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import de.ejb3buch.ticket2rock.entity.Konzert;

@Local
public interface Auskunft {

	/**
	 * Sucht nach Konzerten anhand �bergebener Suchkriterien. Falls ein Suchkriterion null
	 * oder leer ist, wird es nicht bei der Suche ber�cksichtigt
	 * @param ortsname Name des Veranstaltungsortes
	 * @param vonDatum untere Datumsgrenze des Veranstaltungszeitraums
	 * @param bisDatum obere Datumsgrenze des Veranstaltungszeitraums
	 * @return Liste von Konzert Entit�ten, die anhand der �bergebenen Parameter gefunden 
	 * wurden
	 */
	public List<Konzert> sucheKonzerte(String ortsname,Date vonDatum,Date bisDatum);
}
