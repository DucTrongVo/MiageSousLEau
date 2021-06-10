package m2.miage.miagesousleau.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apte implements Serializable {

    private static final long serialVersionUID = 2887822288868059355L;
    private boolean apte;

    private String description;
}