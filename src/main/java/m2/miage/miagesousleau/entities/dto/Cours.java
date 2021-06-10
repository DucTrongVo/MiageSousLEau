package m2.miage.miagesousleau.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cours implements Serializable {
    private static final long serialVersionUID = 7045588171237434666L;
    private Integer id;

    private String nom;

    private int niveauCible;

    private String dateDebut;

    private int idEnseignant;

    private String idLieu;

    private int duree;

    private Integer placeDisponible;

    private List<Integer> idEtudiants;
}
