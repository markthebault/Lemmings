package modele.jeu.partie.niveau.lemming.aptitude;

import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.lemming.Lemming;

public abstract class ApModifieur extends ApNormale {

	// Compteur qui permet de gerer la vitesse des aptitudes
	protected int compteur;
	protected static int COMPTEUR_MAX = 2;

	/**
	 * S�mantique : Cree une aptitude qui va modifier la carte
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param lemming
	 * @param carte
	 */
	public ApModifieur(Lemming lemming, Carte carte) {
		super(lemming, carte);
		this.compteur = 0;
	}

	@Override
	public void executer() {
		super.executer();
		// gestion de la vitesse des aptitudes qui herite de ApModifieur
		if (this.compteur <= COMPTEUR_MAX) {
			this.compteur++;
		}
		else {
			this.modifier();
			this.compteur = 0;
		}
	}

	public abstract void modifier();

}
