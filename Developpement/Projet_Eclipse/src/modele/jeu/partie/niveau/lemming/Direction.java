package modele.jeu.partie.niveau.lemming;

public class Direction {
	/**
	 * Sémantique : Convertis un sens en position
	 * Préconditions : sens valide
	 * Postconditions : translation d'une case selon le sens
	 * 
	 * @param sens
	 */
	public static Position sensVector(SensDeplacement sens) {
		Position p = null;

		switch (sens) {
			case VERS_DROITE:
				p = new Position(1, 0);
				break;
			case VERS_GAUCHE:
				p = new Position(-1, 0);
				break;
			case VERS_HAUT:
				p = new Position(0, -1);
				break;
			case VERS_BAS:
				p = new Position(0, 1);
				break;
		}

		return p;
	}

	/**
	 * le sens de deplacement
	 */
	private SensDeplacement sens;

	/**
	 * le position
	 */
	private Position position;

	/**
	 * Sémantique : Crée une Direction
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param sens
	 *            le sens de deplacement
	 * @param position
	 *            la position
	 */
	public Direction(SensDeplacement sens, Position position) {
		this.sens = sens;
		this.position = position;
	}

	/**
	 * Sémantique : Change la direction de sens
	 * Préconditions :
	 * Postconditions : le sens à changé
	 * 
	 */
	public void changerDeSens() {
		if (this.sens == SensDeplacement.VERS_DROITE) {
			this.sens = SensDeplacement.VERS_GAUCHE;
		} else if (this.sens == SensDeplacement.VERS_GAUCHE) {
			this.sens = SensDeplacement.VERS_DROITE;
		} else if (this.sens == SensDeplacement.VERS_HAUT) {
			this.sens = SensDeplacement.VERS_BAS;
		} else if (this.sens == SensDeplacement.VERS_BAS) {
			this.sens = SensDeplacement.VERS_HAUT;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object direction) {
		boolean egal = false;
		if (direction instanceof Direction) {
			Direction dir = (Direction) direction;
			egal = (this.position.equals(dir.position) && (this.sens == dir.sens));
		}
		return egal;
	}

	/**
	 * Sémantique :
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @return
	 */
	public Position getPosition() {
		return this.position;
	}

	/**
	 * Sémantique :
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @return
	 */
	public SensDeplacement getSens() {
		return this.sens;
	}

	/**
	 * Sémantique :
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param p
	 */
	public void setPosition(Position p) {
		this.position = p;
	}

	/**
	 * Sémantique :
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param sens
	 */
	public void setSens(SensDeplacement sens) {
		this.sens = sens;
	}

}