package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;

import java.beans.PropertyChangeEvent;

import org.emp.gl.timer.service.TimerChangeListener; // AJOUT

// AJOUT: "implements TimerChangeListener"
public class Horloge implements TimerChangeListener {

    String name;
    TimerService timerService;

    // MODIFIÉ: Le constructeur reçoit le TimerService
    public Horloge(String name, TimerService timerService) {
        this.name = name;
        System.out.println("Horloge " + name + " initialized!");
        
        // AJOUT: Injection de dépendance
        this.timerService = timerService; 
        
        // AJOUT (Point c-2): S'inscrire en tant qu'observer
        // "this" représente cette instance de Horloge
        this.timerService.addTimeChangeListener(this); 
    }

    public void afficherHeure() {
        // On garde la vérification au cas où, mais normalement 
        // timerService ne sera plus null
        if (timerService != null) {
            System.out.println(
                name + " affiche : " +
                timerService.getHeures() + ":" +
                timerService.getMinutes() + ":" +
                timerService.getSecondes()
            );
        }
    }

    // AJOUT (Point c-2): Implémentation de la méthode de l'interface
   @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
        // On récupère le nom de la propriété depuis l'objet "evt"
        String prop = evt.getPropertyName();

        if (prop.equals(TimerChangeListener.SECONDE_PROP)) {
            afficherHeure();
        }
    }
}