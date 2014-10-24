/**
 * 
 */
package vue.swing.niveau;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

import modele.jeu.partie.niveau.InfosChangedEvent;
import modele.jeu.partie.niveau.InfosNiveau;
import modele.jeu.partie.niveau.lemming.Position;
import modele.jeu.partie.niveau.lemming.aptitude.TypeAptitude;
import controleur.NiveauController;

/**
 * @author Paul
 * 
 */
public class JPanelNiveauControlView extends NiveauControlView {

	// TODO enlever getter (a voir)
	private JPanel panel;

	private JPanel panelAptitudes;


	private ArrayList<JButton> boutonsAptitudes;

	private JPanel panelOptions;
	private JSlider sliderVitesseApparition;
	private JSlider sliderVitesseJeu;
	private JLabel labelVitesseApparition;
	private JLabel labelVitessejeu;
	private JToggleButton boutonPause;
	private JButton boutonVitesseMax;

	private JPanel panelInfos;
	private javax.swing.JLabel labelEnJeu;
	private javax.swing.JLabel labelNombreEnJeu;
	private javax.swing.JLabel labelSauves;
	private javax.swing.JLabel labelTemps;
	private javax.swing.JLabel labelTotal;
	private JLabel labelMorts;
	private JLabel labelNombreMorts;
	private JLabel labelInfos;
	private javax.swing.JLabel labelNombreTotal;
	private javax.swing.JProgressBar progressBarSauves;
	private javax.swing.JProgressBar progressBarTemps;

	/**
	 * Sémantique : Crée un JPanelNiveauControl
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param niveauController
	 */
	public JPanelNiveauControlView(NiveauController niveauController) {
		this(niveauController, new InfosNiveau());
	}

	/**
	 * Sémantique : Crée un JPanelNiveauControl
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param niveauController
	 */
	public JPanelNiveauControlView(NiveauController niveauController,
			InfosNiveau infos) {
		super(niveauController);
		this.initComposants(infos);
	}

	@Override
	public void aptitudeChanged(InfosChangedEvent evt) {
		
		for(JButton b : this.boutonsAptitudes)
		{
			b.setText(b.getName()+ " ("
				+ evt.getDetailsAptitudes().get(TypeAptitude.valueOf(b.getName()))
				+ ")");
		}
	}

	public void boutonAptitudeActionPerformed(ActionEvent ae) {
		// deselectionner tous les boutons
		for (JButton b : this.boutonsAptitudes) {
			b.getModel().setPressed(false);
		}
		JButton boutonAptitude = (JButton) ae.getSource();
		// selectionner le bouton sur lequel on a clické
		this.setAptitudeSelectionnee(TypeAptitude.valueOf(boutonAptitude
				.getName()));
		boutonAptitude.getModel().setPressed(true);
	}

	private void boutonPauseActionPerformed(java.awt.event.ActionEvent evt) {
		this.getController().notifyJeuEnPause();
	}

	private void boutonVitesseMaxActionPerformed(java.awt.event.ActionEvent evt) {
		this.getController().notitfyVitesseJeuChanged(
				InfosNiveau.VITESSE_JEU_MAX);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.NiveauControlView#close()
	 */
	@Override
	public void close() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vue.NiveauControlView#display()
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
	@Override
	public JPanel getPanel() {
		return this.panel;
	}

	private void initComposants(InfosNiveau infos) {
		this.setAptitudeSelectionnee(null);
		this.panel = new JPanel();
		this.panelAptitudes = new JPanel();
		this.panelOptions = new JPanel();
		this.sliderVitesseApparition = new JSlider();
		this.sliderVitesseJeu = new JSlider();
		this.labelVitesseApparition = new JLabel();
		this.labelVitessejeu = new JLabel();
		this.boutonPause = new JToggleButton();
		this.boutonVitesseMax = new JButton();

		this.panelInfos = new JPanel();
		this.labelEnJeu = new JLabel();
		this.labelNombreEnJeu = new JLabel();
		this.labelSauves = new JLabel();
		this.labelTemps = new JLabel();
		this.labelTotal = new JLabel();
		this.labelMorts = new JLabel();
		this.labelInfos = new JLabel();
		this.labelNombreMorts = new JLabel();
		this.labelNombreTotal = new JLabel();
		this.progressBarSauves = new JProgressBar();
		this.progressBarTemps = new JProgressBar();
		this.labelNombreTotal.setText("" + infos.getNombreLemmingsTotal());
		this.progressBarSauves.setMaximum(infos.getNombreLemmingsASauver());
		this.progressBarSauves.setMinimum(0);
		this.progressBarSauves.setString(infos.getNombreLemmingsSauves() + "/"
				+ this.progressBarSauves.getMaximum());
		this.progressBarSauves.setStringPainted(true);
		this.progressBarTemps.setMaximum(infos.getTempsImparti());
		this.progressBarTemps.setMinimum(0);
		this.progressBarTemps.setValue(infos.getTempsRestant());
		this.progressBarTemps.setString(infos.getTempsRestant() + "/"
				+ infos.getTempsImparti());
		this.progressBarTemps.setStringPainted(true);
		this.sliderVitesseApparition.setValue(infos
				.getVitesseApparitionLemmings());
		this.sliderVitesseJeu.setValue(infos.getVitesseJeu());
		// Panel aptitudes
		this.panelAptitudes.setBorder(javax.swing.BorderFactory
				.createTitledBorder(null, "Aptitudes",
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						new java.awt.Font("Aharoni", 3, 14)));
		this.panelAptitudes.setLayout(new GridLayout(2, infos
				.getDetailsAptitudes().size() / 2));
		this.boutonsAptitudes = new ArrayList<>();
		for (Entry<TypeAptitude, Integer> entry : infos.getDetailsAptitudes()
				.entrySet()) {
			TypeAptitude nomAptitude = entry.getKey();
			Integer nombreAptitude = entry.getValue();
			JButton boutonAptitude = new JButton(nomAptitude.name()+" ("+nombreAptitude.toString()+")");
			boutonAptitude.setName(nomAptitude.name());
			this.boutonsAptitudes.add(boutonAptitude);
			boutonAptitude.setVisible(true);
			boutonAptitude.setPreferredSize(new Dimension(135, 10));
			boutonAptitude.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JPanelNiveauControlView.this
							.boutonAptitudeActionPerformed(arg0);
				}
			});
			this.panelAptitudes.add(boutonAptitude);

		}
		// Sliders
		this.sliderVitesseApparition
				.setMaximum(InfosNiveau.VITESSE_APPARITION_LEMMINGS_MAX);
		this.sliderVitesseApparition
				.setMinimum(InfosNiveau.VITESSE_APPARITION_LEMMINGS_MIN);
		this.sliderVitesseApparition
				.addChangeListener(new javax.swing.event.ChangeListener() {
					@Override
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						JPanelNiveauControlView.this
								.sliderVitesseApparitionStateChanged(evt);
					}
				});

		this.sliderVitesseJeu.setMaximum(InfosNiveau.VITESSE_JEU_MAX);
		this.sliderVitesseJeu.setMinimum(InfosNiveau.VITESSE_JEU_MIN);
		this.sliderVitesseJeu
				.addChangeListener(new javax.swing.event.ChangeListener() {
					@Override
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						JPanelNiveauControlView.this
								.sliderVitesseJeuStateChanged(evt);
					}
				});

		this.labelVitesseApparition.setText("Vitesse Apparition");

		this.labelVitessejeu.setText("Vitesse Jeu");

		// Boutons
		this.boutonPause.setText("Pause");
		this.boutonPause.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JPanelNiveauControlView.this.boutonPauseActionPerformed(evt);
			}
		});

		this.boutonVitesseMax.setText("Vitesse Max");
		this.boutonVitesseMax
				.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						JPanelNiveauControlView.this
								.boutonVitesseMaxActionPerformed(evt);
					}
				});
		// Panel options
		javax.swing.GroupLayout panelOptionsLayout = new javax.swing.GroupLayout(
				this.panelOptions);
		this.panelOptions.setLayout(panelOptionsLayout);
		panelOptionsLayout
				.setHorizontalGroup(panelOptionsLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelOptionsLayout
										.createSequentialGroup()
										.addGap(2, 2, 2)
										.addGroup(
												panelOptionsLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.CENTER)
														.addComponent(
																this.labelVitesseApparition)
														.addComponent(
																this.sliderVitesseApparition,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																122,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												panelOptionsLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.CENTER)
														.addComponent(
																this.labelVitessejeu)
														.addComponent(
																this.sliderVitesseJeu,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																105,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(10, 10, 10)
										.addGroup(
												panelOptionsLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																this.boutonVitesseMax,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																149,
																Short.MAX_VALUE)
														.addComponent(
																this.boutonPause,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
		panelOptionsLayout
				.setVerticalGroup(panelOptionsLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelOptionsLayout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												panelOptionsLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																panelOptionsLayout
																		.createSequentialGroup()
																		.addComponent(
																				this.labelVitessejeu)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				this.sliderVitesseJeu,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																panelOptionsLayout
																		.createSequentialGroup()
																		.addComponent(
																				this.labelVitesseApparition)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				this.sliderVitesseApparition,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap())
						.addGroup(
								panelOptionsLayout
										.createSequentialGroup()
										.addComponent(
												this.boutonPause,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												59,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												this.boutonVitesseMax,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												56,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 9, Short.MAX_VALUE)));
		// panel infos
		this.labelEnJeu.setText("En Jeu");

		this.labelTotal.setText("Total");

		this.labelSauves.setText("Sauvés");

		this.labelTemps.setText("Temps");

		this.labelNombreEnJeu.setText("0");

		this.labelNombreTotal.setText("0");

		this.labelMorts.setText("Morts");

		this.labelNombreMorts.setText("0");

		this.labelInfos.setText("");
		this.labelInfos.setPreferredSize(new Dimension(200, 30));

		javax.swing.GroupLayout panelInfosLayout = new javax.swing.GroupLayout(
				this.panelInfos);
		this.panelInfos.setLayout(panelInfosLayout);
		panelInfosLayout
				.setHorizontalGroup(panelInfosLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								panelInfosLayout
										.createSequentialGroup()
										.addComponent(
												this.labelTotal,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												33,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												this.labelNombreTotal,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												this.labelEnJeu,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												41,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												this.labelNombreEnJeu,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												this.labelMorts,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												42,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												this.labelNombreMorts,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												this.labelInfos,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												189,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												this.labelSauves,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												49, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												this.progressBarSauves,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												this.labelTemps,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												45, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												this.progressBarTemps,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		panelInfosLayout
				.setVerticalGroup(panelInfosLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								panelInfosLayout
										.createSequentialGroup()
										.addContainerGap(25, Short.MAX_VALUE)
										.addGroup(
												panelInfosLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																this.labelTemps)
														.addComponent(
																this.progressBarSauves,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																this.progressBarTemps,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																panelInfosLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				this.labelEnJeu)
																		.addComponent(
																				this.labelTotal)
																		.addComponent(
																				this.labelSauves)
																		.addComponent(
																				this.labelNombreEnJeu)
																		.addComponent(
																				this.labelNombreTotal)
																		.addComponent(
																				this.labelMorts)
																		.addComponent(
																				this.labelNombreMorts)
																		.addComponent(
																				this.labelInfos)))
										.addContainerGap()));
		// panel englobant
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.panel);
		this.panel.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(this.panelAptitudes,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(this.panelOptions,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
				.addComponent(this.panelInfos,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING,
												false)
												.addComponent(
														this.panelOptions,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														this.panelAptitudes,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(this.panelInfos,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
	}// </editor-fold>

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * modele.jeu.partie.niveau.NiveauListener#nombreLemmingsEnJeuChanged(modele
	 * .jeu.partie.niveau.InfosChangedEvent)
	 */
	@Override
	public void nombreLemmingsEnJeuChanged(InfosChangedEvent evt) {
		this.labelNombreEnJeu.setText("" + evt.getNombreLemmingsEnJeu());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * modele.jeu.partie.niveau.NiveauListener#nombreLemmingsMortsChanged(modele
	 * .jeu.partie.niveau.InfosChangedEvent)
	 */
	@Override
	public void nombreLemmingsMortsChanged(InfosChangedEvent evt) {
		this.labelNombreMorts.setText("" + evt.getNombreLemmingsMorts());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * modele.jeu.partie.niveau.NiveauListener#nombreLemmingsSauvesChanged(modele
	 * .jeu.partie.niveau.InfosChangedEvent)
	 */
	@Override
	public void nombreLemmingsSauvesChanged(InfosChangedEvent evt) {
		this.progressBarSauves.setValue(evt.getNombreLemmingsSauves());
		this.progressBarSauves.setString(evt.getNombreLemmingsSauves() + "/"
				+ evt.getNombreLemmingsASauver());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * modele.jeu.partie.niveau.NiveauListener#pauseChanged(modele.jeu.partie
	 * .niveau.PauseChangedEvent)
	 */
	@Override
	public void pauseChanged(InfosChangedEvent evt) {
		this.boutonPause.getModel().setPressed(evt.isJeuEnPause());
	}

	private void sliderVitesseApparitionStateChanged(
			javax.swing.event.ChangeEvent evt) {
		JSlider slider = (JSlider) evt.getSource();
		if (!slider.getValueIsAdjusting()) {
			this.getController().notifyVitesseApparition(slider.getValue());
		}
	}

	private void sliderVitesseJeuStateChanged(javax.swing.event.ChangeEvent evt) {
		JSlider slider = (JSlider) evt.getSource();
		if (!slider.getValueIsAdjusting()) {
			this.getController().notitfyVitesseJeuChanged(slider.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * modele.jeu.partie.niveau.NiveauListener#aptitudeChanged(modele.jeu.partie
	 * .niveau.InfosChangedEvent)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * modele.jeu.partie.niveau.NiveauListener#tempsRestantChanged(modele.jeu
	 * .partie.niveau.InfosChangedEvent)
	 */
	@Override
	public void tempsRestantChanged(InfosChangedEvent evt) {
		this.progressBarTemps.setValue(evt.getTempsRestant());
		this.progressBarTemps.setString(evt.getTempsRestant() + "/"
				+ evt.getTempsImparti());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * vue.swing.niveau.NiveauControlView#updateSelectedLemmingInfos(modele.
	 * jeu.partie.niveau.lemming.Position,
	 * modele.jeu.partie.niveau.lemming.aptitude.TypeAptitude)
	 */
	@Override
	public void updateSelectedLemmingInfos(Position position, TypeAptitude type) {
		this.labelInfos.setText("Position : " + position + " Aptitude :"
				+ type.name());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * modele.jeu.partie.niveau.NiveauListener#vitesseApparitionChanged(modele
	 * .jeu.partie.niveau.InfosChangedEvent)
	 */
	@Override
	public void vitesseApparitionChanged(InfosChangedEvent evt) {
		this.sliderVitesseApparition.setValue(evt
				.getVitesseApparitionLemmings());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * modele.jeu.partie.niveau.NiveauListener#vitesseJeuChanged(modele.jeu.
	 * partie.niveau.InfosChangedEvent)
	 */
	@Override
	public void vitesseJeuChanged(InfosChangedEvent evt) {
		this.sliderVitesseJeu.setValue(evt.getVitesseJeu());
	}
}
