package modele.jeu.partie.niveau.lemming.aptitude;

import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.carte.Case;
import modele.jeu.partie.niveau.carte.Piege;
import modele.jeu.partie.niveau.carte.Sortie;
import modele.jeu.partie.niveau.lemming.Lemming;

public abstract class ApNormale extends Aptitude {
	/**
	 * le special
	 */
	protected ApSpeciale special;

	/**
	 * S�mantique : Cr�e un ApNormale
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param lemming
	 * @param carte
	 */
	public ApNormale(Lemming lemming, Carte carte) {
		super(lemming, carte);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.jeu.partie.niveau.lemming.aptitude.Aptitude#executer()
	 */
	@Override
	public void executer() {
		// on appele le special
		if (this.getSpecial() != null) {
			this.getSpecial().executer();
		}
	}

	/**
	 * S�mantique : gestion des cases particuli�res (pieges et sortie)
	 * Pr�conditions : c != null
	 * Postconditions : retourne vrai si on a gere la case particulire, retourne
	 * faux sinon
	 * 
	 * @param c
	 * @return
	 */
	protected boolean gererCaseParticuliere(Case c) {
		boolean aEteGere = false;

		// si on rencontre un piege
		if (c instanceof Piege) {
			aEteGere = true;
			this.rencontreUnPiege();
		}
		// si on sors de la carte
		else if (c instanceof Sortie) {
			aEteGere = true;
			this.sortiRencontree();
		}

		return aEteGere;
	}

	/**
	 * S�mantique : Permet de recuperer une aptitude speciale, comme la bombe
	 * Pr�conditions :
	 * Postconditions : retourne l aptitude bombeur ou null si pas d aptitude speciale
	 * 
	 * @return ApSpeciale
	 */
	public ApSpeciale getSpecial() {
		return this.special;
	}

	/**
	 * S�mantique : Lorsque le lemmings rencotre un pi�ge il meurt
	 * Pr�conditions :
	 * Postconditions : lemmings est tuer
	 * 
	 */
	protected void rencontreUnPiege() {
		// si un marcheur rencontre un pi�ge alors on meurt
		this.lemming.tuer();
	}

	/**
	 * S�mantique : ajoute l'aptitude sp�ciale � l'aptitude actuelle
	 * Pr�conditions : s != null, s = instanceof ApSpecial
	 * Postconditions :
	 * 
	 * @param s
	 */
	public void setSpecial(ApSpeciale s) {
		// affecte l'aptitude sp�ciale
		this.special = s;
	}

	/**
	 * S�mantique : Lorsque le lemmings rencotre une sortie
	 * Pr�conditions :
	 * Postconditions : lemmings sors de la carte
	 * 
	 */
	private void sortiRencontree() {
		this.lemming.sortir();
	}

}