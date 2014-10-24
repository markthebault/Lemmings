package Test;

import java.io.File;

import modele.jeu.FichierInvalideException;
import modele.jeu.partie.niveau.Niveau;
import vue.VueConsole;

public class TestLemmingsMarcheur {

	/**
	 * S�mantique :
	 * Pr�conditions :
	 * Postconditions :
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Niveau n = null;
		try {
			n = new Niveau(new File("resources/lvl_base.lemmings"));
			VueConsole vue = new VueConsole();
			while (true) {
				n.miseAJour();
				sleep(1);
				// System.out.println(n.toString());
			}

		}
		catch (FichierInvalideException e) {
			e.printStackTrace();
		}

	}

	public static void sleep(int ms) {

		// do what you want to do before sleeping
		try {
			Thread.currentThread().sleep(ms);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// sleep for 1000 ms
			// do what you want to do after sleeptig

	}

}
