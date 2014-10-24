package modele.jeu.partie.niveau.lemming;

public class Position {
	/**
	 * Sémantique : p1 est translaté avec p2
	 * Préconditions : p1 et p2 != null
	 * Postconditions : position qui est la translation de p1 avec p2
	 * 
	 * @param y
	 */
	public static Position translater(Position p1, Position p2) {
		return new Position(p1.getX() + p2.getX(), p1.getY() + p2.getY());
	}

	/**
	 * le x
	 */
	private int x;

	/**
	 * le y
	 */
	private int y;

	/**
	 * Sémantique : Crée un Position
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param x
	 * @param y
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position(Position p) {
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * Sémantique : compare la position actuelle avec une autre position
	 * Préconditions : p != null
	 * Postconditions : this == p
	 * 
	 */
	/*
	 * public boolean equals(Object p)
	 * {
	 * boolean res = false;
	 * 
	 * if(p instanceof Position)
	 * {
	 * Position pos = (Position)p;
	 * res = (this.x == pos.x && this.y == pos.y);
	 * }
	 * 
	 * 
	 * return res;
	 * }
	 */
	@Override
	public boolean equals(Object p) {
		boolean res = false;

		if (p instanceof Position) {
			Position pos = (Position) p;
			return this.equals(pos);
		}
		return res;
	}

	public boolean equals(Position p) {
		return ((this.x == p.x) && (this.y == p.y));
	}

	/**
	 * Sémantique :
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @return
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Sémantique :
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @return
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Sémantique :
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sémantique :
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}