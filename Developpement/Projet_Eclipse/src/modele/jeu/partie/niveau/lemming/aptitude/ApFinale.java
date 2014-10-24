package modele.jeu.partie.niveau.lemming.aptitude;

import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.lemming.Lemming;

/**
 * @author Paul
 *         Cette classe permet de spécifier un comportement différent.
 *         Tous les classes spécialisant celle-ci deviendront des aptitudes
 *         finales, cad qu'aucune autre aptitude, excepté spéciale, ne pourra
 *         leur etre attribué.
 */
public abstract class ApFinale extends ApNormale {

	/**
	 * Sémantique : Crée une aptitude finale
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param lemming
	 * @param carte
	 */
	public ApFinale(Lemming lemming, Carte carte) {
		super(lemming, carte);
	}
}