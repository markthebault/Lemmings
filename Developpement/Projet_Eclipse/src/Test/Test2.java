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
public class Test2 {

	/**
	 * Sémantique :
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		final JeuController jn;

		try {
			jn = new JeuController("resources/lvl_base.lemmings");
			jn.lancerJeu();
		}
		catch (FichierInvalideException e) {
			e.printStackTrace();
		}
	}
}
