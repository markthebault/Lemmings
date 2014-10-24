package modele.jeu.partie.niveau.lemming.aptitude;

import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.carte.Case;
import modele.jeu.partie.niveau.lemming.Direction;
import modele.jeu.partie.niveau.lemming.Lemming;
import modele.jeu.partie.niveau.lemming.Position;
import modele.jeu.partie.niveau.lemming.SensDeplacement;

public class Mineur extends ApModifieur {

	/**
	 * S�mantique : Cr�e un Mineur
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param lemming
	 * @param carte
	 */
	public Mineur(Lemming lemming, Carte carte) {
		super(lemming, carte);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.aptitude.Aptitude#getType()
	 */
	@Override
	public TypeAptitude getType() {
		return TypeAptitude.MINER;
	}

	/* (non-Javadoc)
	 * @see modele.jeu.partie.niveau.lemming.aptitude.ApModifieur#modifier()
	 */
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
			// je regarde en face en bas
			Position diagonaleLem = Position.translater(
					Position.translater(lemDir.getPosition(),
							Direction.sensVector(lemDir.getSens())),
					Direction.sensVector(SensDeplacement.VERS_BAS));

			Case caseEnFace = this.carte.getCase(enFaceLem); // la case en face
			// la case en diagonale
			Case caseEnDiagonale = this.carte.getCase(diagonaleLem);

			boolean caseEnfaceMinable = true;

			// on mine si il y a au moins une case en diagonale
			if (caseEnDiagonale == null) {
				// on perd l'aptitude mineur si on peut pas miner
				this.lemming.enleverAptitude();
			} else {
				// on essaye de miner la case en face
				if (caseEnFace != null) {
					// on peut esperer miner, si les cases ne sont pas
					// indestructibles
					switch (caseEnFace.getSensDestruction()) {
						case TOUT:
							// on detruit la case
							this.carte.enleverCase(enFaceLem);
							this.lemming.getDirectionActuelle().setPosition(
									enFaceLem);
							break;
						case VERS_GAUCHE:
							// on detruit la case
							if (lemDir.getSens() == SensDeplacement.VERS_GAUCHE) {
								this.carte.enleverCase(enFaceLem);
								this.lemming.getDirectionActuelle()
										.setPosition(enFaceLem);
							} else {
								// la case en face n'est pas minable
								caseEnfaceMinable = false;
							}
							break;
						case VERS_DROITE:
							// on detruit la case
							if (lemDir.getSens() == SensDeplacement.VERS_DROITE) {
								this.carte.enleverCase(enFaceLem);
								this.lemming.getDirectionActuelle()
										.setPosition(enFaceLem);
							} else {
								// la case en face n'est pas minable
								caseEnfaceMinable = false;
							}
							break;
						default:
							// la case en face n'est pas minable
							caseEnfaceMinable = false;
					}
				}
				// post : case en face minable et donc nulle (avant ou min�e) ||
				// case non nulle

				// On ne mine la case en diagonale ssi la case en face est
				// minable
				if (caseEnfaceMinable) {
					// on mine seulement si la case en face etait minable ou
					// nulle
					// on essaye de miner la case en face en bas
					switch (caseEnDiagonale.getSensDestruction()) {
						case TOUT:
							// on detruit la case
							this.carte.enleverCase(diagonaleLem);
							// on se deplace sur celle-ci
							this.lemming.getDirectionActuelle().setPosition(
									diagonaleLem);
							break;
						case VERS_BAS:
							// on detruit la case
							this.carte.enleverCase(diagonaleLem);
							// on se deplace sur celle-ci
							this.lemming.getDirectionActuelle().setPosition(
									diagonaleLem);
							break;
							// Prise en compte des cases destructible vers la droite et vers la gauche
						case VERS_GAUCHE:
							// on detruit la case
							if (lemDir.getSens() == SensDeplacement.VERS_GAUCHE) {
								this.carte.enleverCase(diagonaleLem);
								this.lemming.getDirectionActuelle()
										.setPosition(diagonaleLem);
							} else {
								// la case en diagonale n'est pas minable perte de l'aptitude
								this.lemming.enleverAptitude();
							}
							break;
						case VERS_DROITE:
							// on detruit la case
							if (lemDir.getSens() == SensDeplacement.VERS_DROITE) {
								this.carte.enleverCase(diagonaleLem);
								this.lemming.getDirectionActuelle()
										.setPosition(diagonaleLem);
							} else {
								// la case en diagonale n'est pas minable perte de l'aptitude
								this.lemming.enleverAptitude();
							}
							break;
						default:
							// on perd l'aptitude mineur si on peut pas miner
							this.lemming.enleverAptitude();
							break;
					}
				} else {
					// on perd l'aptitude mineur si on peut pas miner
					this.lemming.enleverAptitude();
				}
			}
		}
	}
}