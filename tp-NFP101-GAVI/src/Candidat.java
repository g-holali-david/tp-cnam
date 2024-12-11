import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un candidat dans une élection.
 * Un candidat a un nom, un prénom, une affiliation politique (facultative),
 * un nombre de voix, et un historique des actions.
 */
public class Candidat implements Historique {
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
