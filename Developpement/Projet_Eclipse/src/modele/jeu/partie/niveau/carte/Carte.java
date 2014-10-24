package modele.jeu.partie.niveau.carte;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import modele.jeu.FichierInvalideException;
import modele.jeu.partie.niveau.lemming.Position;

/**
 * @author Mark
 *         Cette classe g�re les cases et informe les listeners �ventuelles de
 *         leur modification (ajout,retrait)
 */
public class Carte {
	/**
	 * les cases de la carte
	 */
	private Map<Position, Case> cases;
	/**
	 * position de la trappe d'entree de la carte
	 */
	private Position positionTrappe;

	/**
	 * S�mantique : Cr�e un Carte avec les cases en fonction du fichier et
	 * initialiser les paramètres
	 * Pr�conditions : Un fichier est passé en paramètre
	 * Postconditions :
	 * 
	 * @param fic
	 */
	public Carte(File fic) throws FichierInvalideException {

		// lecture du fichier texte
		try {
			// ouverture du fichier comme un flux
			InputStream ips = new FileInputStream(fic);
			// lecture du flux
			InputStreamReader ipsr = new InputStreamReader(ips);
			// insertion du flux dans un buffer
			BufferedReader buff = new BufferedReader(ipsr);

			String ligne;
			boolean debutMap = false;
			boolean sortiePresente = false;
			int numLigne = 0;
			// traitement pour chaque ligne
			while ((ligne = buff.readLine()) != null) {
				if (ligne.startsWith("map")) {
					// le debut le la map et initialisation de la HashMap cases
					debutMap = true;
					this.cases = new HashMap<Position, Case>();
					this.positionTrappe = null;
					//
				} else if (debutMap) {
					for (int x = 0; x < ligne.length(); x++) {
						// on récupère les caractères 1 à 1 et on suivant le
						// caractère on ajoute des cases
						switch (ligne.charAt(x)) {
							case '*':
								this.cases.put(new Position(x, numLigne),
										new Case());
								new Position(x, numLigne);
								// System.out.println("Ajout case * : "+this.getCase(p)+" --> "+p);
								break;
							case '+':
								this.cases.put(new Position(x, numLigne),
										new Case(SensDestruction.TOUT));
								break;
							case '<':
								this.cases.put(new Position(x, numLigne),
										new Case(SensDestruction.VERS_GAUCHE));
								break;
							case '>':
								this.cases.put(new Position(x, numLigne),
										new Case(SensDestruction.VERS_DROITE));
								break;
							case 'v':
								this.cases.put(new Position(x, numLigne),
										new Case(SensDestruction.VERS_BAS));
								break;
							case '@':
								// Si il y a une sortie on fait rien
								if (sortiePresente) {
									throw new FichierInvalideException(
											"Il y a plus d'une sortie sur la carte");
								}
								// Sinon création de la sortie
								else {
									this.cases.put(new Position(x, numLigne),
											new Sortie());
									sortiePresente = true;
								}
								break;
							case '%':
								this.cases.put(new Position(x, numLigne),
										new Piege(TypePiege.BROYEUR));
								break;
							case '!':
								this.cases.put(new Position(x, numLigne),
										new Piege(TypePiege.LANCE_FLAMME));
								break;
							case 'O':
								// Si la trappe n'a pas de position (pas
								// présente)
								if (this.positionTrappe == null) {
									this.positionTrappe = new Position(x,
											numLigne);
									this.cases.put(this.positionTrappe,
											new Trappe());
								} else {
									throw new FichierInvalideException(
											"Il y a plus d'une trappe sur la carte");
								}
								break;
						}// fin du case

					}// fin du pour

					numLigne++; // On passe à la ligne du dessous

				}// fin if debutmap

			}// fin du while
			buff.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
			throw new FichierInvalideException(
					"Donn�es de la carte invalides");
		}
	}

	/**
	 * S�mantique : Permet d'ajouter une case à la carte
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param p
	 * @throws CaseDejaPresenteException
	 */
	public void ajouterCase(Position p, Case c)
			throws CaseDejaPresenteException {
		
		//recherche la position correspondant a p
		Set<Position> keys = this.cases.keySet();
		Position postrouve = null;
		for(Position pos : keys)
		{
			if(p.equals(pos))
			{
				postrouve = pos;

			}
		}
		// Si une case est déjà présente à la position p, impossible
		// d'ajouter
		if (this.cases.containsKey(postrouve)) {
			throw new CaseDejaPresenteException(
					"Une Case est déjà à la position " + p);
		} else {
			this.cases.put(p, c);
		}
	}

	/**
	 * S�mantique : va enlever la case à la position p si il y en a une
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param p
	 */
	public void enleverCase(Position p) {
		
		//recherche la position correspondant a p
		Set<Position> keys = this.cases.keySet();
		Position postrouve = null;
		for(Position pos : keys)
		{
			if(p.equals(pos))
			{
				postrouve = pos;

			}
		}
		
		//si on l'a trouv� en enl�ve la case
		if(postrouve != null)
		{
			Case c = this.cases.remove(postrouve);
		}
		
	}

	/**
	 * S�mantique : retourne la case à la position passé en paramètre
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param p
	 */
	public Case getCase(Position p) {
		Set<Position> s = this.cases.keySet();
		for (Position pos : s) {
			if (p.equals(pos)) {
				return this.cases.get(pos);
			}
		}
		return null;
	}

	/**
	 * S�mantique : Renvoie les maximum de chaque axes, (xMax, yMax)
	 * Pr�conditions : carte initialis�e
	 * Postconditions : new Position(xMax, yMax)
	 * 
	 * @return
	 */
	public Position getMaxAxes() {
		int xMax = 0;
		int yMax = 0;

		// Recuperer toutes les positions
		Set<Position> touteCle = this.cases.keySet();
		for (Position pos : touteCle) {

			// si la position en x es superieur a la pr�c�dente, on la
			// sauvegarde
			if (pos.getX() > xMax) {
				xMax = pos.getX();
			}

			// si la position en y es superieur a la pr�c�dente, on la
			// sauvegarde
			if (pos.getY() > yMax) {
				yMax = pos.getY();
			}
		}

		return new Position(xMax, yMax);
	}

	/**
	 * S�mantique : Retourne la position de la trappe d'entrée
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @return
	 */
	public Position getPositionTrappe() {
		return new Position(this.positionTrappe);
	}

	/**
	 * S�mantique : Permet l'affichage de la carte
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		String affCarte = "";
		int xMax = this.getMaxAxes().getX();
		int yMax = this.getMaxAxes().getY();

		// génération du String
		for (int y = 0; y <= yMax; y++) { // pour toutes les ordonn�es [0, yMax]
			for (int x = 0; x <= xMax; x++) { // pour toutes les abscices [0,
												// xMax]
				// r�cuperer la case associ� a la position
				Case c = this.getCase(new Position(x, y));

				// si la case est nulle alors c'est qu'il n'y en a pas
				if (c == null) {
					// case nulle = espace
					affCarte += " ";
				} else {
					// si la case n'est pas nulle alors on l'affiche
					affCarte += c.toString();
				}

			}
			// sauter une ligne quand on a finit de traiter tous les x pour un y
			// donn�
			affCarte += "\n";
		}
		return affCarte;
	}

	public String toString2() {
		Set<Position> s = this.cases.keySet();
		String str = "";

		for (Position p : s) {
			str += ("Pos : " + p + " ---> " + this.cases.get(p)) + " ref : "
					+ this.cases.get(p).hashCode() + "\n";
		}
		return str;
	}
}