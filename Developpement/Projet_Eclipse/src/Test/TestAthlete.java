package Test;

import modele.jeu.FichierInvalideException;
import controleur.JeuController;

public class TestAthlete {
	public static void main(String[] args) throws InterruptedException {
		final JeuController jn;

		try {
			jn = new JeuController("resources/testAthlete.lemmings");
			jn.lancerJeu();
		}
		catch (FichierInvalideException e) {
			e.printStackTrace();
		}
	}
}
