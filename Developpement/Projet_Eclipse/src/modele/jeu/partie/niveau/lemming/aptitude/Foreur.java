package modele.jeu.partie.niveau.lemming.aptitude;

import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.carte.Case;
import modele.jeu.partie.niveau.lemming.Direction;
import modele.jeu.partie.niveau.lemming.Lemming;
import modele.jeu.partie.niveau.lemming.Position;
import modele.jeu.partie.niveau.lemming.SensDeplacement;

public class Foreur extends ApModifieur {
	
	/**
	 * S�mantique : Cr�e un Foreur
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param lemming
	 * @param carte
	 */
	public Foreur(Lemming lemming, Carte carte) {
		super(lemming, carte);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.aptitude.Aptitude#getType()
	 */
	@Override
	public TypeAptitude getType() {
		return TypeAptitude.BASHER;
	}

	@Override
	public void modifier() {
		// je regarde la case en dessous du lemmings
		Position lemPos = this.lemming.getDirectionActuelle().getPosition();
		Position dessousLem = Position.translater(lemPos, 
				Direction.sensVector(SensDeplacement.VERS_BAS));
		
		Case caseDessousLem = this.carte.getCase(dessousLem);
		//Si une case est présente on va regarder ce que nous faisons
		if (caseDessousLem != null){
			switch (caseDessousLem.getSensDestruction()){
			case AUCUN :
				// perte de l'aptitude creuseur
				this.lemming.enleverAptitude();
				break;
			case TOUT :
				//Destruction de la case et déplacement sur la case du dessous
				this.carte.enleverCase(dessousLem);
				this.lemming.getDirectionActuelle().setPosition(dessousLem);
				break;
			case VERS_BAS :
				this.carte.enleverCase(dessousLem);
				this.lemming.getDirectionActuelle().setPosition(dessousLem);
				break;
			case VERS_DROITE :
				// perte de l'aptitude creuseur
				this.lemming.enleverAptitude();
				break;
			case VERS_GAUCHE :
				// perte de l'aptitude creuseur
				this.lemming.enleverAptitude();
				break;
				default:
					// perte de l'aptitude creuseur
					this.lemming.enleverAptitude();
					break;
			}
		}
		// sinon il n'y a pas de case donc le lemming perd foreur et tombe
		else{
			// perte de l'aptitude creuseur
			this.lemming.enleverAptitude();
		}
	}
}