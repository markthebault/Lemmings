/**
 * 
 */
package controleur;

import modele.jeu.FichierInvalideException;
import modele.jeu.Jeu;
import vue.swing.jeu.JFrameJeuView;

/**
 * @author Paul
 *         Cette classe est le controleur du jeu.
 *         Elle gere l'affichage des différentes parties, et les actions
 *         associées.
 *         **Pour l'instant il n'y a pas d'IHM, donc cette classe est là pour de
 *         futurs évolutions
 */
public class JeuController {

	private Jeu jeu;

	/**
	 * le fichier du niveau
	 */
	private String fichier;

	/**
	 * le controleur de Partie
	 */
	private PartieController controleurPartie;

	/**
	 * le fenetre où afficher
	 */
	private JFrameJeuView fenetre;

	/**
	 * Sémantique : Crée un ControleurJeuLemmings, qui initialisera le jeu et
	 * ses paramètres.
	 * Préconditions :
	 * Postconditions :
	 */
	public JeuController(String fichier) throws FichierInvalideException {
		this.jeu = new Jeu(); // le jeu
		this.fenetre = new JFrameJeuView(this); // la fenetre
		this.fichier = fichier;
	}

	/**
	 * Sémantique : Fermer la fenetre
	 * Préconditions :
	 * Postconditions :
	 */
	private void closeViews() {
		this.fenetre.close();
	}

	/**
	 * Sémantique : Lance le jeu, méthode a appeler pour lancer le jeu des
	 * lemmings
	 * Préconditions :
	 * Postconditions :
	 */
	public void lancerJeu() {
		// normalement on affiche l'ihm du jeu
		this.notifyChoixPartie();
		this.closeViews();
	}

	/**
	 * Sémantique : Changer la partie selectionnée
	 * Préconditions :
	 * Postconditions :
	 */
	public void notifyChoixPartie() {
		// on cree le controleur de la partie
		this.controleurPartie = new PartieController(this.jeu.ajouterPartie(),
				this.fenetre); // le controleur de la partie
		// On l'affiche
		this.controleurPartie.displayView();
		// on lance la partie
		this.controleurPartie.lancerPartie(this.fichier);
	}

	/**
	 * Sémantique : Quitte le jeu en cours
	 * Préconditions :
	 * Postconditions :
	 */
	public void quitter() {
		this.jeu.quitter();
	}
}
