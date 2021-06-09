package m2.miage.miagesousleau.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Piscine implements Serializable {
    private static final long serialVersionUID = -6315647524096088992L;

    private String recordId;

    private String nom;

    private String  adresse;

    private List<String> telephones;

    private String longitude;

    private String latitude;

    private String accessibilite;

    private String nomComplet;

}
