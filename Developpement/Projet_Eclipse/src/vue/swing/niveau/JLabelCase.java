/**
 * 
 */
package vue.swing.niveau;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

import modele.jeu.partie.niveau.carte.Case;
import modele.jeu.partie.niveau.carte.Piege;
import modele.jeu.partie.niveau.carte.SensDestruction;
import modele.jeu.partie.niveau.carte.Sortie;
import modele.jeu.partie.niveau.carte.Trappe;
import modele.jeu.partie.niveau.carte.TypePiege;
import modele.jeu.partie.niveau.lemming.Lemming;
import modele.jeu.partie.niveau.lemming.SensDeplacement;
import modele.jeu.partie.niveau.lemming.aptitude.ApModifieur;
import modele.jeu.partie.niveau.lemming.aptitude.ApNormale;
import modele.jeu.partie.niveau.lemming.aptitude.Aptitude;
import modele.jeu.partie.niveau.lemming.aptitude.Bombeur;
import modele.jeu.partie.niveau.lemming.aptitude.Grimpeur;
import modele.jeu.partie.niveau.lemming.aptitude.Marcheur;
import modele.jeu.partie.niveau.lemming.aptitude.Parachutiste;
import modele.jeu.partie.niveau.lemming.aptitude.TypeAptitude;

/**
 * @author Paul
 * 
 */
public class JLabelCase extends JLabel {


	/**
	 * le estlemming
	 */
	private boolean estlemming;

	/**
	 * Sémantique : Crée un JLabelCase
	 * Préconditions :
	 * Postconditions :
	 * 
	 * @param couleurPrec
	 */
	public JLabelCase() {
		
		//proprietes du JLabel
		this.setOpaque(true);
		this.setSize(1, 1);
		this.setEnabled(true);
		
		
	
	}

	private final ImageIcon LEM_D = new ImageIcon("resources/Lem_verD.gif");
	private final ImageIcon LEM_G = new ImageIcon("resources/Lem_verG.gif");
	private final ImageIcon LEM_BLOCK = new ImageIcon("resources/Lem_block.gif");
	private final ImageIcon LEM_TOMBE = new ImageIcon("resources/Lem_tombe.gif");
	private final ImageIcon LEM_GRIMPE = new ImageIcon("resources/Lem_grimpe.gif");
	private final ImageIcon LEM_PARA = new ImageIcon("resources/Lem_para.gif");
	
	private final ImageIcon DESTRUCTIBLE = new ImageIcon("resources/RambiCrate.gif");
	private final ImageIcon DESTRUCTIBLE_D = new ImageIcon("resources/destructible_D.gif");
	private final ImageIcon DESTRUCTIBLE_G = new ImageIcon("resources/destructible_G.gif");
	private final ImageIcon DESTRUCTIBLE_B = new ImageIcon("resources/destructible_B.gif");
	
	private final ImageIcon PIEGE = new ImageIcon("resources/piege.png");
	private final ImageIcon INDESTRUCTIBLE = new ImageIcon("resources/Wall.gif");
	
	private final ImageIcon TRAPPE = new ImageIcon("resources/trappe.png");
	private final ImageIcon SORTIE = new ImageIcon("resources/sortie.gif");

	/**
	 * Sémantique : estLemmings() = faux, change la couleur de la case
	 * 		en fonction de son type
	 * Préconditions : c != null
	 * Postconditions : voir semantique
	 * @param c
	 */
	public void setCase(Case c) {
		
		//on colore la case alors ce n'est pas un lemmings
		this.estlemming = false;
		
		//rinitialiser le texte
		this.setText("");
		this.setIcon(null);
		
		if (c == null) {
			// case nulle = blanc
			this.setName("RIEN");
			this.setBackground(Color.WHITE);
		} else {
			// afficharge diffÃ©rent pour chaque case
			if (c instanceof Piege) {
				Piege p = (Piege) c;
				this.setIcon(PIEGE);
				/*switch (p.getType()) {
					case LANCE_FLAMME:
						this.setName(TypePiege.LANCE_FLAMME.name());
						this.setBackground(Color.RED);
						break;
					case BROYEUR:
						this.setName(TypePiege.BROYEUR.name());
						this.setBackground(Color.BLACK);
						break;
					default:
						this.setName("NULL");
						this.setBackground(Color.CYAN);
				}*/

			} else if (c instanceof Trappe) {
//				this.setName("TRAPPE");
//				this.setBackground(Color.YELLOW);
//				this.setText("T");
				
				this.setIcon(TRAPPE);
				
			} else if (c instanceof Sortie) {
//				this.setName("SORTIE");
//				this.setBackground(Color.GREEN);
//				this.setText("S");
				
				this.setIcon(SORTIE);
			} else {
				switch (c.getSensDestruction()) {
					case AUCUN:
//						this.setName(SensDestruction.AUCUN.name());
//						this.setBackground(Color.DARK_GRAY);
						
						this.setIcon(INDESTRUCTIBLE);
						break;
					case TOUT:
						this.setName(SensDestruction.TOUT.name());
						//this.setBackground(Color.LIGHT_GRAY);
						
						this.setIcon(DESTRUCTIBLE);
						break;
					case VERS_DROITE:
						this.setName(SensDestruction.VERS_DROITE.name());
						//this.setBackground(Color.LIGHT_GRAY);
						//this.setText(">");
						
						this.setIcon(DESTRUCTIBLE_D);
						break;
					case VERS_GAUCHE:
						this.setName(SensDestruction.VERS_GAUCHE.name());
						//this.setBackground(Color.LIGHT_GRAY);
						//this.setText("<");
						this.setIcon(DESTRUCTIBLE_G);
						break;
					case VERS_BAS:
						this.setName(SensDestruction.VERS_BAS.name());
						//this.setBackground(Color.LIGHT_GRAY);
						//this.setText("v");
						this.setIcon(DESTRUCTIBLE_B);
					
						break;
					default:
						this.setName("NULL");
						this.setBackground(Color.MAGENTA);
				}

			}
		}
		this.setBorder(BorderFactory.createEmptyBorder());
	}






	/**
	 * Sémantique : estLemmings() = vrai et change la couleur de la case en fonction
	 * 		du lemmings
	 * Préconditions : lem != null
	 * Postconditions : voir semantique
	 * @param lem
	 */
	public void setlemmingColor(Lemming lem) {
		
		//on colore le lemming alors c'est  un lemming
		this.estlemming = true;
		
		//recuperer le type d'aptitude
		Aptitude ap = lem.getAptitudeEnCours();
		TypeAptitude tAp = lem.getAptitudeEnCours().getType();
		
		//rinitialiser le texte
		this.setText("");

		if(lem.getDirectionActuelle().getSens() == SensDeplacement.VERS_DROITE)
			this.setIcon(LEM_D);
		else
			this.setIcon(LEM_G);
		
		Marcheur m = null;
		switch(tAp)
		{
			case WALKER:
			case CLIMBER:
			case FLOATER :
				//this.setBackground(Color.CYAN);

				
				//recupere l'aptitude
				m = (Marcheur)lem.getAptitudeEnCours();
				
				if(m.tombe())
				{
					if(tAp == TypeAptitude.FLOATER)
						this.setIcon(LEM_PARA);
					else
						this.setIcon(LEM_TOMBE);
				}
				
				if(m.grimpe())
					this.setIcon(LEM_GRIMPE);
				
				

				break;
				
				
				
			case BLOCKER:
//				this.setBackground(Color.RED);
				//this.setText("B");
				
				this.setIcon(LEM_BLOCK);
				break;
				
			case DIGGER:
//				this.setBackground(Color.ORANGE);
				//this.setText("D");
				break;
			case MINER:
//				this.setBackground(Color.ORANGE);
				//this.setText("M");
				break;
				
			case BUILDER:
//				this.setBackground(Color.GREEN);
				//this.setText("B");
				break;
				
			case BASHER:
//				this.setBackground(Color.ORANGE);
				//this.setText("B");
				break;

		}
		
		//teste si le lemmings a un special
		if(ap instanceof ApNormale)
		{
			ApNormale apn = (ApNormale)ap;
			//teste si le special est une bombe
			if(apn.getSpecial() != null && apn.getSpecial() instanceof Bombeur)
			{
				Bombeur b = (Bombeur)apn.getSpecial();
				//affiche la bombe
				this.setBackground(Color.RED);
				
				//affiche le temps restant de la bombe
				//this.setText(""+b.getCompteARebours());
			}
		}


	}

	/**
	 * Sémantique : Rajoute des bordures au label
	 * Préconditions :
	 * Postconditions :
	 */
	public void setLemmingsSelectionne() {

			// TODO faire qqchose de plus joli
			//this.setBorder(BorderFactory.createLineBorder(Color.RED));
	}


	/**
	 * Sémantique :Renvoie vrai si le label represente un lemmings
	 * Préconditions : une fonction telle que setCase() ou setLemmingsColor() a été appele
	 * Postconditions :Renvoie vrai si le label represente un lemmings
	 * @return
	 */
	public boolean estlemming() {
		// TODO Auto-generated method stub
		return this.estlemming;
	}
	
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setBackground(java.awt.Color)
	 */
	public void setBackground(Color col)
	{
		//change la couleur uniquement si elle est différente de l'état d'avant
		if(this.getBackground() != null && !this.getBackground().equals(col))
		{
			super.setBackground(col);
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JLabel#setIcon(javax.swing.Icon)
	 */
	public void setIcon(Icon ic)
	{
		//on regarde si l'icone est differente avant de la detruire
		if(this.getIcon() != ic)
			super.setIcon(ic);
	}
	
}
