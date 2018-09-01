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
package de.ejb3buch.ticket2rock.session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.apache.log4j.Logger;

import de.ejb3buch.ticket2rock.entity.Konzert;

@Singleton
//@Startup
@WebService(serviceName = "KonzertInfoHeute")
@SOAPBinding(style = Style.RPC)
public class AuskunftHeuteBean implements AuskunftHeuteLocal, AuskunftHeute {
	static Logger logger = Logger.getLogger(AuskunftHeuteBean.class);

	@EJB
	private AuskunftLocal auskunftLocal;

	private Calendar konzerteAktualisiertDatum;

	private List<Konzert> tagesaktuelleKonzerte = new ArrayList<Konzert>();

	/**
	 * Ermittelt alle Konzerte des aktuellen Tages aller Veranstaltungsorte.
	 * 
	 * @return Liste der tagesaktuelle Konzerte.
	 */
	@WebMethod(exclude = true)
	@Lock(LockType.READ)
	public List<Konzert> sucheTagesaktuelleKonzerte() {

		Calendar heute = getHeutigesDatum();

		if (!heute.equals(konzerteAktualisiertDatum)) {

			tagesaktuelleKonzerte = auskunftLocal.sucheKonzerte(null, heute.getTime(), heute.getTime());

			konzerteAktualisiertDatum = heute;
		}

		return tagesaktuelleKonzerte;
	}

	@WebMethod
	@Lock(LockType.READ)
	public String sucheTagesaktuelleKonzerteWeb() {
		List<Konzert> konzerte = sucheTagesaktuelleKonzerte();
		StringBuffer resultate = new StringBuffer("<konzert-liste>\n");
		for (Konzert konzert : konzerte) {
			resultate.append("  <konzert>\n");
			resultate.append("    <ort>");
			resultate.append(konzert.getOrt().getName());
			resultate.append("</ort>\n");
			resultate.append("    <location>");
			resultate.append(konzert.getOrt().getAdresse());
			resultate.append("</location>\n");
			resultate.append("    <interpret>");
			resultate.append(konzert.getInterpret().getName());
			resultate.append("</interpret>\n");
			resultate.append("    <tournee>");
			resultate.append(konzert.getTournee().getName());
			resultate.append("</tournee>\n");
			resultate.append("  </konzert>\n");
		}
		resultate.append("</konzert-liste>");
		return resultate.toString();
	}

	private Calendar getHeutigesDatum() {
		Calendar heute = new GregorianCalendar();
		heute.setTime(new Date());

		heute.set(Calendar.HOUR_OF_DAY, 0);
		heute.set(Calendar.MINUTE, 0);
		heute.set(Calendar.SECOND, 0);
		heute.set(Calendar.MILLISECOND, 0);
		return heute;
	}

}
