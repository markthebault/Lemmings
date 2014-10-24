/**
 * 
 */
package controleur;

import java.io.File;

import modele.jeu.FichierInvalideException;
import modele.jeu.partie.Partie;
import vue.swing.jeu.JFrameJeuView;
import vue.swing.partie.JPanelPartieView;
import vue.swing.partie.PartieView;

/**
 * @author Paul
 *         Cette classe est le controleur de la partie.
 *         Elle gere l'affichage des différents niveaux d'une partie, et les
 *         actions associées.
 *         **Pour l'instant il n'y a pas d'IHM, donc cette classe est là pour de
 *         futurs évolutions
 */
public class PartieController {
	// les vues
	/**
	 * le fenetre où afficher
	 */
	private JFrameJeuView fenetre;
	/**
	 * le vue de la partie
	 */
	private PartieView vue;
	// le modèle
	/**
	 * le partie
	 */
	private Partie partie;

	// le fichier du niveau
	String fichier;

	/**
	 * le controleur du Niveau
	 */
	private NiveauController controleurNiveau;

	/**
	 * Sémantique : Crée un controleur de partie
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param partie
	 * @param fenetre
	 */
	public PartieController(Partie partie, JFrameJeuView fenetre) {
		this.partie = partie;
		this.fenetre = fenetre;
		this.vue = new JPanelPartieView(this);

		this.addListenerToModel();
	}

	/**
	 * Sémantique : Ajoute les listeners(vues) au modèle
	 * Préconditions :
	 * Postconditions :
	 */
	private void addListenerToModel() {
	}

	/**
	 * Sémantique : ferme les vues
	 * Préconditions :
	 * Postconditions :
	 */
	public void closeViews() {
		this.vue.close();
	}

	/**
	 * Sémantique : affiche les vues
	 * Préconditions :
	 * Postconditions :
	 */
	public void displayView() {
		// this.vue.display();
		this.fenetre.displayPartie(this.vue);
	}

	/**
	 * Sémantique : Lancer la partie
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param fic
	 */
	public void lancerPartie(String fic) {
		this.notifyChoixNiveau(fic);
	}

	/**
	 * Sémantique : Choisir le niveau
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param fic
	 */
	public void notifyChoixNiveau(String fic) {
		try {
			// cree le controleur du niveau
			this.controleurNiveau = new NiveauController(
					this.partie.ajouterNiveau(new File(fic)), this.fenetre);
		}
		catch (FichierInvalideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// afficher les vues
		this.controleurNiveau.displayView();
		// lancer le niveau
		this.controleurNiveau.lancerNiveau();
	}
}
