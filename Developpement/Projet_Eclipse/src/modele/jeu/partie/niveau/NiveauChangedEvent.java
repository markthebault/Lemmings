/**
 * 
 */
package modele.jeu.partie.niveau;

import java.util.ArrayList;
import java.util.EventObject;

import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.lemming.Lemming;

/**
 * @author Paul
 * 
 */
public class NiveauChangedEvent extends EventObject {

	private Carte carte;
	private ArrayList<Lemming> lemmings;

	/**
	 * Sémantique : Crée un NiveauChangedEvent
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param arg0
	 */
	public NiveauChangedEvent(Object source, Carte carte,
			ArrayList<Lemming> lemmings) {
		super(source);

		this.carte = carte;
		this.lemmings = lemmings;
	}

	/**
	 * Récupère le carte
	 * 
	 * @return le carte
	 */
	public Carte getCarte() {
		return this.carte;
	}

	/**
	 * Récupère le lemmings
	 * 
	 * @return le lemmings
	 */
	public ArrayList<Lemming> getLemmings() {
		return this.lemmings;
	}

}
