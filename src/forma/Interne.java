package forma;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maxime BLAISE
 */
public class Interne {

    /**
     * Nom de la table dans la base de données.
     */
    public static String nomTable = "Interne";

    /**
     * Matricule de l'interne.
     */
    private int matricule;

    /**
     * Nom de l'interne.
     */
    private String nom;

    /**
     * Prénom de l'interne.
     */
    private String prenom;

    /**
     * Code du service dans lequel se trouve le client.
     */
    private int code_service;

    /**
     * Création d'un interne.
     *
     * @param matricule int
     * @param nom String
     * @param prenom String
     * @param code_service int
     */
    public Interne(int matricule, String nom, String prenom, int code_service) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.code_service = code_service;
    }

    /**
     * Insertion de l'interne dans la base.
     */
    public void insert() {
        String sql = "INSERT INTO " + Interne.nomTable + "(nom, prenom, code_service) VALUES(" + this.nom + ", " + this.prenom + ", " + this.code_service + ")";
        Main.bs.insert(sql);
    }

    /**
     * Modification de l'interne dans la base.
     */
    public void update() {
        String sql = "UPDATE " + Interne.nomTable + " SET nom=" + this.nom + ", prenom=" + this.prenom + ", code_service=" + this.code_service + " WHERE matricule=" + this.matricule;
        Main.bs.update(sql);
    }

    /**
     * Suppression de l'interne dans la base.
     */
    public void delete() {
        String sql = "DELETE FROM " + Interne.nomTable + " WHERE matricule=" + this.matricule;
        Main.bs.delete(sql);
    }

    /**
     * Retourne la liste de tous les internes.
     *
     * @return Liste des Internes.
     */
    public static ArrayList<Interne> findAll() {
        ArrayList<Interne> listeInternes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + Interne.nomTable;
            ResultSet res = Main.bs.select(sql);
            while (res.next()) {
                int matricule = res.getInt("matricule");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                int code_service = res.getInt("code_service");
                listeInternes.add(new Interne(matricule, nom, prenom, code_service));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Interne.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listeInternes;
    }

    /**
     * Retourne un interne selon son identifiant.
     *
     * @param id Matricule
     * @return Un Interne
     */
    public static Interne findById(int id) {
        String sql = "SELECT * FROM " + Interne.nomTable + " WHERE matricule=" + id;
        ResultSet res = Main.bs.select(sql);
        try {
            if(res.next()) {
                int matricule = res.getInt("matricule");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                int code_service = res.getInt("code_service");
                return new Interne(matricule, nom, prenom, code_service);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Interne.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getCode_service() {
        return code_service;
    }

    public void setCode_service(int code_service) {
        this.code_service = code_service;
    }

}
