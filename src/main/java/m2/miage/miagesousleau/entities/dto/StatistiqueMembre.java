package m2.miage.miagesousleau.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatistiqueMembre implements Serializable
{

    private static final long serialVersionUID = -1322768605837138378L;
    private int nombreMembres;

    private int nombreEnseignants;

    private int totalCotisationPrevues;

    private int totalCotisationReglees;
}
