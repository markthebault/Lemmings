package vue;

import java.util.Observable;
import java.util.Observer;

import modele.jeu.partie.niveau.Niveau;

public class VueConsole implements Observer {

	/**
	 * Sémantique : Crée un VueConsole
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param nv
	 */
	public VueConsole() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {

		// ecrire le niveau
		System.out.println(((Niveau) arg1).toString());

	}

}
