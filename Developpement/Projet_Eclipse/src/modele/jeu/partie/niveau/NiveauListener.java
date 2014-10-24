/**
 * 
 */
package modele.jeu.partie.niveau;

import java.util.EventListener;

/**
 * @author Paul
 * 
 */
public interface NiveauListener extends EventListener {
	public void niveauChanged(NiveauChangedEvent evt);
}
