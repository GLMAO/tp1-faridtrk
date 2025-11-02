package org.emp.gl.timer.service;

// AJOUT: Importer l'interface standard de Java
import java.beans.PropertyChangeListener;

/**
 *
 * @author tina
 */

// MODIFIÉ: "extends PropertyChangeListener" 
public interface TimerChangeListener extends PropertyChangeListener {
    
    // Les constantes ne changent pas
    final static String DIXEME_DE_SECONDE_PROP = "dixième";
    final static String SECONDE_PROP = "seconde";
    final static String MINUTE_PROP = "minute";
    final static String HEURE_PROP = "heure";
    
    // MODIFIÉ: On supprime la méthode "propertyChange(String prop...)"
    // car elle est maintenant héritée de PropertyChangeListener
    // (sa nouvelle signature sera: propertyChange(PropertyChangeEvent evt))
}