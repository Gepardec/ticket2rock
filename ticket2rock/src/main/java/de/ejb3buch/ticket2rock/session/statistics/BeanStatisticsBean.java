/**
 *  Ticket2Rock ist die Beispielanwendung des Buchs "EJB 3.1 professionell" (dpunkt).
 *  Es implementiert eine einfache Webanwendung zur Onlinebuchung von Tickets f�r
 *  Rockkonzerten. 
 *
 *  Copyright (C) 2011
 *  Jo Ehm, Stefan M. Heldt, Oliver Ihns, Holger Koschek,
 *  Carsten Sahling, Roman Schloemmer, Norman Erck, Daniel Steinh�fer,
 *  Carl A. D�vel.
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
package de.ejb3buch.ticket2rock.session.statistics;

import java.lang.reflect.Method;
import java.util.Map;

import javax.ejb.Stateless;
import javax.interceptor.ExcludeDefaultInterceptors;

/**
 * Diese Bean f�hrt mittels statische Felder eine Statistik von Methodenaufrufen,
 * deren Dauer und die Benutzung von Klassen. 
 * TODO: Carl MkSingleton! 
 */


@Stateless
@ExcludeDefaultInterceptors
public class BeanStatisticsBean implements BeanStatisticsLocal,
		BeanStatisticsRemote {

	@SuppressWarnings("rawtypes")
	public Map<Class, Integer> getClassUsage() {
		return BeanStatisticsRecord.classUsage;
	}

	public Map<Method, Integer> getMethodUsage() {
		return BeanStatisticsRecord.methodUsage;
	}

	public Map<Method, Long> getMethodTotalDuration() {
		return BeanStatisticsRecord.methodDuration;
	}

	public void reportNewObject(Object object) {
		if (!BeanStatisticsRecord.classUsage.containsKey(object.getClass())) {
			// First object of the given class
			BeanStatisticsRecord.classUsage.put(object.getClass(), 1);
		} else {
			// Other objects of this class already exist
			Integer currentCount = BeanStatisticsRecord.classUsage.get(object
					.getClass());
			currentCount++;
			BeanStatisticsRecord.classUsage
					.put(object.getClass(), currentCount);
		}
	}

	public void reportMethodCall(Method method) {
		if (!BeanStatisticsRecord.methodUsage.containsKey(method)) {
			// First object of the given class
			BeanStatisticsRecord.methodUsage.put(method, 1);
		} else {
			// Other objects of this class already exist
			Integer currentCount = BeanStatisticsRecord.methodUsage.get(method);
			currentCount++;
			BeanStatisticsRecord.methodUsage.put(method, currentCount);
			BeanStatisticsRecord.methodUsage.put(method, currentCount++);
		}
	}

	public void reportMethodDuration(Method method, long duration) {
		if (!BeanStatisticsRecord.methodDuration.containsKey(method)) {
			// First object of the given class
			BeanStatisticsRecord.methodDuration.put(method, duration);
		} else {
			// Other objects of this class already exist
			Long totalDuration = BeanStatisticsRecord.methodDuration
					.get(method);
			totalDuration += duration;
			BeanStatisticsRecord.methodDuration.put(method, totalDuration);
		}
	}
}