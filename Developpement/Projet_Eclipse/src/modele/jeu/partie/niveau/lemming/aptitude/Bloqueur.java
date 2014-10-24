package modele.jeu.partie.niveau.lemming.aptitude;

import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.carte.Case;
import modele.jeu.partie.niveau.carte.CaseDejaPresenteException;
import modele.jeu.partie.niveau.lemming.Lemming;

public class Bloqueur extends ApModifieur {

	/**
	 * bloque indique si le lemming est bloqueur
	 */
	private boolean bloque;

	/**
	 * S�mantique : Cr�e un Bloqueur
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param lemming
	 * @param carte
	 */
	public Bloqueur(Lemming lemming, Carte carte) {
		super(lemming, carte);
		this.bloque = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.jeu.partie.niveau.lemming.aptitude.ApModifieur#executer()
	 */
	@Override
	public void executer() {
		super.executer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.aptitude.Aptitude#getType()
	 */
	@Override
	public TypeAptitude getType() {
		return TypeAptitude.BLOCKER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.jeu.partie.niveau.lemming.aptitude.ApModifieur#modifier()
	 */
	@Override
	public void modifier() {
		if (!this.bloque) {
			//si l bloque n est pas a vrai on l active
			this.bloque = true;
			//et on ajoute une case indestructible et non grimpable a la position du lemming
			try {
				this.carte.ajouterCase(this.lemming.getDirectionActuelle()
						.getPosition(), new Case(false));
			}
			// exception pas lev�e
			catch (CaseDejaPresenteException e) {
				e.printStackTrace();
			}
		}
	}

}