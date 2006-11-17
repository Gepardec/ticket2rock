package de.ejb3buch.ticket2rock.applikation.businessdelegate;

import java.util.List;

import de.ejb3buch.ticket2rock.applikation.helper.IViewCollectionBuilder;
import de.ejb3buch.ticket2rock.applikation.model.BandBackingBean;


public interface BandVerwaltungDelegate {
    /**
     * Selektiert alle Band-Entit�ten
     * @return Liste aller Band-Entit�ten representiert
     * durch BandBackingBeans
     */
	public List<BandBackingBean> getBands();

	/**
	 * Kreiert eine neue Band-Entit�t
	 * @param band BandBackingBean mit den Attributen der
	 * neu anzulegenden Band-Entit�t
	 */
	public void createBand(BandBackingBean band);
	
    /**
     * Selektiert eine Band-Entit�t mit einem gegebenen 
     * Bandnamen
     * @param name Name der Band
     * @return Band-Entit�t mit dem gegebenen Namen representiert
     * durch eine BandBackingBean
     */
	public BandBackingBean getBandByName(String name);

	/**
	 * Aktualisiere die Attribute der Band-Entity in der 
	 * Persistenzschicht
	 * @param bandBackingBean BandBackingBean mit den neuen 
	 * Attributen
	 */
	public void updateBand(BandBackingBean bandBackingBean);

	/**
	 * L�sche eine Band-Entit�t. 
	 * @param bandId id der zu l�schenden Band Entit�t
	 */
	public void deleteBand(Integer bandId);
	
	
    /**
     * generiere eine Kollektion der Musiker einer Band. 
     * Die Representation der Kollektion ist abh�ngig des �bergebenen
     * Builders (Anwendung des GoF Builder-Patterns)
     * @param collectionBuilder Ein Builder, der eine Kollektion erstellt, die
     * im View verwendet werden kann
     */
	public void buildMusikerCollection(IViewCollectionBuilder collectionBuilder);

    /**
     * generiere eine Kollektion der Musiker-Entit�ten. 
     * Die Representation der Kollektion ist abh�ngig des �bergebenen
     * Builders (Anwendung des GoF Builder-Patterns)
     * @param collectionBuilder Ein Builder, der eine Kollektion erstellt, die
     * im View verwendet werden kann
     */	
	public void buildBandMusikerCollection(IViewCollectionBuilder collectionBuilder,Integer bandId);
	
}
