package de.ejb3buch.ticket2rock.applikation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Band Baking Bean f�r die Verwendung im JSF-Frameworks
 * @author Jochen J�rg
 */
public class BandBackingBean {
   
	private Integer id = null;
	private String name = null;;
	private Collection<String> musikerNamensListe = null;;
	private Collection<String> musikerIdListe = null;
	
	public BandBackingBean () {

	}
	
	public BandBackingBean (Integer aId, String aName) {
		id = aId;
		name = aName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}


	public void setMusikerNamensListe(Collection<String> musikerNamen) {
		this.musikerNamensListe = musikerNamen;		
	}

	public Collection<String> getMusikerNamensListe() {
		return musikerNamensListe;
	}
	
	public List<String> getMusikerListeKommaGetrennt() {
		
		if  (musikerNamensListe == null) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		Iterator iter = musikerNamensListe.iterator();
		boolean isFirst = true;
		while (iter.hasNext()) {
			if (isFirst) {
				list.add((String)iter.next());
				isFirst = false;
			}
			else {
				list.add(", " + (String) iter.next());
			}			
		}
		return list;
	}

	public Collection<String> getMusikerIdListe() {
		return musikerIdListe;
	}

	public void setMusikerIdListe(Collection<String> musikerIdListe) {
		this.musikerIdListe = musikerIdListe;
	}

	
}
