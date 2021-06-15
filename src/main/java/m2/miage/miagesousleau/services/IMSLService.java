package m2.miage.miagesousleau.services;

import m2.miage.miagesousleau.entities.dto.Apte;
import m2.miage.miagesousleau.entities.dto.Cours;
import m2.miage.miagesousleau.entities.dto.CoursWithPiscine;
import m2.miage.miagesousleau.entities.dto.Membre;
import m2.miage.miagesousleau.entities.dto.Operation;
import m2.miage.miagesousleau.entities.dto.Piscine;
import m2.miage.miagesousleau.entities.dto.Statistique;
import m2.miage.miagesousleau.exception.ForbiddenException;
import m2.miage.miagesousleau.exception.GeneralErreurException;
import m2.miage.miagesousleau.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IMSLService {
    // Gestion Membre
    ResponseEntity<Membre> getMembreByMail(String email) throws NotFoundException;
    List<Membre> getAllMembre(String emailRequester) throws ForbiddenException, NotFoundException;
    Membre creerMembre(Membre membre);
    Membre mettreAJourMembre(String emailRequester, String email, Membre membreAJour);
    Operation creerPaiement(String emailMembre, String iban, double montant);
    Apte isMembreApte(String email);
    List<Operation> getAllOperationByMembre(String emailSec, String emailMembre);
    String supprimerUnMembre(String emailRequester, String email);

    // Gestion Piscine
    Piscine getPiscineByRecordID(String recordId) throws GeneralErreurException, NotFoundException;
    List<Piscine> getAllPiscines() throws GeneralErreurException;

    // Gestion Operation
    List<Operation> getAllOperation(String emailSec);
    Operation validerPaiement(String emailSec, Integer idOperation);
    Operation refuserPaiement(String emailSec, Integer idOperation);

    // Gestion Cours
    CoursWithPiscine getCoursWithPiscineById(int idCours) throws GeneralErreurException, NotFoundException;
    List<CoursWithPiscine> getAllCours() throws GeneralErreurException, NotFoundException;
    Cours creerCours(Cours cours, String emailEnseignant) throws ForbiddenException, NotFoundException;
    Cours inscrit(int idCours, String emailEtudiant) throws ForbiddenException, NotFoundException;
    Cours desinscrit(int idCours, String emailEtudiant) throws ForbiddenException, NotFoundException;

    // stat
    Statistique getStat(String emailPresident);
}
