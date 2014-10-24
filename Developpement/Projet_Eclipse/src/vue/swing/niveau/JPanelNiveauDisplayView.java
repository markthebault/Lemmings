/**
 * 
 */
package vue.swing.niveau;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modele.jeu.partie.niveau.NiveauChangedEvent;
import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.carte.Case;
import modele.jeu.partie.niveau.carte.Piege;
import modele.jeu.partie.niveau.carte.SensDestruction;
import modele.jeu.partie.niveau.carte.Sortie;
import modele.jeu.partie.niveau.carte.Trappe;
import modele.jeu.partie.niveau.lemming.Lemming;
import modele.jeu.partie.niveau.lemming.Position;
import modele.jeu.partie.niveau.lemming.aptitude.Aptitude;
import modele.jeu.partie.niveau.lemming.aptitude.Marcheur;
import controleur.NiveauController;

/**
 * @author Paul
 * 
 */
public class JPanelNiveauDisplayView extends NiveauDisplayView {

	/**
	 * @author Paul
	 *         Classe permettant de gerer les animations
	 */
	class ListenerTimer implements ActionListener {

		private JLabelCase label;

		/**
		 * Sémantique : Crée un ListenerTimer
		 * Préconditions :
		 * Postconditions :
		 * 
		 * @param label
		 */
		public ListenerTimer(JLabelCase label) {
			this.label = label;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//this.label.setEtatPrecedent();
		}
	}

	private JPanel panel, panelPrincipal;
	private JScrollPane scroll;

	/**
	 * l'ensemble des jLabel. L'indice ds l'arraylist est le meme que celui de
	 * la carte
	 */
	private ArrayList<ArrayList<JLabelCase>> ecranNiveau;

	/**
	 * Sémantique : Crée un JPanelNiveauDisplayView
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param controller
	 */
	public JPanelNiveauDisplayView(NiveauController controller, Carte carte) {
		super(controller);
		FlowLayout layout = new FlowLayout();
		this.panelPrincipal = new JPanel();
		this.panelPrincipal.setLayout(layout);
		this.panel = new JPanel();
		
		
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
		
		
		
		this.initCarte(carte);
		this.panelPrincipal.add(this.panel);
		this.scroll = new JScrollPane(this.panelPrincipal);
		
		//calcule la taille en Y
		int tailleY = (carte.getMaxAxes().getY() + 1) * 20 + 20;
		//met une taille maxi
		tailleY = tailleY > 400 ? 400 : tailleY;
		
		
		//met la taille preferentielle
		this.scroll.setPreferredSize(new Dimension(1000,tailleY));
		
	}

	private void afficherEcran() {
		for (ArrayList<JLabelCase> liste : this.ecranNiveau) {
			for (JLabelCase l : liste) {
				this.panel.add(l);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.NiveauDisplayView#close()
	 */
	@Override
	public void close() {
		this.panel.setVisible(false);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.NiveauDisplayView#display()
	 */
	@Override
	public void display() {
		this.panel.setVisible(true);

	}

	/**
	 * Récupère le panel
	 * 
	 * @return le panel
	 */
	public Component getPanel() {
		return this.scroll;
	}

	private void initCarte(Carte carte) {
		this.ecranNiveau = new ArrayList<>();
		ArrayList<JLabelCase> listeCases = null;
		// crÃ©ation d'un tableau pour le niveau
		// recherche des coordonnÃ©es les plus Ã©levÃ©es
		int xMax = carte.getMaxAxes().getX() + 1;
		int yMax = carte.getMaxAxes().getY() + 1;
		this.panel.setLayout(new GridLayout(yMax, xMax));

		for (int y = 0; y < yMax; y++) {
			listeCases = new ArrayList<>();
			for (int x = 0; x < xMax; x++) {
	
				//recuperer la case
				Case c = carte.getCase(new Position(x,y));
				
				//creation d'une case
				JLabelCase typeCase = new JLabelCase();
				
				//on ajoute la couleur de la case 
				typeCase.setCase(c);
				
				//cree un listener pour les cases
				typeCase.addMouseListener(new MouseAdapter() {

					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * java.awt.event.MouseAdapter#mouseEntered(java.awt.event
					 * .MouseEvent)
					 */
					@Override
					public void mouseClicked(MouseEvent arg0) {
						JPanelNiveauDisplayView.this
								.mouseClickedLemmingAction(arg0);
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * java.awt.event.MouseAdapter#mouseEntered(java.awt.event
					 * .MouseEvent)
					 */
					@Override
					public void mouseEntered(MouseEvent arg0) {
						JPanelNiveauDisplayView.this
								.mouseEnteredLemmingAction(arg0);
						super.mouseEntered(arg0);
					}

				});

				//Sauvegarder la case dans un tableau
				listeCases.add(typeCase);
			}
			
			//ajoute les lignes des X au Y
			this.ecranNiveau.add(listeCases);
		}
		
		//ajouter les cases au JPanel
		this.afficherEcran();
	}

	private void mettreAjour(Carte carte, ArrayList<Lemming> lemmings) {
		
		this.mettreAJourCarte(carte);
		this.mettreAJourLemmings(lemmings);
		

	}

	private void mettreAJourLemmings(ArrayList<Lemming> lemmings) {
		
		for(Lemming l : lemmings)
		{
			//recupere les coords du lemming
			int x = l.getDirectionActuelle().getPosition().getX();
			int y = l.getDirectionActuelle().getPosition().getY();
			
			//recupere le label correspodant a la position du lemming
			JLabelCase label = this.ecranNiveau.get(y).get(x);
			
			//met a jour la couleur du lemming
			label.setlemmingColor(l);
		}
		
	}

	private void mettreAJourCarte(Carte carte) {
		//recupere les coordonnes
		int xMax = carte.getMaxAxes().getX() + 1;
		int yMax = carte.getMaxAxes().getY() + 1;

		//pour chaque case
		for (int y = 0; y < yMax; y++) {
			for (int x = 0; x < xMax; x++) {		
			
				//recuperer la case
				Case c = carte.getCase(new Position(x,y));
				
				//recupere le label correspodant a la case
				JLabelCase label = this.ecranNiveau.get(y).get(x);
		
				//on ajoute la couleur de la case 
				label.setCase(c);
			}
		}
		
	}

	/**
	 * Sémantique : quand on clique sur le lemmings
	 * Préconditions :
	 * Postconditions : appele le controller
	 * @param arg0
	 */
	private void mouseClickedLemmingAction(MouseEvent arg0) {
		JLabelCase label = (JLabelCase) arg0.getSource();
		int x = 0;
		int y = 0;
		if (label.estlemming()) {
			for (ArrayList<JLabelCase> l : this.ecranNiveau) {
				if (l.indexOf(label) != -1) {
					x = l.indexOf(label);
					y = this.ecranNiveau.indexOf(l);
				}
			}
			Position p = new Position(x, y);
			this.getController().notifyLemmingSelectedChanged(p);
			this.getController().notitfyAptitudeChanged(p);
		}
	}

	private void mouseEnteredLemmingAction(MouseEvent arg0) {
		JLabelCase label = (JLabelCase) arg0.getSource();
		if (label.estlemming()) {
			label.setLemmingsSelectionne();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * modele.jeu.partie.niveau.NiveauListener#niveauChanged(modele.jeu.partie
	 * .niveau.NiveauChangedEvent)
	 */
	@Override
	public void niveauChanged(NiveauChangedEvent evt) {
		this.mettreAjour(evt.getCarte(), evt.getLemmings());
		this.getController().notifyUpdateLemmingInfos();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
