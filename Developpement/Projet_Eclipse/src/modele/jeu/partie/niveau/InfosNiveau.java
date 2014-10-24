/**
 * 
 */
package modele.jeu.partie.niveau;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import modele.jeu.partie.niveau.lemming.aptitude.TypeAptitude;

/**
 * Cette classe regroupe toutes les infos du niveau, permettant ainsi
 * d'alléger celle-ci.
 * De plus cela permet d'encapsuler ces infos et ne pas donner acces au
 * niveau directement.
 */
public class InfosNiveau {

	/**
	 * le niveau est terminé (temps ecoulé, ts lemmings mort ou sortis)
	 */
	private boolean finDeNiveau;
	/**
	 * le joueur a gagne (nombre de lemmings demandé sortis)
	 */
	private boolean gagne;

	/**
	 * les listeners
	 */
	ArrayList<NiveauInfoListener> listeners;
	/**
	 * coefficient permettant de transformer la vitesse en période tout en
	 * restant > 0
	 */
	public static int COEFFICIENT_VITESSE = 1000;
	// //// LES VITESSES D'APPARITION
	/**
	 * le vitesseApparitionLemmingsMax
	 */
	public static int VITESSE_APPARITION_LEMMINGS_MAX = 1000;
	/**
	 * la vitesse d'apparition des lemmings min
	 */
	public static int VITESSE_APPARITION_LEMMINGS_MIN = 1;
	/**
	 * la vitesse d'apparition des lemmings
	 */
	private int vitesseApparitionLemmings;
	/**
	 * le vitesse d'Apparition des Lemmings Minimum
	 */
	private int vitesseApparitionLemmingsMin;
	// //// LES VITESSES DE JEU
	/**
	 * le VITESSE_JEU_MIN
	 */
	public static int VITESSE_JEU_MIN = 1;
	/**
	 * le vitesseJeuMax
	 */
	public static int VITESSE_JEU_MAX = 10;
	/**
	 * le vitesseJeu
	 */
	private int vitesseJeu;
	// //// LES NOMBRES DE LEMMINGS
	/**
	 * le nombre de lemmings total du niveau
	 */
	private int nombreLemmingsTotal;
	/**
	 * le nombre de lemmings morts
	 */
	private int nombreLemmingsMorts;
	/**
	 * le nombre de lemmings a sauver
	 */
	private int nombreLemmingsASauver;
	/**
	 * le nombre de lemmings sauvés
	 */
	private int nombreLemmingsSauves;
	/**
	 * le nombreLemmingsEnJeu
	 */
	private int nombreLemmingsEnJeu;
	// //// LES TEMPS DE JEU
	/**
	 * le temps imparti pour finir le niveau
	 */
	private int tempsImparti;
	/**
	 * le temps restant pour sauver tous les lemmings
	 */
	private int tempsRestant;
	// //// LE FICHIER CARACTERISANT LES INFOS DU NIVEAU
	private File fichierDuNiveau;
	// //// LES APTITUDES
	/**
	 * le detail des aptitudes (nom et nombre par aptitude)
	 */
	private Map<TypeAptitude, Integer> detailsAptitudes;
	/**
	 * le jeuEnPause
	 */
	private boolean jeuEnPause;

	/**
	 * Sémantique : Crée un infosNiveau
	 * Préconditions :
	 * Postconditions :
	 */
	public InfosNiveau() {
		this.listeners = new ArrayList<>();
		this.detailsAptitudes = new TreeMap<>();
		this.fichierDuNiveau = null;
		this.jeuEnPause = false;
		this.nombreLemmingsASauver = 0;
		this.nombreLemmingsMorts = 0;
		this.nombreLemmingsSauves = 0;
		this.nombreLemmingsTotal = 0;
		this.tempsImparti = 0;
		this.tempsRestant = 0;
		this.vitesseApparitionLemmings = VITESSE_APPARITION_LEMMINGS_MIN;
		this.vitesseApparitionLemmingsMin = VITESSE_APPARITION_LEMMINGS_MIN;
		this.vitesseJeu = VITESSE_JEU_MIN;
	}

	/**
	 * Modifie le detailsAptitudes
	 * 
	 * @param detailsAptitudes
	 *            le nouveau detailsAptitudes
	 */
	protected void addDetailsAptitudes(TypeAptitude nom, Integer nombre) {
		this.detailsAptitudes.put(nom, nombre);
		this.fireAptitudeChanged();
	}

	public void addListener(NiveauInfoListener listener) {
		this.listeners.add(listener);
	}

	private void fireAptitudeChanged() {
		for (NiveauInfoListener l : this.listeners) {
			l.aptitudeChanged(new InfosChangedEvent(this));
		}
	}

	public void fireNombreLemmingsEnJeuChanged() {
		for (NiveauInfoListener l : this.listeners) {
			l.nombreLemmingsEnJeuChanged(new InfosChangedEvent(this));
		}
	}

	public void fireNombreLemmingsMortsChanged() {
		for (NiveauInfoListener l : this.listeners) {
			l.nombreLemmingsMortsChanged(new InfosChangedEvent(this));
		}
	}

	public void fireNombreLemmingsSauvesChanged() {
		for (NiveauInfoListener l : this.listeners) {
			l.nombreLemmingsSauvesChanged(new InfosChangedEvent(this));
		}
	}

	public void firePauseChanged() {
		for (NiveauInfoListener l : this.listeners) {
			l.pauseChanged(new InfosChangedEvent(this));
		}
	}

	public void fireTempsRestantChanged() {
		for (NiveauInfoListener l : this.listeners) {
			l.tempsRestantChanged(new InfosChangedEvent(this));
		}
	}

	public void fireVitesseApparitionChanged() {
		for (NiveauInfoListener l : this.listeners) {
			l.vitesseApparitionChanged(new InfosChangedEvent(this));
		}
	}

	public void fireVitesseJeuChanged() {
		for (NiveauInfoListener l : this.listeners) {
			l.vitesseJeuChanged(new InfosChangedEvent(this));
		}
	}

	/**
	 * Sémantique : Récupère le detail des aptitudes possibles (nom et nombre
	 * restant par aptitude)
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @return le detail des aptitudes
	 */
	public Map<TypeAptitude, Integer> getDetailsAptitudes() {
		return this.detailsAptitudes;
	}

	/**
	 * Récupère le fichierDuNiveau
	 * 
	 * @return le fichierDuNiveau
	 */
	public File getFichierDuNiveau() {
		return this.fichierDuNiveau;
	}

	/**
	 * Sémantique : Récupère le nombre d'aptitudes de ce type
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param nom
	 * @return
	 */
	public int getNombreAptitude(TypeAptitude nom) {
		return this.detailsAptitudes.get(nom);
	}

	/**
	 * Sémantique : Récupère le nombre de lemmings apparus depuis le début du
	 * niveau
	 * Préconditions :
	 * Postconditions : < nombreLemmingsTotal
	 * 
	 * @return le nombre de lemmings apparus
	 */
	public int getNombreLemmingsApparus() {
		return this.nombreLemmingsEnJeu + this.nombreLemmingsMorts
				+ this.nombreLemmingsSauves;
	}

	/**
	 * Sémantique : Récupère le nombre de lemmings restant a sauver
	 * Préconditions :
	 * Postconditions : >= 0
	 * 
	 * @return le nombre de lemmings a sauver
	 */
	public int getNombreLemmingsASauver() {
		return this.nombreLemmingsASauver;
	}

	/**
	 * Récupère le nombreLemmingsEnJeu
	 * 
	 * @return le nombreLemmingsEnJeu
	 */
	public int getNombreLemmingsEnJeu() {
		return this.nombreLemmingsEnJeu;
	}

	/**
	 * Sémantique : Récupère le nombre de lemmings morts
	 * Préconditions :
	 * Postconditions : >=0 et <= getNombreLemmingsTotal()
	 * 
	 * @return le nombre de lemmings morts
	 */
	public int getNombreLemmingsMorts() {
		return this.nombreLemmingsMorts;
	}

	/**
	 * Sémantique : Récupère le nombre de lemmings sauvés
	 * Préconditions :
	 * Postconditions : >= 0 et <= getNombreLemmingsASauver()
	 * 
	 * @return le nombre de lemmings sauves
	 */
	public int getNombreLemmingsSauves() {
		return this.nombreLemmingsSauves;
	}

	/**
	 * Sémantique : Récupère le nombre total de lemmings du niveau
	 * Préconditions :
	 * Postconditions : >= 0
	 * 
	 * @return le nombre total de lemmings du niveau
	 */
	public int getNombreLemmingsTotal() {
		return this.nombreLemmingsTotal;
	}

	/**
	 * Récupère le tempsImparti
	 * 
	 * @return le tempsImparti
	 */
	public int getTempsImparti() {
		return this.tempsImparti;
	}

	/**
	 * Sémantique : Récupère le temps restant
	 * Préconditions :
	 * Postconditions : >= 0 && <= temps imparti
	 * 
	 * @return le temps restant
	 */
	public int getTempsRestant() {
		return this.tempsRestant;
	}

	/**
	 * Sémantique : Récupère la vitesse d'apparition des lemmings
	 * Préconditions :
	 * Postconditions : >= 0 && <= VITESSE_APPARITION_LEMMINGS_MAX
	 * 
	 * @return la vitesse du jeu
	 */
	public int getVitesseApparitionLemmings() {
		return this.vitesseApparitionLemmings;
	}

	/**
	 * Sémantique : Récupère le vitesseApparitionLemmingsMin
	 * Préconditions :
	 * Postconditions : >= 0 && <= VITESSE_APPARITION_LEMMINGS_MAX
	 * 
	 * @return le vitesseApparitionLemmingsMin
	 */
	public int getVitesseApparitionLemmingsMin() {
		return this.vitesseApparitionLemmingsMin;
	}

	/**
	 * Sémantique : Récupère la vitesse du jeu
	 * Préconditions :
	 * Postconditions : >= 0 && <= VITESSE_JEU_MAX
	 * 
	 * @return la vitesse du jeu
	 */
	public int getVitesseJeu() {
		return this.vitesseJeu;
	}

	/**
	 * Récupère le finDeNiveau
	 * 
	 * @return le finDeNiveau
	 */
	public boolean isFinDeNiveau() {

		return (this.finDeNiveau || ((this.nombreLemmingsTotal == this
				.getNombreLemmingsApparus()) && (this.nombreLemmingsEnJeu == 0)));
	}

	/**
	 * Récupère le gagne
	 * 
	 * @return le gagne
	 */
	public boolean isGagne() {
		return (this.gagne || (this.nombreLemmingsSauves >= this.nombreLemmingsASauver));
	}

	/**
	 * Récupère le jeuEnPause
	 * 
	 * @return le jeuEnPause
	 */
	public boolean isJeuEnPause() {
		return this.jeuEnPause;
	}

	public void removeListener(NiveauInfoListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * Modifie le fichierDuNiveau
	 * 
	 * @param fichierDuNiveau
	 *            le nouveau fichierDuNiveau
	 */
	protected void setFichierDuNiveau(File fichierDuNiveau) {
		this.fichierDuNiveau = fichierDuNiveau;
	}

	/**
	 * Modifie le finDeNiveau
	 * 
	 * @param finDeNiveau
	 *            le nouveau finDeNiveau
	 */
	public void setFinDeNiveau(boolean finDeNiveau) {
		this.finDeNiveau = finDeNiveau;
	}

	/**
	 * Modifie le gagne
	 * 
	 * @param gagne
	 *            le nouveau gagne
	 */
	public void setGagne(boolean gagne) {
		this.gagne = gagne;
	}

	/**
	 * Modifie le jeuEnPause
	 * 
	 * @param jeuEnPause
	 *            le nouveau jeuEnPause
	 */
	public void setJeuEnPause(boolean jeuEnPause) {
		this.jeuEnPause = jeuEnPause;
		this.firePauseChanged();
	}

	/**
	 * Modifie le nombreLemmingsASauver
	 * 
	 * @param nombreLemmingsASauver
	 *            le nouveau nombreLemmingsASauver
	 */
	protected void setNombreLemmingsASauver(int nombreLemmingsASauver) {
		this.nombreLemmingsASauver = nombreLemmingsASauver;
	}

	/**
	 * Modifie le nombreLemmingsEnJeu
	 * 
	 * @param nombreLemmingsEnJeu
	 *            le nouveau nombreLemmingsEnJeu
	 */
	protected void setNombreLemmingsEnJeu(int nombreLemmingsEnJeu) {
		this.nombreLemmingsEnJeu = nombreLemmingsEnJeu;
		this.fireNombreLemmingsEnJeuChanged();
	}

	/**
	 * Modifie le nombreLemmingsMorts
	 * 
	 * @param nombreLemmingsMorts
	 *            le nouveau nombreLemmingsMorts
	 */
	protected void setNombreLemmingsMorts(int nombreLemmingsMorts) {
		this.nombreLemmingsMorts = nombreLemmingsMorts;
		this.fireNombreLemmingsMortsChanged();
	}

	/**
	 * Modifie le nombreLemmingsSauves
	 * 
	 * @param nombreLemmingsSauves
	 *            le nouveau nombreLemmingsSauves
	 */
	protected void setNombreLemmingsSauves(int nombreLemmingsSauves) {
		this.nombreLemmingsSauves = nombreLemmingsSauves;
		this.fireNombreLemmingsSauvesChanged();
	}

	/**
	 * Modifie le nombreLemmingsTotal
	 * 
	 * @param nombreLemmingsTotal
	 *            le nouveau nombreLemmingsTotal
	 */
	protected void setNombreLemmingsTotal(int nombreLemmingsTotal) {
		this.nombreLemmingsTotal = nombreLemmingsTotal;
	}

	/**
	 * Modifie le tempsImparti
	 * 
	 * @param tempsImparti
	 *            le nouveau tempsImparti
	 */
	protected void setTempsImparti(int tempsImparti) {
		this.tempsImparti = tempsImparti;
	}

	/**
	 * Modifie le tempsRestant
	 * 
	 * @param tempsRestant
	 *            le nouveau tempsRestant
	 */
	protected void setTempsRestant(int tempsRestant) {
		this.tempsRestant = tempsRestant;
		this.fireTempsRestantChanged();
	}

	/**
	 * Sémantique : Modifie la vitesse d'apparition des lemmings
	 * Préconditions :
	 * Postconditions : old.vitesseApparitionLemmings =
	 * vitesseApparitionLemmings &&
	 * vitesseApparitionLemmings>=vitesseApparitionLemmingsMin &&
	 * vitesseApparitionLemmings <= VITESSE_APPARITION_LEMMINGS_MAX
	 * 
	 * @param la
	 *            nouvelle vitesse d'apparition des lemmings
	 */
	public void setVitesseApparition(int vitesseApparitionLemmings) {
		if (vitesseApparitionLemmings >= VITESSE_APPARITION_LEMMINGS_MAX) {
			this.vitesseApparitionLemmings = VITESSE_APPARITION_LEMMINGS_MAX;
		} else if (vitesseApparitionLemmings <= this.vitesseApparitionLemmingsMin) {
			this.vitesseApparitionLemmings = this.vitesseApparitionLemmingsMin;
		} else {
			this.vitesseApparitionLemmings = vitesseApparitionLemmings;
		}
		this.fireVitesseApparitionChanged();
	}

	/**
	 * Modifie le vitesseApparitionLemmingsMin
	 * 
	 * @param vitesseApparitionLemmingsMin
	 *            le nouveau vitesseApparitionLemmingsMin
	 */
	protected void setVitesseApparitionLemmingsMin(
			int vitesseApparitionLemmingsMin) {
		if (vitesseApparitionLemmingsMin <= VITESSE_APPARITION_LEMMINGS_MIN) {
			this.vitesseApparitionLemmingsMin = VITESSE_APPARITION_LEMMINGS_MIN;
		} else {
			this.vitesseApparitionLemmingsMin = vitesseApparitionLemmingsMin;
		}
	}

	/**
	 * Sémantique : Modifie la vitesse du jeu
	 * Préconditions :
	 * Postconditions : old.vitesseJeu = vitesseJeu &&
	 * vitesseJeu >= 1 && vitesseJeu <= VITESSE_JEU_MAX
	 * 
	 * @param vitesseJeu
	 *            la nouvelle vitesse du jeu
	 */
	public void setVitesseJeu(int vitesseJeu) {
		if (vitesseJeu <= 1) {
			this.vitesseJeu = 1;
		} else if (vitesseJeu >= VITESSE_JEU_MAX) {
			this.vitesseJeu = VITESSE_JEU_MAX;
		} else {
			this.vitesseJeu = vitesseJeu;
		}
		this.fireVitesseJeuChanged();
	}
}
