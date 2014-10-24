/**
 * 
 */
package vue.swing.niveau;

import javax.swing.JPanel;

import modele.jeu.partie.niveau.NiveauInfoListener;
import modele.jeu.partie.niveau.lemming.Position;
import modele.jeu.partie.niveau.lemming.aptitude.TypeAptitude;
import controleur.NiveauController;

/**
 * @author Paul
 *         Cette classe est la classe parente de toutes les ihm de contrôle du
 *         niveau
 */
public abstract class NiveauControlView implements NiveauInfoListener {
	private NiveauController controller;
	private TypeAptitude aptitudeSelectionnee;

	/**
	 * Sémantique : Crée un ControleurNiveauView
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param controleur
	 */
	public NiveauControlView(NiveauController controleur) {
		this.controller = controleur;
	}

	public abstract void close();

	public abstract void display();

	/**
	 * Récupère le aptitudeSelectionnee
	 * 
	 * @return le aptitudeSelectionnee
	 */
	public TypeAptitude getAptitudeSelectionnee() {
		return this.aptitudeSelectionnee;
	}

	public final NiveauController getController() {
		return this.controller;
	}

	public abstract JPanel getPanel();

	/**
	 * Modifie le aptitudeSelectionnee
	 * 
	 * @param aptitudeSelectionnee
	 *            le nouveau aptitudeSelectionnee
	 */
	public void setAptitudeSelectionnee(TypeAptitude aptitudeSelectionnee) {
		this.aptitudeSelectionnee = aptitudeSelectionnee;
	}

	/**
	 * Sémantique :
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param position
	 * @param type
	 */
	public abstract void updateSelectedLemmingInfos(Position position,
			TypeAptitude type);

}
