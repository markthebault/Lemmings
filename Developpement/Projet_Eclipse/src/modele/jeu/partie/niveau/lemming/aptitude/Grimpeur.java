package modele.jeu.partie.niveau.lemming.aptitude;


import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.carte.Case;
import modele.jeu.partie.niveau.lemming.Direction;
import modele.jeu.partie.niveau.lemming.Lemming;
import modele.jeu.partie.niveau.lemming.Position;
import modele.jeu.partie.niveau.lemming.SensDeplacement;

public class Grimpeur extends Marcheur {

	/**
	 * le parachute du grimpeur s'il en a un
	 */
	private Parachutiste parachute;
	
	/**
	 * Parametre utiliser pour gerer la vitesse du grimpeur
	 */
	private static final int FREQ_MONTEE = 2;
	private int compteurMonte;

	/**
	 * S�mantique : Cr�e un Grimpeur
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param lemming
	 * @param carte
	 */
	public Grimpeur(Lemming lemming, Carte carte) {
		super(lemming, carte);
		this.compteurMonte = 0;
	}

	/**
	 * S�mantique : Appelle avancer du marcheur
	 * Pr�conditions :
	 * Postconditions :
	 */
	protected void avancer()
	{
		super.avancer();
		
		if(this.parachute != null)
		{
			this.parachute.reinitCompteurChute();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.jeu.partie.niveau.lemming.aptitude.Marcheur#actionMur()
	 */
	@Override
	protected void actionMur() {
		//regule la vitesse de montee
		if(this.compteurMonte >= FREQ_MONTEE )
		{
			//recupere la direction du lemmings
			Direction dir = this.lemming.getDirectionActuelle();
			
			//recupere la case d'au dessus du lemmings
			Case auDessus = this.carte.getCase(Position.translater(
					dir.getPosition(),
					Direction.sensVector(SensDeplacement.VERS_HAUT)));
			
			if (auDessus != null) {//si au dessus il n'y a pas de case
				this.grimpe = false;
				
				//on touche le plafond
				if(!this.gererCaseParticuliere(auDessus))
				{
					//si le plafond n'est pas une case speciale
					this.actionPlafond();
				}
			} else {
				
				//monter vers le haut !!!!
				this.seDeplacer(Direction.sensVector(SensDeplacement.VERS_HAUT));
				this.grimpe = true;
			}
			this.compteurMonte = 0;
		}
		else
		{
			this.compteurMonte++;
		}
	}

	/**
	 * S�mantique : Quand le lemming rencontre le plafon il change de sens
	 * Pr�conditions : lemming != null
	 * Postconditions : lemming a change de sens
	 */
	protected void actionPlafond() {
		this.lemming.getDirectionActuelle().changerDeSens();
		this.compteurMonte = 0;
	}

	/**
	 * S�mantique : R�cup�re le parachute
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @return le parachute, null si aucun parachute
	 */
	public Parachutiste getParachute() {
		return this.parachute;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.aptitude.Marcheur#getType()
	 */
	@Override
	public TypeAptitude getType() {
		return TypeAptitude.CLIMBER;
	}

	/**
	 * S�mantique : Modifie le parachutiste
	 * Pr�conditions : n'a aucun parachute avant
	 * Postconditions :
	 * 
	 * @param parachute
	 */
	public void setParachute(Parachutiste parachute) {
		this.parachute = parachute;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.jeu.partie.niveau.lemming.aptitude.Marcheur#tomber()
	 */
	@Override
	protected void tomber() {
		if(this.getParachute() != null)
		{
			this.parachute.executer();
		}
		else
		{
			super.tomber();
		}
	}
	
	public Boolean estEntrainDeGrimper()
	{
		return this.grimpe;
	}

}