/**
 * 
 */
package modele.jeu.partie.niveau;

import java.util.EventObject;
import java.util.Map;

import modele.jeu.partie.niveau.lemming.aptitude.TypeAptitude;

/**
 * @author Paul
 * 
 */
public class InfosChangedEvent extends EventObject {

	/**
	 * Sémantique : Crée un DonneesEvent
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param source
	 * @param nombreTotal
	 * @param nombreASauver
	 * @param tempsImparti
	 * @param vitesseJeu
	 * @param vitesseApparition
	 */
	public InfosChangedEvent(InfosNiveau infos) {
		super(infos);
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
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.getDetailsAptitudes();
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
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.getNombreAptitude(nom);
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
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.getNombreLemmingsApparus();
	}

	/**
	 * Sémantique : Récupère le nombre de lemmings restant a sauver
	 * Préconditions :
	 * Postconditions : >= 0
	 * 
	 * @return le nombre de lemmings a sauver
	 */
	public int getNombreLemmingsASauver() {
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.getNombreLemmingsASauver();
	}

	/**
	 * Récupère le nombreLemmingsEnJeu
	 * 
	 * @return le nombreLemmingsEnJeu
	 */
	public int getNombreLemmingsEnJeu() {
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.getNombreLemmingsEnJeu();
	}

	/**
	 * Sémantique : Récupère le nombre de lemmings morts
	 * Préconditions :
	 * Postconditions : >=0 et <= getNombreLemmingsTotal()
	 * 
	 * @return le nombre de lemmings morts
	 */
	public int getNombreLemmingsMorts() {
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.getNombreLemmingsMorts();
	}

	/**
	 * Sémantique : Récupère le nombre de lemmings sauvés
	 * Préconditions :
	 * Postconditions : >= 0 et <= getNombreLemmingsASauver()
	 * 
	 * @return le nombre de lemmings sauves
	 */
	public int getNombreLemmingsSauves() {
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.getNombreLemmingsSauves();
	}

	/**
	 * Sémantique : Récupère le nombre total de lemmings du niveau
	 * Préconditions :
	 * Postconditions : >= 0
	 * 
	 * @return le nombre total de lemmings du niveau
	 */
	public int getNombreLemmingsTotal() {
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.getNombreLemmingsTotal();
	}

	/**
	 * Récupère le tempsImparti
	 * 
	 * @return le tempsImparti
	 */
	public int getTempsImparti() {
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.getTempsImparti();
	}

	/**
	 * Sémantique : Récupère le temps restant
	 * Préconditions :
	 * Postconditions : >= 0 && <= temps imparti
	 * 
	 * @return le temps restant
	 */
	public int getTempsRestant() {
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.getTempsRestant();
	}

	/**
	 * Sémantique : Récupère la vitesse d'apparition des lemmings
	 * Préconditions :
	 * Postconditions : >= 0 && <= VITESSE_APPARITION_LEMMINGS_MAX
	 * 
	 * @return la vitesse du jeu
	 */
	public int getVitesseApparitionLemmings() {
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.getVitesseApparitionLemmings();
	}

	/**
	 * Sémantique : Récupère le vitesseApparitionLemmingsMin
	 * Préconditions :
	 * Postconditions : >= 0 && <= VITESSE_APPARITION_LEMMINGS_MAX
	 * 
	 * @return le vitesseApparitionLemmingsMin
	 */
	public int getVitesseApparitionLemmingsMin() {
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.getVitesseApparitionLemmingsMin();
	}

	/**
	 * Sémantique : Récupère la vitesse du jeu
	 * Préconditions :
	 * Postconditions : >= 0 && <= VITESSE_JEU_MAX
	 * 
	 * @return la vitesse du jeu
	 */
	public int getVitesseJeu() {
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.getVitesseJeu();
	}

	/**
	 * Récupère le jeuEnPause
	 * 
	 * @return le jeuEnPause
	 */
	public boolean isJeuEnPause() {
		InfosNiveau infos = (InfosNiveau) this.source;
		return infos.isJeuEnPause();
	}
}
