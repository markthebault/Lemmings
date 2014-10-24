package modele.jeu.partie.niveau.lemming.aptitude;

import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.lemming.Direction;
import modele.jeu.partie.niveau.lemming.Lemming;
import modele.jeu.partie.niveau.lemming.SensDeplacement;

public class Parachutiste extends Marcheur {
	/**
	 * le grapin du parachutiste s'il en a un qui indique si il est aussi grimpeur
	 */
	private Grimpeur grappin;
	
	/**
	 * indice de reduction de la vitesse de chute
	 */
	private static final int FREQ_TOMBER = 2;
	
	/**
	 * Permet de ralentir la chute apres 3 cases de chute 
	 */
	private static final int NB_CASES_AV_SORTIR_PARA = 3;
	
	private int compteurTomber;

	/**
	 * S�mantique : Cr�e un Parachutiste
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param lemming
	 * @param carte
	 */
	public Parachutiste(Lemming lemming, Carte carte) {
		super(lemming, carte);
		this.compteurTomber = 0;
	}

	/**
	 * S�mantique : R�cup�re le grapin
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @return le grapin, null si aucun grapin
	 */
	public Grimpeur getGrappin() {
		return this.grappin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.aptitude.Marcheur#getType()
	 */
	@Override
	public TypeAptitude getType() {
		return TypeAptitude.FLOATER;
	}

	/**
	 * S�mantique : Modifie le grapin
	 * Pr�conditions : n'a aucun grapin
	 * Postconditions :
	 * 
	 * @param grapin
	 *            le grapin
	 */
	public void setGrappin(Grimpeur grappin) {
		this.grappin = grappin;
	}
	
	/**
	 * S�mantique : Realise l action de tomber du lemming ayant un parachute et un grappin
	 * Pr�conditions : 
	 * Postconditions :
	 * 
	 */
	protected void tomber()
	{
		//si on etait entrain de grimper alors on continue
		if(this.grappin != null && this.grappin.estEntrainDeGrimper())
		{
			this.grappin.executer();
		}
		else //sinon c'est qu'on tomber reelement donc on tombe !
		{
			super.estEntrainDeTomber = true;
			
			super.nbCasesChute++;
		
			//On sors le parachute apres un certain nombre de case
			if(this.nbCasesChute > NB_CASES_AV_SORTIR_PARA)
			{
				//sortir parachute
				super.nbCasesChute = NB_CASES_AV_SORTIR_PARA;
				
				//on tombe doucement ...
				if(this.compteurTomber >= FREQ_TOMBER)
				{
					this.compteurTomber = 0;
					
					// ... vers le bas
					this.seDeplacer(Direction.sensVector(SensDeplacement.VERS_BAS));
				}
				else
				{
					this.compteurTomber++;
				}
			}
			else
			{
				//si on a pas sorti le parachute on tombe
				this.seDeplacer(Direction.sensVector(SensDeplacement.VERS_BAS));
			
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see modele.jeu.partie.niveau.lemming.aptitude.Marcheur#actionMur()
	 */
	protected void actionMur() {
		
		if(this.grappin != null)
		{
			//si on a des grappins on mote avec
			this.grappin.executer();
		}
		else
		{
			//sinon on fais demi tour normalement
			super.actionMur();
		}
	}
	
	/**
	 * S�mantique : remet a zero le compteur de chute du parachutite
	 * Pr�conditions :
	 * Postconditions :
	 */
	public void reinitCompteurChute()
	{
		this.nbCasesChute = 0;
	}
}