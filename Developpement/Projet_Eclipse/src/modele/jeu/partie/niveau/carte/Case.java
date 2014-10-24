package modele.jeu.partie.niveau.carte;


public class Case {
	/**
	 * est grimpable
	 */
	private boolean grimpable;

	/**
	 * le sens de destruction
	 */
	private SensDestruction sensDestruction;

	/**
	 * S�mantique : Cr�e un Case non destructible et grimpable
	 * Pr�conditions :
	 * Postconditions :
	 */
	public Case() {
		this.grimpable = true;
		this.sensDestruction = SensDestruction.AUCUN;
	}

	/**
	 * S�mantique : Cr�e une Case non destructible et grimpable ou pas
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param grimpable
	 *            est grimpable ou pas
	 */
	public Case(boolean grimpable) {
		this.grimpable = grimpable;
		this.sensDestruction = SensDestruction.AUCUN;
	}

	/**
	 * S�mantique : Cr�e une Case grimpable et dont le choix du sens de
	 * destruction est libre
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param sens
	 *            le sens de destruction
	 */
	public Case(SensDestruction sens) {
		this.grimpable = true;
		this.sensDestruction = sens;
	}

	/**
	 * S�mantique : Indique si la case est destructible dans le sens sp�cifi�.
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param sens
	 * @return true si destructible dans sens, false sinon. Renvoie toujours
	 *         false si la case est non destructible
	 */
	public boolean estDestructible(SensDestruction sens) {
		if (sens == SensDestruction.AUCUN) {
			return false;
		} else {
			return sens == this.sensDestruction;
		}
	}

	/**
	 * S�mantique : Indique si la case est grimpable
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @return true si la case est grimpable, false sinon
	 */
	public boolean estGrimpable() {
		return this.grimpable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		switch (this.sensDestruction) {
			case TOUT:
				return "+";
			case VERS_BAS:
				return "v";
			case VERS_DROITE:
				return ">";
			case VERS_GAUCHE:
				return "<";
			case AUCUN:
				return "*";
			default:
				return "?";
		}
	}
	
	public SensDestruction getSensDestruction (){
		return this.sensDestruction;
	}
}