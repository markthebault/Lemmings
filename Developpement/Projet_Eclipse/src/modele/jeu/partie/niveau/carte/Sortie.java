package modele.jeu.partie.niveau.carte;


public class Sortie extends Case {

	/**
	 * Sémantique : Crée une Sortie (non destructible et non grimpable)
	 * Préconditions :
	 * Postconditions :
	 */
	public Sortie() {
		super(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see modele.Case#toString()
	 */
	@Override
	public String toString() {
		return "@";
	}
}