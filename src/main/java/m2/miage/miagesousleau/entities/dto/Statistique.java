package m2.miage.miagesousleau.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistique implements Serializable
{

    private static final long serialVersionUID = -5959687664759327172L;

    private int nombreMembres;

    private int nombreEnseignants;

    private int nombreCours;

    private int totalCotisationPrevues;

    private int totalCotisationReglees;
}
