package m2.miage.miagesousleau.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursWithPiscine extends Cours{
    private static final long serialVersionUID = 8779578719003387064L;
    private Piscine piscine;

    private List<Membre> listEtudiants;

    public CoursWithPiscine(Cours cours){
        super(cours.getId(), cours.getNom(), cours.getNiveauCible(), cours.getDateDebut(), cours.getIdEnseignant(), cours.getIdLieu(), cours.getDuree(), cours.getPlaceDisponible(), cours.getIdEtudiants());
        listEtudiants = new ArrayList<>();
    }
}
