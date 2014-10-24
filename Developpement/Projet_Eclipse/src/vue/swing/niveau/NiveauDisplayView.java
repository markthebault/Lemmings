/**
 * 
 */
package vue.swing.niveau;

import java.awt.Component;

import modele.jeu.partie.niveau.NiveauListener;
import controleur.NiveauController;

/**
 * @author Paul
 *         Cette classe est la classe parente de toutes les ihm d'affichage du
 *         niveau
 */
public abstract class NiveauDisplayView implements NiveauListener {

	private NiveauController controller;

	/**
	 * Sémantique : Crée un NiveauDisplayView
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param controller
	 */
	public NiveauDisplayView(NiveauController controller) {
		this.controller = controller;
	}

	public abstract void close();

	public abstract void display();

	public final NiveauController getController() {
		return this.controller;
	}

	public abstract Component getPanel();

}
