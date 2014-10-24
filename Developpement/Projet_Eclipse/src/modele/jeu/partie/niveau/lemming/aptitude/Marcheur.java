package modele.jeu.partie.niveau.lemming.aptitude;

import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.carte.Case;
import modele.jeu.partie.niveau.carte.Sortie;
import modele.jeu.partie.niveau.lemming.Direction;
import modele.jeu.partie.niveau.lemming.Lemming;
import modele.jeu.partie.niveau.lemming.Position;
import modele.jeu.partie.niveau.lemming.SensDeplacement;

public class Marcheur extends ApNormale {


	protected boolean grimpe;
	/**
	 * le nbCasesChute
	 */
	protected int nbCasesChute;
	protected boolean estEntrainDeTomber;

	protected static final int HAUTEUR_CHUTE_FATALE = 6;

	/**
	 * S�mantique : Cr�e un Marcheur
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param lemming
	 * @param carte
	 */
	public Marcheur(Lemming lemming, Carte carte) {
		super(lemming, carte);

		// initialisation du nombre de casses chut�es
		this.nbCasesChute = 0;
		this.estEntrainDeTomber = false;

	}

	/**
	 * S�mantique : quand un marcheur rencotre un mur, il change de sens
	 * Pr�conditions : le lemmings rencotre un mur
	 * Postconditions : le sens du lemmings a change
	 */
	protected void actionMur() {
		this.lemming.getDirectionActuelle().changerDeSens();
	}

	/**
	 * S�mantique : Permet au lemmings d'avancer d'une case, monter une case,
	 * faire demi-tour, mourrir s'il rencotre un piege
	 * Pr�conditions :
	 * Postconditions : le lemmings a chang� d'�tat
	 * 
	 */
	protected void avancer() {
		// --->r�cup�rer le prochaines cases du terrin selon la direction du
		// lemmings
		// recuperer la direction (pos + sens) du lemmings
		Direction dir = this.lemming.getDirectionActuelle();
		SensDeplacement sens = dir.getSens();

		// recuperer la case en face du lemmings
		Case enFace = this.carte.getCase(Position.translater(dir.getPosition(),
				Direction.sensVector(sens)));

		// recuprer la au dessus de la case en face
		Case auDessusDenFace = this.carte.getCase(Position.translater(dir
				.getPosition(), Position.translater(Direction.sensVector(sens),
				new Position(0, -1))));

		// si le chemain est libre on avance
		if (enFace == null ) {
			this.seDeplacer(Direction.sensVector(sens));
		} else {
			// essaye de gerer les cases particulieres (piege, sortie)
			if (!this.gererCaseParticuliere(enFace)) {

				// regarde si on rencotre un mur
				if (auDessusDenFace != null) {
					
					//si la case au dessus en face est une sortie
					if (auDessusDenFace instanceof Sortie)
					{
						//monter dans la sortie
						this.lemming.sortir();
					}
					else
					{
						// on a rencontre un mu, on fais quelque chose
						this.actionMur();
					}
				} 
				else {
				
					// on regarde si la case d'en face est un pi�ge

					// regarde si on peut monter le mur, sinon demitour
					if (enFace.estGrimpable()) {
						// monter par dessus la case
						Position ps = Direction.sensVector(this.lemming
								.getDirectionActuelle().getSens());

						this.seDeplacer(Position.translater(ps, new Position(0,
								-1)));
					} else {
						// si on peut pas monter, ben alors on fais demi-tour
						this.lemming.getDirectionActuelle().changerDeSens();
					}

				}
			}
		}

	}

	/**
	 * S�mantique :
	 * Marche si le lemmings touche le sol,
	 * tombe s'il n'y a pas de sol
	 * change de sens l'orque le lemmings rencontre un obstacle
	 * monte d'une case s'il n'y a qu'une seulle case devant lui
	 * 
	 * Pr�conditions : lemmings et carte sont initialis�s
	 * Postconditions : position du lemmings a chang�
	 */
	@Override
	public void executer() {
		super.executer();
		// --->r�cup�rer le prochaines cases du terrin selont la direction du
		// lemmings
		// recuperer la direction (pos + sens) du lemmings
		Direction dir = this.lemming.getDirectionActuelle();

		// recuprer la case en dessous du lemmings
		Position p = Position.translater(dir.getPosition(), new Position(0, 1));
		Case enDessous = this.carte.getCase(p);

		// regarder si on tombe
		if ((enDessous == null) && !this.grimpe) {
			this.tomber();
		}

		// on tante de g�rer les cases particuli�res, si ce n'est pas
		// une case particulire on effectue le triatement
		else if (!this.gererCaseParticuliere(enDessous)) {
			// si on est tombe depuis trop longtemps on tue le lemmings
			if (this.nbCasesChute >= Marcheur.HAUTEUR_CHUTE_FATALE) {
				this.lemming.tuer();
			} else {
				// si tout est ok alors on avance
				this.nbCasesChute = 0;
				this.estEntrainDeTomber = false;
				this.avancer();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.aptitude.Aptitude#getType()
	 */
	@Override
	public TypeAptitude getType() {
		return TypeAptitude.WALKER;
	}

	/**
	 * S�mantique : vas vers la case de la direction
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 */
	protected void seDeplacer(Position direction) {

		Position p = this.lemming.getDirectionActuelle().getPosition();
		p.setX(p.getX() + direction.getX());
		p.setY(p.getY() + direction.getY());
		this.grimpe = false;
	}

	/**
	 * S�mantique : tombe d'une case
	 * Pr�conditions :
	 * Postconditions : lemmings est une case plus bas et son sens n'a pas
	 * chang�
	 * 
	 */
	protected void tomber() {
		// incr�menter le compteur de chute
		this.nbCasesChute++;

		// aller vers le bas
		this.seDeplacer(Direction.sensVector(SensDeplacement.VERS_BAS));
		this.grimpe = false;
		this.estEntrainDeTomber = true;

	}

	/**
	 * S�mantique : vrai si le lemming est en train de tomber
	 * Pr�conditions :
	 * Postconditions : 
	 */
	public boolean tombe() {
		return this.estEntrainDeTomber;
	}

	/**
	 * S�mantique : vrai si le lemming est en train de grimper
	 * Pr�conditions :
	 * Postconditions : 
	 */
	public boolean grimpe() {
		return this.grimpe;
	}

}