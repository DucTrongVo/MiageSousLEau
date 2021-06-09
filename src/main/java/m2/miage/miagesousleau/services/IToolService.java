package m2.miage.miagesousleau.services;

import m2.miage.miagesousleau.entities.dto.Piscine;
import m2.miage.miagesousleau.exception.GeneralErreurException;

import java.util.List;

public interface IToolService {
    Piscine convertJsonToPiscine(String json) throws GeneralErreurException;
    List<Piscine> convertJsonToPiscines(String json) throws GeneralErreurException;
}
