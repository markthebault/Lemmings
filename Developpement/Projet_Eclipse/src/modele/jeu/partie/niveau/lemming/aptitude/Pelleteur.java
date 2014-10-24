package modele.jeu.partie.niveau.lemming.aptitude;

import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.carte.Case;
import modele.jeu.partie.niveau.lemming.Direction;
import modele.jeu.partie.niveau.lemming.Lemming;
import modele.jeu.partie.niveau.lemming.Position;
import modele.jeu.partie.niveau.lemming.SensDeplacement;

public class Pelleteur extends ApModifieur {

	/**
	 * SÈmantique : CrÈe un Pelleteur
	 * PrÈconditions :
	 * Postconditions :
	 * 
	 * @param lemming
	 * @param carte
	 */
	public Pelleteur(Lemming lemming, Carte carte) {
		super(lemming, carte);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.aptitude.Aptitude#getType()
	 */
	@Override
	public TypeAptitude getType() {
		return TypeAptitude.DIGGER;
	}

	@Override
	public void modifier() {
		// la direction du lemming
		Direction lemDir = this.lemming.getDirectionActuelle();

		// on regarde si le lemming est arrive dans un trou
		Position enBasLem = Position.translater(lemDir.getPosition(),
				Direction.sensVector(SensDeplacement.VERS_BAS));
		Case caseEnBas = this.carte.getCase(enBasLem);
		if (caseEnBas == null) {
			// le lemming est au dessus d'un trou, il perd donc l'aptitude et
			// tombe
			this.lemming.enleverAptitude();
			this.lemming.getDirectionActuelle().setPosition(enBasLem);
		} else {
			// je regarde la case en face du lemmings
			Position enFaceLem = Position.translater(lemDir.getPosition(),
					Direction.sensVector(lemDir.getSens()));

			Case caseEnFace = this.carte.getCase(enFaceLem);
			// Si une case est pr√©sente on va regarder ce que nous faisons
			if (caseEnFace != null) {
				switch (caseEnFace.getSensDestruction()) {
					case TOUT:
						// Destruction de la case et d√©placement sur la case en
						// face
						this.carte.enleverCase(enFaceLem);
						this.lemming.getDirectionActuelle().setPosition(
								enFaceLem);
						break;
					case VERS_GAUCHE:
						// Destruction de la case et d√©placement sur la case en
						// face
						if (lemDir.getSens() == SensDeplacement.VERS_GAUCHE) {
							this.carte.enleverCase(enFaceLem);
							this.lemming.getDirectionActuelle().setPosition(
									enFaceLem);
						}
						break;
					case VERS_DROITE:
						// Destruction de la case et d√©placement sur la case en
						// face
						if (lemDir.getSens() == SensDeplacement.VERS_DROITE) {
							this.carte.enleverCase(enFaceLem);
							this.lemming.getDirectionActuelle().setPosition(
									enFaceLem);
						}
						break;
					default:
						// perte de l'aptitude pelleteur
						this.lemming.enleverAptitude();
						break;
				}
			}
			// sinon il n'y a pas de case donc le lemming perd pelleteur et
			// tombe
			else {
				// perte de l'aptitude pelleteur
				this.lemming.enleverAptitude();
			}
		}
	}
}