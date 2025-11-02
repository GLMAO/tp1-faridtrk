package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.clients.CompteARebours;
import org.emp.gl.timer.service.TimerService; 
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;

// AJOUT: Pour les nombres aléatoires
import java.util.Random; 

public class App {

    public static void main(String[] args) {
        testDuTimeService();
    }

    private static void testDuTimeService() {
        
        TimerService timerService = new DummyTimeServiceImpl();

        // On garde les horloges
        Horloge horloge1 = new Horloge("Horloge 1", timerService);
        Horloge horloge2 = new Horloge("Horloge 2", timerService);

        // AJOUT (Point d-3): Instancier 10 compteurs aléatoires
        
        // 1. Créer un générateur de nombres aléatoires
        Random random = new Random();

        // 2. Créer une boucle de 10
        for (int i = 0; i < 10; i++) {
            
            // 3. Générer un nombre aléatoire entre 10 (inclus) et 21 (exclus)
            // random.nextInt(11) -> donne un nombre entre 0 et 10
            // + 10                 -> décale la plage pour être entre 10 et 20
            int valeurInitiale = random.nextInt(11) + 10; 
            
            // 4. Créer le nouveau compteur
            new CompteARebours(valeurInitiale, timerService);
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}