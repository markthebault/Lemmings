/**
 * 
 */
package modele.jeu.partie.niveau;

import java.util.EventListener;

/**
 * @author Paul
 * 
 */
public interface NiveauInfoListener extends EventListener {
	public void aptitudeChanged(InfosChangedEvent evt);

	public void nombreLemmingsEnJeuChanged(InfosChangedEvent evt);

	public void nombreLemmingsMortsChanged(InfosChangedEvent evt);

	public void nombreLemmingsSauvesChanged(InfosChangedEvent evt);

	public void pauseChanged(InfosChangedEvent evt);

	public void tempsRestantChanged(InfosChangedEvent evt);

	public void vitesseApparitionChanged(InfosChangedEvent evt);

	public void vitesseJeuChanged(InfosChangedEvent evt);
}
