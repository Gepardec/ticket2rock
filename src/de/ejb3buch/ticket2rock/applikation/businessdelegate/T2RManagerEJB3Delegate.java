package de.ejb3buch.ticket2rock.applikation.businessdelegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import de.ejb3buch.ticket2rock.applikation.model.BandBakingBean;
import de.ejb3buch.ticket2rock.entity.Band;
import de.ejb3buch.ticket2rock.entity.Musiker;
import de.ejb3buch.ticket2rock.session.manager.T2RManagerLocal;

public class T2RManagerEJB3Delegate implements T2RManagerDelegate {

	T2RManagerLocal myT2RManager;
	static Logger logger = Logger.getLogger(T2RManagerEJB3Delegate.class);

	/**
	 * Konstruktor des BusinessDelegates. �ber den InitialContext wird eine
	 * lokale Referenz der Session Bean geholt
	 * 
	 */
	public T2RManagerEJB3Delegate() {
		try {
			InitialContext ctx = new InitialContext();
			myT2RManager = (T2RManagerLocal) ctx
					.lookup("ticket2rock/T2RManagerBean/local");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Hole die Liste aller Band EJBs und konvertiere diese in eine Liste von
	 * BandBacking Beans
	 * 
	 * @return Liste von BandcBakingBeans
	 */
	@SuppressWarnings("unchecked")
	public List<BandBakingBean> getBands() {
		List<Band> bandEntityBeans = myT2RManager.getBands();
		if (bandEntityBeans == null) {
			return new ArrayList();
		}
		List bandBakingBeans = new ArrayList();
		for (Band bandEBean : bandEntityBeans) {
			BandBakingBean bandBBean = new BandBakingBean(bandEBean.getId(),
					bandEBean.getName());
            logger.debug("processing band bbean " + bandEBean.getName());
			Set<Musiker> musikerList = bandEBean.getMusiker();
			List musikerNamen = new ArrayList();
			if ((musikerList != null) && (!musikerList.isEmpty())) {
				logger.debug("musiker list is not empty and not null");
				for (Musiker musiker : musikerList) {
				  logger.debug("adding musiker name to band: " + musiker.getName() );
                  musikerNamen.add(musiker.getName());
				}
			}            
			bandBBean.setMusikerNamensListe(musikerNamen);
			bandBakingBeans.add(bandBBean);
		}
		return bandBakingBeans;
	}

	/**
	 * Selektiere die Band mit einem gegebenen Namen
	 * 
	 * @param name
	 * @return BandBackingBean
	 */
	public BandBakingBean getBandByName(String name) {
		Band band = myT2RManager.getBandByName(name);
		if (band != null) {
			return new BandBakingBean(band.getId(), band.getName());
		}
		return null;
	}

	public void createBand(BandBakingBean bandBackingBean) {
		Band band = new Band();
		band.setName(bandBackingBean.getName());
		this.myT2RManager.createBand(band);
	}

	public void updateBand(BandBakingBean bandBackingBean) {
		Band band = new Band();
		band.setId(bandBackingBean.getId());
		band.setName(bandBackingBean.getName());
		this.myT2RManager.updateBand(band);

	}

	public void deleteBand(BandBakingBean bandBackingBean) {
		Band band = new Band();
		band.setId(bandBackingBean.getId());
		band.setName(bandBackingBean.getName());
		myT2RManager.deleteBand(band);

	}
}
