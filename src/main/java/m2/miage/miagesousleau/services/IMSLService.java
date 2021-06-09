package m2.miage.miagesousleau.services;

import m2.miage.miagesousleau.entities.dto.Membre;
import m2.miage.miagesousleau.entities.dto.Piscine;
import m2.miage.miagesousleau.exception.ForbiddenException;
import m2.miage.miagesousleau.exception.GeneralErreurException;
import m2.miage.miagesousleau.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IMSLService {
    ResponseEntity<Membre> getMembreByMail(String email) throws NotFoundException;
    List<Membre> getAllMembre(String emailRequester) throws ForbiddenException, NotFoundException;
    Piscine getPiscineByRecordID(String recordId) throws GeneralErreurException, NotFoundException;
    List<Piscine> getAllPiscines() throws GeneralErreurException;
}
