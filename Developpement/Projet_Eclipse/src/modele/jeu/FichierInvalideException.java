/**
 * 
 */
package modele.jeu;

/**
 * @author Paul
 * 
 */
public class FichierInvalideException extends Exception {

	/**
	 * le serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Sémantique : Crée un FichierInvalideException
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param message
	 */
	public FichierInvalideException(String message) {
		super(message);
	}

}
