package org.emp.gl.time.service.impl;

import java.time.LocalTime;
// import java.util.LinkedList; // SUPPRIMÉ
// import java.util.List;       // SUPPRIMÉ
import java.util.Timer;
import java.util.TimerTask;

// AJOUT: Imports pour la solution sécurisée
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeEvent;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

public class DummyTimeServiceImpl implements TimerService {

    int dixiemeDeSeconde;
    int minutes;
    int secondes;
    int heures;
    
    // SUPPRIMÉ: La liste manuelle qui causait le bogue
    // List<TimerChangeListener> listeners = new LinkedList<>();

    // AJOUT (Partie e): L'outil de gestion des observeurs
    private PropertyChangeSupport support;

   public DummyTimeServiceImpl() {
        
        // AJOUT: Initialiser l'outil EN PREMIER
        support = new PropertyChangeSupport(this); 
        
        setTimeValues(); // APPELÉ EN SECOND (maintenant c'est bon)

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timeChanged();
            }
        };
        timer.scheduleAtFixedRate(task, 100, 100);
    }

    private void setTimeValues() {
        LocalTime localTime = LocalTime.now();
        setSecondes(localTime.getSecond());
        setMinutes(localTime.getMinute());
        setHeures(localTime.getHour());
        setDixiemeDeSeconde(localTime.getNano() / 100000000);
    }

    @Override
    public void addTimeChangeListener(TimerChangeListener pl) {
        // MODIFIÉ: On délègue l'ajout à l'outil sécurisé
        support.addPropertyChangeListener(pl);
    }

    @Override
    public void removeTimeChangeListener(TimerChangeListener pl) {
        // MODIFIÉ: On délègue la suppression à l'outil sécurisé
        support.removePropertyChangeListener(pl);
    }

    private void timeChanged() {
        setTimeValues();
    }

    public void setDixiemeDeSeconde(int newDixiemeDeSeconde) {
        if (dixiemeDeSeconde == newDixiemeDeSeconde)
            return;
        int oldValue = dixiemeDeSeconde;
        dixiemeDeSeconde = newDixiemeDeSeconde;
        dixiemeDeSecondesChanged(oldValue, dixiemeDeSeconde);
    }

    private void dixiemeDeSecondesChanged(int oldValue, int newValue) {
        // MODIFIÉ: On remplace la boucle "for" par "firePropertyChange"
        // C'est cette méthode qui gère la notification de manière sécurisée
        support.firePropertyChange(TimerChangeListener.DIXEME_DE_SECONDE_PROP, 
                                   oldValue, newValue);
    }

    public void setSecondes(int newSecondes) {
        if (secondes == newSecondes)
            return;
        int oldValue = secondes;
        secondes = newSecondes;
        secondesChanged(oldValue, secondes);
    }

    private void secondesChanged(int oldValue, int newValue) {
        // MODIFIÉ: Remplacement de la boucle for
        support.firePropertyChange(TimerChangeListener.SECONDE_PROP, 
                                   oldValue, newValue);
    }

    public void setMinutes(int newMinutes) {
        if (minutes == newMinutes)
            return;
        int oldValue = minutes;
        minutes = newMinutes;
        minutesChanged(oldValue, minutes);
    }

    private void minutesChanged(int oldValue, int newValue) {
        // MODIFIÉ: Remplacement de la boucle for
        // (J'ai aussi corrigé un bogue ici, l'ancien code envoyait "secondes")
        support.firePropertyChange(TimerChangeListener.MINUTE_PROP, 
                                   oldValue, newValue);
    }

    public void setHeures(int newHeures) {
        if (heures == newHeures)
            return;
        int oldValue = heures;
        heures = newHeures;
        heuresChanged(oldValue, heures);
    }

    private void heuresChanged(int oldValue, int newValue) {
        // MODIFIÉ: Remplacement de la boucle for
        // (J'ai aussi corrigé un bogue ici, l'ancien code envoyait "secondes")
        support.firePropertyChange(TimerChangeListener.HEURE_PROP, 
                                   oldValue, newValue);
    }

    // ... Les méthodes get...() ne changent pas ...
    
    @Override
    public int getDixiemeDeSeconde() { return dixiemeDeSeconde; }
    @Override
    public int getHeures() { return heures; }
    @Override
    public int getMinutes() { return minutes; }
    @Override
    public int getSecondes() { return secondes; }
}