package modele.jeu.partie.niveau.lemming.aptitude;

import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.carte.Case;
import modele.jeu.partie.niveau.carte.CaseDejaPresenteException;
import modele.jeu.partie.niveau.carte.SensDestruction;
import modele.jeu.partie.niveau.lemming.Direction;
import modele.jeu.partie.niveau.lemming.Lemming;
import modele.jeu.partie.niveau.lemming.Position;
import modele.jeu.partie.niveau.lemming.SensDeplacement;

public class Constructeur extends ApModifieur {
	/**
	 * le nbMarchesConstruites
	 */
	private int nbMarchesConstruites;
	
	
	/**
	 * le nbMarcheAConstruire
	 */
	private static int nbMarcheAConstruire = 4;
	

	/**
	 * S�mantique : Cr�e un Constructeur
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param lemming
	 * @param carte
	 */
	public Constructeur(Lemming lemming, Carte carte) {
		super(lemming, carte);
		this.nbMarchesConstruites = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.aptitude.Aptitude#getType()
	 */
	@Override
	public TypeAptitude getType() {
		return TypeAptitude.BUILDER;
	}

	@Override
	public void modifier() {
		//si la case au dessous est vide on perd l aptitude
		Position posLem = this.lemming.getDirectionActuelle().getPosition();
		Position posDessousLem = Position.translater(posLem, Direction.sensVector(SensDeplacement.VERS_BAS));
		Position posDevantLem = Position.translater(posLem, Direction.sensVector(this.lemming.getDirectionActuelle().getSens()));
		Position posDevHautLem = Position.translater(posDevantLem, Direction.sensVector(SensDeplacement.VERS_HAUT));
		//perte de l'aptitude si case vide en dessous du lemming
		if (this.carte.getCase(posDessousLem) == null){
			this.lemming.enleverAptitude();
		}
		// si il reste des cases on peut construire
		else if (nbMarchesConstruites < nbMarcheAConstruire) {
			// On ajoute une marche devant le lemming si deja presente ==> exception
			try {
				this.carte.ajouterCase(posDevantLem, new Case (SensDestruction.TOUT));
				this.nbMarchesConstruites += 1;
				// si la case au dessus de celle que nous venons de créer est vide on monte
				if (this.carte.getCase(posDevHautLem) == null){
					this.lemming.getDirectionActuelle().setPosition(posDevHautLem);
				}
			}
			catch (CaseDejaPresenteException e){
				System.out.println("Construiction impossible : "+e.getMessage());
				this.lemming.enleverAptitude();
			}
		}
		//sinon (plus de marche ou impossiblité de monter) on perd l aptitude
		else {
			this.lemming.enleverAptitude();
		}
	}
}