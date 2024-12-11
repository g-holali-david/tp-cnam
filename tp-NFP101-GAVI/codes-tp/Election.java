import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Interface pour gérer l'historique des actions.
 */
interface Historique {
    /**
     * Ajoute une action à l'historique.
     *
     * @param action La description de l'action à ajouter.
     */
    void ajouterAction(String action);

    /**
     * Récupère toutes les actions de l'historique.
     *
     * @return Une liste immuable des actions.
     */
    List<String> getHistoriqueActions();
}



/**
 * Représente un candidat dans une élection.
 * Un candidat a un nom, un prénom, une affiliation politique (facultative),
 * un nombre de voix, et un historique des actions.
 */
class Candidat implements Historique {
    private final String nom;             // Nom du candidat
    private final String prenom;          // Prénom du candidat
    private final String affiliation;     // Affiliation politique (par défaut : "Indépendant")
    private Integer voix;                 // Nombre de voix obtenues
    private final List<String> historiqueActions; // Historique des actions

    /**
     * Constructeur de la classe Candidat.
     *
     * @param nom         Le nom du candidat.
     * @param prenom      Le prénom du candidat.
     * @param affiliation L'affiliation politique (peut être null ou vide pour "Indépendant").
     */
    public Candidat(String nom, String prenom, String affiliation) {
        if (nom == null || prenom == null || nom.isEmpty() || prenom.isEmpty()) {
            throw new IllegalArgumentException("Le nom et le prénom du candidat sont obligatoires.");
        }
        this.nom = nom;
        this.prenom = prenom;
        this.affiliation = (affiliation == null || affiliation.isEmpty()) ? "Indépendant" : affiliation;
        this.voix = null; // Pas encore de voix enregistrées
        this.historiqueActions = new ArrayList<>();
        ajouterAction("Création du candidat : " + getNomComplet() + " (Affiliation : " + this.affiliation + ")");
    }

    @Override
    public void ajouterAction(String action) {
        if (action == null || action.isEmpty()) {
            throw new IllegalArgumentException("L'action ne peut pas être vide.");
        }
        String horodatage = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        historiqueActions.add(horodatage + " - " + action);
    }

    @Override
    public List<String> getHistoriqueActions() {
        return new ArrayList<>(historiqueActions); // Retourne une copie pour protéger l'original
    }

    /**
     * Ajoute des voix au candidat.
     *
     * @param nbVoix Le nombre de voix à ajouter (doit être positif).
     * @throws IllegalArgumentException si le nombre de voix est négatif.
     */
    public void ajouterVoix(int nbVoix) {
        if (nbVoix < 0) {
            throw new IllegalArgumentException("Le nombre de voix ne peut pas être négatif.");
        }
        if (voix == null) {
            voix = 0; // Initialise les voix si elles ne sont pas encore enregistrées
        }
        voix += nbVoix;
        ajouterAction("Ajout de " + nbVoix + " voix. Total voix : " + voix);
    }

    /**
     * Récupère le nombre de voix du candidat.
     *
     * @return Le nombre de voix (0 si non enregistré).
     */
    public int getVoix() {
        return voix != null ? voix : 0;
    }

    /**
     * Récupère le nom complet du candidat.
     *
     * @return Le prénom suivi du nom.
     */
    public String getNomComplet() {
        return prenom + " " + nom;
    }

    /**
     * Retourne une représentation textuelle du candidat.
     *
     * @return Une chaîne décrivant le candidat avec son affiliation et ses voix.
     */
    @Override
    public String toString() {
        return getNomComplet() + " (" + affiliation + ") : " + (voix != null ? voix : "Non enregistré") + " voix";
    }
}


/**
 * Classe représentant une circonscription électorale.
 * Implémente l'interface Historique pour gérer les actions.
 */
class Circonscription implements Historique {
    private final String nom;                   // Nom de la circonscription
    private final int inscrits;                 // Nombre d'inscrits sur les listes électorales
    private final List<Candidat> candidats;     // Liste des candidats dans la circonscription
    private final List<String> historiqueActions; // Historique des actions
    private boolean electionTerminee;           // Indique si l'élection est terminée

    /**
     * Constructeur de la classe Circonscription.
     *
     * @param nom      Le nom de la circonscription.
     * @param inscrits Le nombre d'inscrits sur les listes électorales (doit être positif).
     */
    public Circonscription(String nom, int inscrits) {
        if (nom == null || nom.isEmpty() || inscrits <= 0) {
            throw new IllegalArgumentException("Le nom de la circonscription est obligatoire et le nombre d'inscrits doit être positif.");
        }
        this.nom = nom;
        this.inscrits = inscrits;
        this.candidats = new ArrayList<>();
        this.historiqueActions = new ArrayList<>();
        this.electionTerminee = false;

        ajouterAction("Création de la circonscription : " + this.nom);
    }

    @Override
    public void ajouterAction(String action) {
        if (action == null || action.isEmpty()) {
            throw new IllegalArgumentException("L'action ne peut pas être vide.");
        }
        String horodatage = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        historiqueActions.add(horodatage + " - " + action);
    }

    @Override
    public List<String> getHistoriqueActions() {
        return new ArrayList<>(historiqueActions); // Retourne une copie pour protéger l'original
    }

    public void ajouterCandidat(Candidat candidat) {
        if (electionTerminee) {
            throw new IllegalStateException("L'élection est terminée. Impossible d'ajouter des candidats.");
        }
        if (candidats.stream().anyMatch(c -> c.getNomComplet().equals(candidat.getNomComplet()))) {
            throw new IllegalArgumentException("Un candidat portant ce nom complet existe déjà dans la circonscription.");
        }
        candidats.add(candidat);
        ajouterAction("Ajout du candidat : " + candidat.getNomComplet());
    }

    public void ajouterVoix(String nomComplet, int voix) {
        if (electionTerminee) {
            throw new IllegalStateException("L'élection est terminée. Impossible d'ajouter des voix.");
        }
        if (voix <= 0) {
            throw new IllegalArgumentException("Le nombre de voix doit être positif.");
        }
        Candidat candidat = candidats.stream()
                .filter(c -> c.getNomComplet().equals(nomComplet))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Candidat " + nomComplet + " non trouvé."));
        candidat.ajouterVoix(voix);
        ajouterAction("Ajout de " + voix + " voix pour " + nomComplet);
    }

    public void cloreElection() {
        int totalVoix = candidats.stream().mapToInt(Candidat::getVoix).sum();
        if (totalVoix > inscrits) {
            throw new IllegalStateException("Le total des voix dépasse le nombre d'inscrits. Vérifiez les données.");
        }
        electionTerminee = true;
        ajouterAction("Élection clôturée.");
    }

    public double tauxParticipation() {
        int totalVoix = candidats.stream().mapToInt(Candidat::getVoix).sum();
        return (double) totalVoix / inscrits * 100;
    }

    public List<Candidat> trouverVainqueur() {
        int maxVoix = candidats.stream().mapToInt(Candidat::getVoix).max().orElse(0);
        return candidats.stream().filter(c -> c.getVoix() == maxVoix).toList();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Élection dans la circonscription " + nom + " :\n");
        for (Candidat candidat : candidats) {
            result.append("- ").append(candidat).append("\n");
        }
        return result.toString();
    }
}


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

