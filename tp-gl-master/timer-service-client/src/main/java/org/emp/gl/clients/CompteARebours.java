package org.emp.gl.clients;

import java.beans.PropertyChangeEvent;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

// La classe est "similaire à la classe Horloge" 
public class CompteARebours implements TimerChangeListener {

    // Pour stocker la valeur actuelle
    private int compteur; 
    
    // On a besoin du timerService pour s'abonner
    private TimerService timerService;

    // Le constructeur prend un entier en paramètre 
    public CompteARebours(int valeurInitiale, TimerService timerService) {
        this.compteur = valeurInitiale;
        this.timerService = timerService;
        
        System.out.println("Nouveau Compte à rebours initialisé à " + this.compteur);

        // Abonnement au service
        this.timerService.addTimeChangeListener(this);
    }

    // C'est la méthode qui sera appelée à chaque "tick"
   @Override
    public void propertyChange(PropertyChangeEvent evt) {

        // On récupère le nom de la propriété depuis l'objet "evt"
        String prop = evt.getPropertyName();

        if (prop.equals(TimerChangeListener.SECONDE_PROP)) {
            if (this.compteur > 0) {
                this.compteur--;
                System.out.println("Compte à rebours : " + this.compteur);

                if (this.compteur == 0) {
                    System.out.println("Compte à rebours terminé. Désinscription.");
                    this.timerService.removeTimeChangeListener(this);
                }
            }
        }
    }
}