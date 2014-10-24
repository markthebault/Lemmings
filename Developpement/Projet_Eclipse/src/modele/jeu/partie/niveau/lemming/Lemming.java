package modele.jeu.partie.niveau.lemming;

import java.util.Stack;

import modele.jeu.partie.niveau.lemming.aptitude.ApFinale;
import modele.jeu.partie.niveau.lemming.aptitude.ApModifieur;
import modele.jeu.partie.niveau.lemming.aptitude.ApNormale;
import modele.jeu.partie.niveau.lemming.aptitude.ApSpeciale;
import modele.jeu.partie.niveau.lemming.aptitude.Aptitude;
import modele.jeu.partie.niveau.lemming.aptitude.AptitudeInvalideException;
import modele.jeu.partie.niveau.lemming.aptitude.Grimpeur;
import modele.jeu.partie.niveau.lemming.aptitude.Marcheur;
import modele.jeu.partie.niveau.lemming.aptitude.Parachutiste;

public class Lemming {

	/**
	 * si le lemmings est sorti
	 */
	private boolean sorti;

	/**
	 * l'aptitudes
	 */
	private Stack<Aptitude> aptitudes;
	/**
	 * si le lemmings est mort
	 */
	private boolean mort;

	/**
	 * si le lemmings est arrive
	 */
	private boolean arrive;

	/**
	 * la directionActuelle
	 */
	private Direction directionActuelle;

	/**
	 * S�mantique : Cr�e un Lemming
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param direction
	 */
	public Lemming(Direction direction) {
		this.sorti = false;
		this.mort = false;
		this.directionActuelle = direction;
		this.aptitudes = new Stack<Aptitude>();
	}

	/**
	 * S�mantique : change d
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param ap
	 */
	public void changerAptitude(Aptitude ap) throws AptitudeInvalideException {

		boolean aptitudePriseEnCompte = false;

		if ((this.aptitudes.size() < 2) || (ap instanceof ApFinale)
				|| (ap instanceof ApSpeciale)) {
			// test si le lemmings a deja 2
			// aptitudes

			// si on a pas d'aptitude on ne peut que accepter les marcheurs
			if (this.aptitudes.size() == 0) {
				// on a pas d'aptitudes, on ajoute que les marcheurs (et
				// extentions
				// de marcheurs)
				if (ap instanceof Marcheur) {
					this.aptitudes.add(ap);
					aptitudePriseEnCompte = true;
				} else {
					throw new AptitudeInvalideException(
							"Le lemmings de base doit recevoir un marcheur");
				}
			} else {
				// si on essaye de ajouter au lemmings la facult� de modifier
				// la carte
				if ((ap instanceof ApModifieur)
						&& (this.aptitudes.peek() instanceof Marcheur)) {
					// empile le modifieur
					this.aptitudes.add(ap);
					aptitudePriseEnCompte = true;
				}

				// on suppose que l'ont veut un grimpeur sur un parachutiste
				if ((ap instanceof Grimpeur)
						&& (this.aptitudes.peek() instanceof Parachutiste)) {
					// recuprer le grimpeur
					Parachutiste para = (Parachutiste) this.aptitudes.peek();
					if (para.getGrappin() == null) {
						para.setGrappin((Grimpeur) ap);
					} else {
						throw new AptitudeInvalideException(
								"Le lemming a deja un grapin.");
					}
					aptitudePriseEnCompte = true;
				}

				// on suppose que l'ont veut mettre un parachute sur un grimpeur
				if ((ap instanceof Parachutiste)
						&& (this.aptitudes.peek() instanceof Grimpeur)) {
					// recuprer le grimpeur
					Grimpeur grimp = (Grimpeur) this.aptitudes.peek();
					if (grimp.getParachute() == null) {
						grimp.setParachute((Parachutiste) ap);
					} else {
						throw new AptitudeInvalideException(
								"Le lemming a deja un parachute.");
					}
					aptitudePriseEnCompte = true;
				}

				// si l'ont essaye d'ajouter soit un parachute soit des grapins
				// sur
				// un marcheur,
				// on remplace le marcheur par cette nouvelle instance
				if ((ap instanceof Marcheur)
						&& !(this.aptitudes.peek() instanceof Parachutiste)
						&& !(this.aptitudes.peek() instanceof Grimpeur)) {
					// recuperer le special
					ApSpeciale spe = ((Marcheur) this.aptitudes.peek())
							.getSpecial();

					// enlever le marcheur
					this.aptitudes.pop();

					// ajouter le nouveau marcheur
					Marcheur m = (Marcheur) ap;
					m.setSpecial(spe);
					this.aptitudes.add(m);

					aptitudePriseEnCompte = true;
				}

				// ajouter bombe a toutes les instance de ApNormal
				if (ap instanceof ApSpeciale) {
					ApNormale n = (ApNormale) this.aptitudes.peek();

					// verifie que le lemmings n'a pas deja de bombe
					if (n.getSpecial() != null) {
						throw new AptitudeInvalideException(
								"Le lemmings a deja une bombe");
					}

					else {
						n.setSpecial((ApSpeciale) ap);
						aptitudePriseEnCompte = true;
					}
				}

				// on teste si on veut mettre un bloqueur sur un bloqueur
				if (ap instanceof ApFinale) {
					if (!(this.aptitudes.peek() instanceof ApFinale)) {
						// Remplace le bloqueur par les autres aptitudes
						this.aptitudes.pop();
						this.aptitudes.add(ap);
						aptitudePriseEnCompte = true;
					} else {
						throw new AptitudeInvalideException(
								"Le lemmings est deja bloqueur");
					}
				}

			}
		} else {
			throw new AptitudeInvalideException(
					"Le lemmings a deja trop d'aptitudes");
		}

		if (!aptitudePriseEnCompte) {
			throw new AptitudeInvalideException("Aptitude non geree");
		}
	}

	/**
	 * S�mantique : Enleve aptitude en cour
	 * Pr�conditions : l'aptitude en cour n'est pas une instance de Marcheur
	 * Postconditions : l'aptitude est enlevé de la pile
	 * 
	 * @throws AptitudeInvalideException
	 * 
	 */
	public void enleverAptitude() {
		this.aptitudes.pop();
	}

	/**
	 * R�cup�re le arrive
	 * 
	 * @return le arrive
	 */
	public boolean estArrive() {
		return this.arrive;
	}

	/**
	 * S�mantique : indique si le lemmings est mort
	 * Pr�conditions :
	 * Postconditions : retourne vrai si le lemmings est mort, non sinon
	 * 
	 * @return
	 */
	public boolean estMort() {
		return this.mort;
	}

	/**
	 * S�mantique : indique si le lemmins est sorti
	 * Pr�conditions :
	 * Postconditions : retourne vrai si le lemmings
	 * est sortie par la sortie de la carte
	 * 
	 * @return
	 */
	public boolean estSorti() {
		return this.sorti;
	}

	/**
	 * S�mantique :
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @return
	 */
	public Aptitude getAptitudeEnCours() {
		return this.aptitudes.lastElement();
	}

	public Direction getDirectionActuelle() {
		return this.directionActuelle;
	}

	/**
	 * S�mantique : appele la fonction executer de la derniere aptitude du
	 * lemmings
	 * Pr�conditions : le lemmings doit avoir au moin une aptitude
	 * Postconditions : l'aptitude sera execut�e
	 * 
	 */
	public void miseAJour() {
		Position positionPrec = new Position(this.getDirectionActuelle()
				.getPosition());
		this.aptitudes.peek().executer();
	}

	/**
	 * Modifie le arrive
	 * 
	 * @param arrive
	 *            le nouveau arrive
	 */
	public void setArrive() {
		this.arrive = true;
	}

	public void setDirectionActuelle(Direction directionActuelle) {
		this.directionActuelle = directionActuelle;
	}

	/**
	 * S�mantique : Fais sortir le lemmings
	 * Pr�conditions :
	 * Postconditions : le lemmings sors du jeux
	 * 
	 */
	public void sortir() {
		this.sorti = true;
	}

	/*
	 * @see java.lang.Object#toString()
	 * Affiche un L pour repr�senter un lemmings
	 */
	@Override
	public String toString() {
		return "L";
	}

	/**
	 * S�mantique : tue le lemmings
	 * Pr�conditions : le lemming existe
	 * Postconditions : lemmings est tuer
	 * 
	 */
	public void tuer() {
		this.mort = true;
	}
}