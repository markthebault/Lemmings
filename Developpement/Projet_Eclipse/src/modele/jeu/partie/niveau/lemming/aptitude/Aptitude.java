package modele.jeu.partie.niveau.lemming.aptitude;

import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.lemming.Lemming;

public abstract class Aptitude {
	/**
	 * la carte
	 */
	protected Carte carte;

	/**
	 * le lemming
	 */
	protected Lemming lemming;

	/**
	 * Sémantique : Crée un Aptitude
	 * Préconditions : carte != null, lemming != null
	 * Postconditions : initialisation aptitude
	 * 
	 * @param lemming
	 * @param carte
	 */
	public Aptitude(Lemming lemming, Carte carte) {
		// récupere les données
		this.lemming = lemming;
		this.carte = carte;
	}

	/**
	 * Sémantique : execute l'action associé à l'aptitude
	 * Préconditions : aptitude initialisée
	 * Postconditions : action de l'aptitude
	 */
	public abstract void executer();

	public abstract TypeAptitude getType();
}