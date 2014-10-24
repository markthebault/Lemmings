package modele.jeu.partie.niveau.carte;


public class Trappe extends Case {
	/**
	 * Sémantique : Crée une Trappe (non destructible, et non grimpable)
	 * Préconditions :
	 * Postconditions :
	 */
	public Trappe() {
		super(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.Case#toString()
	 */
	@Override
	public String toString() {
		return "0";
	}
}