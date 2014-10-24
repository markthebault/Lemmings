package modele.jeu;

import java.util.ArrayList;

import modele.jeu.partie.Partie;

public class Jeu {
	/**
	 * les parties
	 */
	private ArrayList<Partie> parties = new ArrayList<Partie>();

	/**
	 * le partieCourante
	 */
	private int partieCourante;

	/**
	 * Sémantique : Cree le jeu nu
	 * paramettre
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param fic
	 */
	public Jeu() {
		this.parties = new ArrayList<Partie>();
		this.partieCourante = -1;
	}

	/**
	 * Sémantique : ajoute une partie et la définie comme partie courante
	 * Préconditions :
	 * Postconditions : la partie est ajouté
	 */
	public Partie ajouterPartie() {
		Partie partieAjoutee = new Partie();
		this.parties.add(partieAjoutee);
		this.partieCourante = this.parties.indexOf(partieAjoutee);
		return partieAjoutee;
	}

	/**
	 * Sémantique : met a jour la partie
	 * Préconditions : partie != null
	 * Postconditions : partie a jour
	 */
	public void miseAJour() {
		// met a jour la partie courante
		if (this.parties.size() > 0) {
			// ajouter le niveau a la partie courante
			this.parties.get(this.partieCourante).miseAJour();
		}
	}

	/**
	 * Sémantique : quitte le programme
	 * Préconditions :
	 * Postconditions : fin du programme
	 */
	public void quitter() {
		System.exit(0);
	}
}