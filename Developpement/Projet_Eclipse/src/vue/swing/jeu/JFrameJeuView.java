/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue.swing.jeu;

import javax.swing.JFrame;

import vue.swing.niveau.NiveauControlView;
import vue.swing.niveau.NiveauDisplayView;
import vue.swing.partie.PartieView;
import controleur.JeuController;

/**
 * 
 * @author Paul
 */
public class JFrameJeuView {

	/**
	 * le serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JeuController jeuController;
	// private NiveauController niveauController;
	// TODO a remettre une fois controleur de jeu fini
	// private ControleurJeuLemmings controleurJeu;

	// Les diff�rents element de l'interface de la fenetre
	private javax.swing.JMenu MenuPrincipal;
	private javax.swing.JMenuBar barreDeMenu;
	// private JPanelNiveauDisplayView panelNiveau;

	private javax.swing.JMenuItem menuQuitter;

	// private JPanelNiveauControlView panelControle;

	public JFrameJeuView(JeuController jeuController) {
		// this.controleurJeu = controleurJeu;
		this.jeuController = jeuController;
		this.initComposants();
		// initialisation du niveau
		// ajout de la fenetre en observateur du niveau
	}

	/**
	 * 
	 * Creates new form Fenetre
	 */
	// TODO A changer, initialiser avec controleur jeu, et ajouter
	// controleuNiveau au lancement d'un niveau
	// public JFrameJeuView(NiveauController niveauController) {
	// this.controleurJeu = controleurJeu;
	// this.niveauController = niveauController;
	// this.initComposants();
	// //initialisation du niveau
	// ajout de la fenetre en observateur du niveau
	// }

	public void close() {
		this.frame.dispose();
	}

	public void display() {
		this.frame.setVisible(true);
		this.frame.setExtendedState(this.frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	public void displayNiveau(NiveauControlView control,
			NiveauDisplayView display) {
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				this.frame.getContentPane());
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(control.getPanel())
				.addComponent(display.getPanel(),
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(display.getPanel(),
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(control.getPanel(),
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)));
		this.frame.getContentPane().setLayout(layout);
		this.frame.pack();

	}

	public void displayPartie(PartieView partieView) {
		// TODO afficher vue de la partie si jamais un jour ca marche
	}

	private void initComposants() {
		// TODO initialiser interface du jeu si jamais un jour tout marche
		this.frame = new JFrame();
		this.menuQuitter = new javax.swing.JMenuItem();
		// TODO A enlever
		this.barreDeMenu = new javax.swing.JMenuBar();
		this.MenuPrincipal = new javax.swing.JMenu();

		this.frame
				.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		this.MenuPrincipal.setText("Menu");

		this.menuQuitter.setText("Quitter");
		this.menuQuitter.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JFrameJeuView.this.menuQuitterActionPerformed(evt);
			}
		});
		this.MenuPrincipal.add(this.menuQuitter);

		this.barreDeMenu.add(this.MenuPrincipal);

		this.frame.setJMenuBar(this.barreDeMenu);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				this.frame.getContentPane());
		this.frame.getContentPane().setLayout(layout);
		this.frame.setResizable(true);
		this.frame.pack();
		// this.jeuController.displayView();
	}

	private void menuQuitterActionPerformed(java.awt.event.ActionEvent evt) {
		this.jeuController.quitter();
	}
}
