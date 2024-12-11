import java.util.List;

/**
 * Interface pour gérer l'historique des actions.
 */
public interface Historique {
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
