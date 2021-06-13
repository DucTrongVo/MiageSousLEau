package m2.miage.miagesousleau.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatistiqueCours implements Serializable
{

    private static final long serialVersionUID = 28573955007399028L;
    private int nombreCours;
}
