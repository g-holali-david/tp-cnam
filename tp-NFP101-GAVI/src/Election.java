/**
 * Classe principale pour tester l'élection.
 * Simule une élection avec plusieurs candidats et affiche les résultats.
 */
public class Election {
    public static void main(String[] args) {

        try {
            // Création de la circonscription
            Circonscription picsouville = new Circonscription("Picsouville", 75);

            Candidat riri = new Candidat( "BALKONI", "Riri",null);

            Candidat fifi = new Candidat("LEMEC", "Fifi", null);
            Candidat loulou = new Candidat("TENERIS", "Loulou",null);

            // Ajout des candidats
            picsouville.ajouterCandidat(riri);
            picsouville.ajouterCandidat(fifi);
            picsouville.ajouterCandidat(loulou);

            // Ajout des voix
            picsouville.ajouterVoix(riri.getNomComplet(), 12);
            picsouville.ajouterVoix(fifi.getNomComplet(), 5);
            picsouville.ajouterVoix(loulou.getNomComplet(), 37);

            // Clore l'élection
            picsouville.cloreElection();

            System.out.println("\n*********************************************");
            // Résultats
            System.out.println(picsouville);
            System.out.println("*********************************************\n");

            System.out.println("Taux de participation : " + picsouville.tauxParticipation() + "%");
            System.out.println("Vainqueur(s) : " + picsouville.trouverVainqueur());

            System.out.println("\n************* Historique - action andidats ************");
            // Afficher l'historique des actions pour chaque candidat
            System.out.println("Historique des actions de Riri :");
            riri.getHistoriqueActions().forEach(System.out::println);

            System.out.println("\nHistorique des actions de Fifi :");
            fifi.getHistoriqueActions().forEach(System.out::println);

            System.out.println("\nHistorique des actions de Loulou :");
            loulou.getHistoriqueActions().forEach(System.out::println);

            System.out.println("\n************ Historique - action Circonscription *************");
            picsouville.getHistoriqueActions().forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}
