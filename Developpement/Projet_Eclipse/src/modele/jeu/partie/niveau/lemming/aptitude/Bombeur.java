package modele.jeu.partie.niveau.lemming.aptitude;

import java.util.Calendar;

import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.carte.Case;
import modele.jeu.partie.niveau.carte.SensDestruction;
import modele.jeu.partie.niveau.lemming.Lemming;
import modele.jeu.partie.niveau.lemming.Position;

public class Bombeur extends ApSpeciale {
	/**
	 * le compteARebours
	 */
	private long compteARebours;
	
	/**
	 * Va contenir l heure initiale (en ms) a laquelle nous affectons la bombe
	 */
	private long tpsInit;

	/**
	 * S�mantique : Cr�e un Bombeur
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param lemming
	 * @param carte
	 */
	public Bombeur(Lemming lemming, Carte carte) {
		super(lemming, carte);
		// on recupere le temps a l initialisation de la bombe
		this.tpsInit = Calendar.getInstance().getTimeInMillis();
		// initilaisation du compte a rebour a 5sec
		this.compteARebours = 5000;
	}

	@Override
	public void executer() {
		//si le compte à rebour est a 0 le lemmings explose
		if (Calendar.getInstance().getTimeInMillis() - this.tpsInit > this.compteARebours){
			explosion();
		}

	}

	/**
	 * R�cup�re le compteARebours
	 * 
	 * @return le compteARebours
	 */
	public long getCompteARebours() {
		return 5 - (Calendar.getInstance().getTimeInMillis() - this.tpsInit)/1000;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.aptitude.Aptitude#getType()
	 */
	@Override
	public TypeAptitude getType() {
		return TypeAptitude.BOMBER;
	}
	
	/**
	 * Va gérer l explosion du lemmings
	 * 
	 * 
	 */
	private void explosion(){
		Position posLem = this.lemming.getDirectionActuelle().getPosition();
		int xPosLem = posLem.getX();
		int yPosLem = posLem.getY();
		
		
		//Destruction des cases adjacentes au lemming
		for (int y = yPosLem -1; y <=yPosLem +1; y++){
			for (int x = xPosLem -1; x <=xPosLem +1; x++){
				//Si une case est presente
				Position posCour = new Position (x,y);
				Case c = this.carte.getCase(posCour);
				if (c != null){
					SensDestruction sDest = c.getSensDestruction();
					//detruire la case si elle est destructible
					if (sDest == SensDestruction.TOUT){
						this.carte.enleverCase(posCour);
					}
					else if (x > xPosLem && sDest == SensDestruction.VERS_DROITE) {
						this.carte.enleverCase(posCour);
					}
					else if (x < xPosLem && sDest == SensDestruction.VERS_GAUCHE) {
						this.carte.enleverCase(posCour);
					}
					else if (y > yPosLem && sDest == SensDestruction.VERS_BAS) {
						this.carte.enleverCase(posCour);
					}
					else {
						// Pas de destruction
					}
				}
			}
		}
		// Destruction du lemming
		this.lemming.tuer();
		this.carte.enleverCase(posLem);
	}

}