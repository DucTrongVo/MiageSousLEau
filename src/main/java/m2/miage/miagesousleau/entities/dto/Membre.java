package m2.miage.miagesousleau.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membre implements Serializable {

    private static final long serialVersionUID = 823908740381102968L;

    private Integer id;

    private String nom;

    private String prenom;

    private String mail;

    private String mdp;

    private String adresse;

    /**
     * date validité de la derniere certification
     */
    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private String dateCertif;

    /**
     * niveau de classe
     */
    private Integer niveau;

    /**
     * numé de licence
     */
    private String numLicence;

    /**
     * Type d'utilisateur
     */
    private String type;

    /**
     * Etat du paiement
     */
    private String etat;
}
