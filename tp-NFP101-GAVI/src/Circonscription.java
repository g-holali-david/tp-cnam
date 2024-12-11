import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe représentant une circonscription électorale.
 * Implémente l'interface Historique pour gérer les actions.
 */
public class Circonscription implements Historique {
    private final String nom; // Nom de la circonscription
    private final int inscrits; // Nombre d'inscrits sur les listes électorales
    private final List<Candidat> candidats; // Liste des candidats dans la circonscription
    private final List<String> historiqueActions; // Historique des actions
    private boolean electionTerminee; // Indique si l'élection est terminée

    /**
     * Constructeur de la classe Circonscription.
     *
     * @param nom      Le nom de la circonscription.
     * @param inscrits Le nombre d'inscrits sur les listes électorales (doit être
     *                 positif).
     */
    public Circonscription(String nom, int inscrits) {
        if (nom == null || nom.isEmpty() || inscrits <= 0) {
            throw new IllegalArgumentException(
                    "Le nom de la circonscription est obligatoire et le nombre d'inscrits doit être positif.");
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
            throw new IllegalArgumentException(
                    "Un candidat portant ce nom complet existe déjà dans la circonscription.");
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
        return candidats.stream()
                .filter(c -> c.getVoix() == maxVoix)
                .collect(Collectors.toList()); // Utilisation de Collectors.toList()
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
