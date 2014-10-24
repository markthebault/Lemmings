/**
 * 
 */
package Test;

import modele.jeu.FichierInvalideException;
import controleur.JeuController;

/**
 * @author Paul
 * 
 */
public class Test1 {

	/**
	 * Sémantique :
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		final JeuController jn;

		try {
			jn = new JeuController("resources/niveau.lemmings");
			jn.lancerJeu();
		}
		catch (FichierInvalideException e) {
			e.printStackTrace();
		}
	}
}
