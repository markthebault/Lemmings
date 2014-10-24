package modele.jeu.partie;

import java.io.File;
import java.util.ArrayList;

import modele.jeu.FichierInvalideException;
import modele.jeu.partie.niveau.Niveau;

public class Partie {

	private boolean finie;

	/**
	 * le niveaux
	 */
	private ArrayList<Niveau> niveaux = new ArrayList<Niveau>();

	/**
	 * le niveauCourrant
	 */
	private int niveauCourrant;

	/**
	 * Sémantique : Initiqlise une Partie
	 * Préconditions :
	 * Postconditions : partie initialisée
	 */
	public Partie() {
		this.niveaux = new ArrayList<Niveau>();
		this.niveauCourrant = 0;
	}

	/**
	 * Sémantique : ajoute le niveau correspondant au fichier
	 * Préconditions : fichier correspondant a la semantique du niveau
	 * Postconditions : un niveau crée correspondant au fichier
	 * 
	 * @param fic
	 */
	public Niveau ajouterNiveau(File fichierNiveau)
			throws FichierInvalideException {
		Niveau niveau = new Niveau(fichierNiveau);
		this.niveaux.add(niveau);
		return niveau;
	}

	/**
	 * Récupère le finie
	 * 
	 * @return le finie
	 */
	public boolean isFinie() {
		return this.finie;
	}

	/**
	 * Sémantique : met a jour le niveau courrant
	 * Préconditions : au moin un niveau initialisé
	 * Postconditions : niveau courrant a jour
	 */
	public void miseAJour() {
		// if(this.niveaux.size() > 0)
		{
			this.niveaux.get(this.niveauCourrant).miseAJour();
		}
	}

	/**
	 * Sémantique : passe au niveau suivant si il y en a d'autres apres
	 * Préconditions : au moins un niveau initialisé
	 * Postconditions : on est passé au niveau suivant
	 */
	public void passerAuNiveauSuivant() {
		if (this.niveauCourrant < (this.niveaux.size() - 1)) {
			this.niveauCourrant++;
		} else {
			this.finie = true;
			this.niveauCourrant = 0;
		}
	}
}