package base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class BaseSetting, qui premet la connexion aux bases de données.
 *
 * @author Maxime Blaise
 */
public class BaseSetting {

    /**
     * BaseInformation, qui contient les informations lues dans le fichier.
     */
    private BaseInformation bi;

    /**
     * Nom du driver, mais qui se trouve normalement dans le fichier. Pour
     * oracle, "oracle.jdbc.driver.OracleDriver"
     */
    private final String driver = "com.mysql.jdbc.Driver";

    /**
     * Objet connection.
     */
    private Connection connection;

    /**
     * Statement <=> Requête SQL.
     */
    private Statement statement;

    /**
     * Constructeur vide
     */
    public BaseSetting() {
        // Lecture des informations à partir du fichier.
        lireInformation();
    }

    /**
     * Méthode qui se charge de lire les informations.
     */
    public final void lireInformation() {
        // Lecture
        bi = BaseInformation.lectureInformations();

        if (bi == null) {
            // La lecture a échoué
            System.err.println("Les informations de connexion sont mal renseignées.");
        }
    }

    /**
     * Constructeur avec les informations de la base de données.
     *
     * @param bi l'objet
     */
    public BaseSetting(BaseInformation bi) {
        this.bi = bi;
    }

    public void update(String query) {
        try {
            this.setStatement(getConnection().createStatement());
            getStatement().executeUpdate(query);
        } catch (SQLException ex) {
            System.err.println("Erreur pour la requête : " + query);
        }
    }
    
    public void delete(String query) {
        try {
            this.setStatement(getConnection().createStatement());
            getStatement().executeUpdate(query);
        } catch (SQLException ex) {
            System.err.println("Erreur pour la requête : " + query);
        }
    }

    public void insert(String query) {
        try {
            this.setStatement(getConnection().createStatement());
            getStatement().executeUpdate(query);
        } catch (SQLException ex) {
            System.err.println("Erreur pour la requête : " + query);
        }
    }

    public ResultSet select(String query) {
        try {
            this.setStatement(getConnection().createStatement());
            return this.getStatement().executeQuery(query);
        } catch (SQLException ex) {
            System.err.println("Erreur pour la requête : " + query);
        }
        return null;
    }

    /**
     * Met à jour les informations du fichier.
     *
     */
    public void setInformations() {
        this.bi = BaseInformation.lectureInformations();
    }

    /**
     * Méthode qui test la connexion à la base, en initialisant l'objet
     * Connection
     *
     * @return true/false
     */
    public final boolean testerConnexion() {
        /* if (bi.getDbname().equals("")) {
         url = "jdbc:" + bi.getDriver() + ":" + bi.getUrl();
         } else {
         url = "jdbc:" + bi.getDriver() + ":" + bi.getUrl() + "/" + bi.getDbname();
         }*/

        try {
            //On lit les informations du fichier, au cas où elles auraient changées.
            setInformations();

            Class.forName(driver);

            if (bi == null) {
                System.err.println("bi null");
                return false;
            }

            //Création du path
            String dbPath = String.format(
                    "jdbc:%s:%s/%s?user=%s&password=%s&characterEncoding=utf-8&"
                    + "useUnicode=true", bi.getDriver(), bi.getUrl(), bi.getDbname(), bi.getLogin(), bi.getPassword());

            //Initiatialisation de la connection
            connection = java.sql.DriverManager.getConnection(dbPath);

            // Préparation des tables pour l'UTF8
            prepareTables();

            //Si on arrive ici, c'est que tout s'est bien passé.
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            // Affichage d'un message d'erreur.
            System.err.println("La connexion à la base de données a échoué.");
        }

        //Erreur quelque part !
        return false;
    }

    /**
     * Préparation des tables, encodage en UTF-8
     *
     * @throws SQLException
     */
    private void prepareTables() throws SQLException {
        java.sql.Statement stat = connection.createStatement();

        String myquery = "set names utf8";
        stat.execute(myquery);

        myquery = "set character set utf8";
        stat.execute(myquery);
    }

    /**
     * Récupère l'objet BaseInformation, qui contient toutes les informations
     * (juste les valeurs)
     *
     * @return les informations utiles
     */
    public BaseInformation getBaseInformations() {
        return this.bi;
    }

    /**
     * Met à jour les informations du fichier.
     *
     */
    public void lireInformations() {
        bi = BaseInformation.lectureInformations();
    }

    /**
     * Permet de récupérer la connexion à la base.
     *
     * @return l'objet connexion
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Permet de réinitialiser la connection.
     *
     * @param connection l'objet connexion
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Récupère le statement courant.
     *
     * @return l'objet Statement
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * Initialiser la requête.
     *
     * @param statement l'objet Statement
     */
    public void setStatement(Statement statement) {
        this.statement = statement;
    }

}
