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
package de.ejb3buch.ticket2rock.session.interceptor;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import de.ejb3buch.ticket2rock.session.statistics.BeanStatisticsLocal;


public @BeanStatistics @Interceptor class BeanStatisticsInterceptor {

	private BeanStatisticsLocal beanstats;

	@EJB(beanName = "BeanStatisticsBean")
	public void setBeanStatisticsBean(BeanStatisticsLocal c) {
		beanstats = c;
	}

	// Lebenszyklus-Interzeptoren und Geschäftsmethoden-Interzeptoren
	// können in derselben Interzeptor-Klasse definiert werden.
	// Interzeptoren dürfen keine applikationsspezifischen Exceptions
	// werfen.
	//@PostConstruct
	public void onPostConstruct(InvocationContext ctx) throws Exception {
		beanstats.reportNewObject(ctx.getTarget());
		ctx.proceed();
	}

	@AroundInvoke
	public Object onMethodCall(InvocationContext ctx) throws Exception {
		beanstats.reportMethodCall(ctx.getMethod());
		long startTime = System.currentTimeMillis();
		try {
			return ctx.proceed();
		} finally {
			long duration = System.currentTimeMillis() - startTime;
			beanstats.reportMethodDuration(ctx.getMethod(), duration);
		}
	}
}
