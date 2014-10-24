package modele.jeu.partie.niveau;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;

import modele.jeu.FichierInvalideException;
import modele.jeu.partie.niveau.carte.Carte;
import modele.jeu.partie.niveau.lemming.Direction;
import modele.jeu.partie.niveau.lemming.Lemming;
import modele.jeu.partie.niveau.lemming.Position;
import modele.jeu.partie.niveau.lemming.SensDeplacement;
import modele.jeu.partie.niveau.lemming.aptitude.Aptitude;
import modele.jeu.partie.niveau.lemming.aptitude.AptitudeInvalideException;
import modele.jeu.partie.niveau.lemming.aptitude.Bloqueur;
import modele.jeu.partie.niveau.lemming.aptitude.Bombeur;
import modele.jeu.partie.niveau.lemming.aptitude.Constructeur;
import modele.jeu.partie.niveau.lemming.aptitude.Foreur;
import modele.jeu.partie.niveau.lemming.aptitude.Grimpeur;
import modele.jeu.partie.niveau.lemming.aptitude.Marcheur;
import modele.jeu.partie.niveau.lemming.aptitude.Mineur;
import modele.jeu.partie.niveau.lemming.aptitude.Parachutiste;
import modele.jeu.partie.niveau.lemming.aptitude.Pelleteur;
import modele.jeu.partie.niveau.lemming.aptitude.TypeAptitude;

public class Niveau {

	ArrayList<NiveauListener> listeners;

	/**
	 * le infos du niveau
	 */
	private InfosNiveau infos;

	/**
	 * compteur permettant de définir quand mettre à jour le jeu
	 */
	private int compteurVitesse;

	/**
	 * le timer du niveau
	 */
	private Timer timer;

	/**
	 * les lemmings sur le niveau. Ils sont tous ajoutés au début de partie
	 */
	private ArrayList<Lemming> lemmingsDuNiveau;

	/**
	 * la carte
	 */
	private Carte carte;

	/**
	 * Permet de gerer la période d'apparition des lemmings
	 */
	private int compteurApparitionLemmings;

	/**
	 * Sémantique : Crée un Niveau à partir d'un fichier
	 * Initialise les objectifs et contraintes.
	 * Préconditions :
	 * Postconditions : niveau initialisé
	 * 
	 * @param fic
	 */
	public Niveau(File fic) throws FichierInvalideException {
		this.infos = new InfosNiveau();
		// initialiser toutes les aptitudes a 0
		this.infos.addDetailsAptitudes(TypeAptitude.CLIMBER, 0);
		this.infos.addDetailsAptitudes(TypeAptitude.FLOATER, 0);
		this.infos.addDetailsAptitudes(TypeAptitude.BLOCKER, 0);
		this.infos.addDetailsAptitudes(TypeAptitude.BOMBER, 0);
		this.infos.addDetailsAptitudes(TypeAptitude.BUILDER, 0);
		this.infos.addDetailsAptitudes(TypeAptitude.BASHER, 0);
		this.infos.addDetailsAptitudes(TypeAptitude.DIGGER, 0);
		this.infos.addDetailsAptitudes(TypeAptitude.MINER, 0);
		// initialiser les differents compteur
		this.lemmingsDuNiveau = new ArrayList<>();
		this.compteurApparitionLemmings = 0;
		this.compteurVitesse = 0;
		try {
			// on enregistre le fichier
			this.infos.setFichierDuNiveau(fic);
			// creation du lecteur de fichier
			InputStream ips = new FileInputStream(fic);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);

			// on parcours tous le fichier
			String ligne = br.readLine();
			while (((ligne != null) && !ligne.startsWith("map"))) {
				String[] ligneParsee = ligne.split("\\t| ");
				switch (ligneParsee[0].toLowerCase()) {
				// vitesse d'apparition des lemmings
					case "speedlemmings":
						int vitesseApparition = Integer
								.parseInt(ligneParsee[1]);
						this.infos.setVitesseApparition(vitesseApparition);
						// on set le min pour augmenter la difficulté
						this.infos
								.setVitesseApparitionLemmingsMin(vitesseApparition);
						break;
					// vitesse du jeu
					case "speedgame":
						this.infos.setVitesseJeu(Integer
								.parseInt(ligneParsee[1]));
						break;
					// temps limite
					case "time":
						int temps = Integer.parseInt(ligneParsee[1]);
						this.infos.setTempsImparti(temps);
						this.infos.setTempsRestant(temps);
						break;
					// nombre de lemmings
					case "count":
						this.infos.setNombreLemmingsTotal(Integer
								.parseInt(ligneParsee[1]));
						break;
					// nombre minimum de lemmings à sauver
					case "rescue":
						this.infos.setNombreLemmingsASauver(Integer
								.parseInt(ligneParsee[1]));
						break;
					// Les aptitudes
					case "climber":
						this.infos.addDetailsAptitudes(TypeAptitude.CLIMBER,
								Integer.parseInt(ligneParsee[1]));
						break;
					case "floater":
						this.infos.addDetailsAptitudes(TypeAptitude.FLOATER,
								Integer.parseInt(ligneParsee[1]));
						break;
					case "blocker":
						this.infos.addDetailsAptitudes(TypeAptitude.BLOCKER,
								Integer.parseInt(ligneParsee[1]));
						break;
					case "bomber":
						this.infos.addDetailsAptitudes(TypeAptitude.BOMBER,
								Integer.parseInt(ligneParsee[1]));
						break;
					case "builder":
						this.infos.addDetailsAptitudes(TypeAptitude.BUILDER,
								Integer.parseInt(ligneParsee[1]));
						break;
					case "basher":
						this.infos.addDetailsAptitudes(TypeAptitude.BASHER,
								Integer.parseInt(ligneParsee[1]));
						break;
					case "digger":
						this.infos.addDetailsAptitudes(TypeAptitude.DIGGER,
								Integer.parseInt(ligneParsee[1]));
						break;
					case "miner":
						this.infos.addDetailsAptitudes(TypeAptitude.MINER,
								Integer.parseInt(ligneParsee[1]));
				}
				// passer a la ligne suivante
				ligne = br.readLine();
			}
			// fermer le stream
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new FichierInvalideException(
					"Erreur lors de la creation du niveau");
		}
		// initialiser la carte
		this.carte = new Carte(fic);
		// demarrer le timer du niveau
		// ce dernier ne sert qu'a mettre a jour le temps restant dans les infos
		this.timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				InfosNiveau infos = Niveau.this.getInfos();
				if (infos.getTempsRestant() > 0) {
					// il reste du temps
					// on modifie le temps restant
					// on redemarre le timer
					infos.setTempsRestant(infos.getTempsRestant() - 1);
					Timer timer = (Timer) arg0.getSource();
					timer.restart();
				} else {
					// c'est fini
					infos.setFinDeNiveau(true);
					infos.setGagne(false);
				}
			}
		});
		// Feu
		this.infos.setFinDeNiveau(false);
		this.infos.setGagne(false);
		this.timer.start();

		// Initialiser listeners
		this.listeners = new ArrayList<NiveauListener>();

		this.fireNiveauChanged();
	}

	public void addListener(NiveauListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Sémantique : Essaie d'attribuer l'aptitude ap au lemming
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param lemming
	 * @param ap
	 * @throws AptitudeInvalideException
	 *             si ap n'est pas une aptitude
	 */
	public void changerAptitude(Lemming lemming, TypeAptitude ap)
			throws AptitudeInvalideException {
		if (this.infos.getDetailsAptitudes().get(ap) > 0) {

			Aptitude aptitude; // aptitude a creer
			// on ne peut pas changer l'aptitude si le jeu est en pause
			if (!this.infos.isJeuEnPause()) {
				// on cree l'aptitude
				switch (ap) {
					case CLIMBER:
						aptitude = new Grimpeur(lemming, this.carte);
						break;
					case FLOATER:
						aptitude = new Parachutiste(lemming, this.carte);
						break;
					case BLOCKER:
						aptitude = new Bloqueur(lemming, this.carte);
						break;
					case BOMBER:
						aptitude = new Bombeur(lemming, this.carte);
						break;
					case BUILDER:
						aptitude = new Constructeur(lemming, this.carte);
						break;
					case BASHER:
						aptitude = new Pelleteur(lemming, this.carte);
						break;
					case DIGGER:
						aptitude = new Foreur(lemming, this.carte);
						break;
					case MINER:
						aptitude = new Mineur(lemming, this.carte);
						break;
					default:
						throw new AptitudeInvalideException("L'aptitude " + ap
								+ " n'existe pas.");
				}
				// changer aptitude du lemming
				try {
					// si elle est deja affectée, on recupere l'exception sans
					// rien
					// faire, c'est juste pour eviter de decrementer si
					// l'aptitude
					// n'est pas attribuée
					lemming.changerAptitude(aptitude);
					this.infos.addDetailsAptitudes(ap,
							this.infos.getNombreAptitude(ap) - 1);
				}
				catch (Exception e) {
					// rien
				}
			}
		}
	}

	private void fireNiveauChanged() {
		for (NiveauListener l : this.listeners) {
			l.niveauChanged(new NiveauChangedEvent(this, this.getCarte(), this
					.getLemmingsEnJeu()));
		}
	}

	/**
	 * Sémantique : Récupère la carte
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @return la carte
	 */
	public Carte getCarte() {
		return this.carte;
	}

	/**
	 * Récupère le infos
	 * 
	 * @return le infos
	 */
	public InfosNiveau getInfos() {
		return this.infos;
	}

	/**
	 * Sémantique :
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param x
	 * @param y
	 */
	public Lemming getLemming(int x, int y) {
		Position p = new Position(x, y);
		for (Lemming l : this.lemmingsDuNiveau) {
			if (l.getDirectionActuelle().getPosition().equals(p)) {
				return l;
			}
		}
		return null;
	}

	/**
	 * Récupère le lemmingsEnJeu
	 * 
	 * @return le lemmingsEnJeu
	 */
	public ArrayList<Lemming> getLemmingsEnJeu() {
		return this.lemmingsDuNiveau;
	}

	/**
	 * Sémantique : Mettre à jour le niveau, cad tous les lemmings et la carte
	 * Préconditions :
	 * Postconditions :
	 */
	public void miseAJour() {
		// Controle de fin de partie
		if (!this.infos.isFinDeNiveau()) {

			// controle de la pause
			if (this.infos.isJeuEnPause()) {
				// jeu en pause
				if (this.timer.isRunning()) {
					// le timer tourne alors que le jeu est en pause
					// on arrete le timer
					this.timer.stop();
				}
			} else {
				// jeu en cours
				if (!this.timer.isRunning()) {
					// jeu en cours mais timer arreté
					// on remet le timer
					this.timer.start();
				}

				// controle de mise a jour du niveau
				if (this.compteurVitesse < (InfosNiveau.COEFFICIENT_VITESSE / this.infos
						.getVitesseJeu())) {
					// il ne faut pas encore mettre a jour
					this.compteurVitesse++;
				} else {
					// c'est le moment de mettre a jour

					// on regarde s'il reste des lemmings a sortir
					if (this.infos.getNombreLemmingsApparus() < this.infos
							.getNombreLemmingsTotal()) {
						// il reste des lemmings a sortir

						// controle d'apparition des lemmings
						if ((this.compteurApparitionLemmings < (InfosNiveau.COEFFICIENT_VITESSE / this.infos
								.getVitesseApparitionLemmings()))) {
							// ce n'est pas le moment
							// on incremente le compteur
							this.compteurApparitionLemmings++;
						} else {
							// c'est le moment
							// faire apparaitre un lemming se deplaçant vers la
							// droite
							// on recupere le dernier lemming a faire apparaitre
							// creer tous les lemmings
							Lemming lemming = new Lemming(new Direction(
									SensDeplacement.VERS_DROITE,
									this.carte.getPositionTrappe()));
							// **** exception jamais levée normalement
							try {
								// lui attribuer l'aptitude marcheur
								lemming.changerAptitude(new Marcheur(lemming,
										this.carte));
							}
							catch (AptitudeInvalideException e) {
								e.printStackTrace();
							}
							// l'ajouter aux lemming en jeu
							this.lemmingsDuNiveau.add(lemming);
							this.infos.setNombreLemmingsEnJeu(this.infos
									.getNombreLemmingsEnJeu() + 1);
							// il arrive
							lemming.setArrive();
							// on reinitialise le compteur
							this.compteurApparitionLemmings = 0;
						}
					} else {
						// tous les lemmings sont sortis
					}

					// il est temps de mettre a jour tous les lemmings
					for (Iterator<Lemming> it = this.lemmingsDuNiveau
							.iterator(); it.hasNext();) {
						Lemming l = it.next();
						// On met a jour ts les lemmings
						if (l.estMort()) {
							// s'il est mort depuis la derniere maj, on le sort
							// et on met a jour compteur
							this.infos.setNombreLemmingsMorts(this.infos
									.getNombreLemmingsMorts() + 1);
							this.infos.setNombreLemmingsEnJeu(this.infos
									.getNombreLemmingsEnJeu() - 1);
							it.remove();
						} else if (l.estSorti()) {
							// s'il est sorti depuis la derniere maj, on le sort
							// et on met a jour compteur
							this.infos.setNombreLemmingsSauves(this.infos
									.getNombreLemmingsSauves() + 1);
							this.infos.setNombreLemmingsEnJeu(this.infos
									.getNombreLemmingsEnJeu() - 1);
							it.remove();
						} else if (l.estArrive()) {
							// s'il est en jeu, on le met a jour
							l.miseAJour();
						} else {
							// le memming est en attente, on le mettra a jour a
							// son arrivée
						}
					}
					this.fireNiveauChanged();
					// on reinitialise le compteur
					this.compteurVitesse = 0;
				}
			}
		}
	}

	/**
	 * Sémantique : Quitte le niveau courant
	 * Préconditions :
	 * Postconditions :
	 */
	public void quitter() {
		System.exit(0);
	}

	public void removeListener(NiveauInfoListener listener) {
		this.listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String carte = this.carte.toString();
		StringBuffer str = new StringBuffer(carte);
		Position maxA = this.carte.getMaxAxes();

		for (Lemming l : this.lemmingsDuNiveau) {

			int posYlem = l.getDirectionActuelle().getPosition().getY();
			int lgLigne = maxA.getX() + 2;

			int charOfLem = (posYlem * lgLigne)
					+ l.getDirectionActuelle().getPosition().getX();

			str.setCharAt(charOfLem, this.lemmingsDuNiveau.get(0).toString()
					.charAt(0));
		}
		return str.toString();
	}

}