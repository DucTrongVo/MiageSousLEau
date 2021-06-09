package m2.miage.miagesousleau.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import m2.miage.miagesousleau.entities.dto.Piscine;
import m2.miage.miagesousleau.exception.GeneralErreurException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ToolServiceImpl implements IToolService{
    private static final Logger logger = LoggerFactory.getLogger(ToolServiceImpl.class);

    @Override
    public Piscine convertJsonToPiscine(String json) throws GeneralErreurException {
        ObjectMapper mapper = new ObjectMapper();
        logger.info("json receive "+json);
        try {
            JsonNode jsonNode = mapper.readValue(json, JsonNode.class).get("records").get(0);
            logger.info("json receive "+jsonNode.asText());
//            String[] telephones = jsonNode.get("records").get(0).get("fields").get("telephone").asText().split("/");
//            List<String> listTel = new ArrayList<>(Arrays.asList(telephones));
            return buildPiscineFromJson(jsonNode);
//            piscine.setNom(jsonNode.get("records").get(0).get("fields").get("index").asText());
//            piscine.setRecordId(jsonNode.get("records").get(0).get("recordid").asText());
//            piscine.setAdresse(jsonNode.get("records").get(0).get("fields").get("adresse").asText());
//            piscine.setTelephones(listTel);
//            piscine.setLatitude(jsonNode.get("records").get(0).get("fields").get("geo_shape").get("coordinates").get(1).asText());
//            piscine.setLongitude(jsonNode.get("records").get(0).get("fields").get("geo_shape").get("coordinates").get(0).asText());
//            piscine.setAccessibilite(jsonNode.get("records").get(0).get("fields").get("accessibilite").asText());
//            piscine.setNomComplet(jsonNode.get("records").get(0).get("fields").get("nom_complet").asText());

            //return piscine;
        } catch (Exception e){
            logger.error("Une erreur est survenue : ",e);
            throw new GeneralErreurException();
        }
    }

    @Override
    public List<Piscine> convertJsonToPiscines(String json) throws GeneralErreurException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readValue(json, JsonNode.class).get("records");
            List<Piscine> piscines = new ArrayList<>();
            for (JsonNode node : jsonNode) {
                piscines.add(buildPiscineFromJson(node));
            }
            return piscines;
        } catch (Exception e){
            logger.error("Une erreur est survenue : ",e);
            throw new GeneralErreurException();
        }
    }

    private Piscine buildPiscineFromJson(JsonNode jsonNode) {
        Piscine piscine = new Piscine();
        String[] telephones = jsonNode.get("fields").get("telephone").asText().split("/");
        List<String> listTel = new ArrayList<>(Arrays.asList(telephones));
        piscine.setNom(setItem(jsonNode.get("fields").get("index")));
        piscine.setRecordId(setItem(jsonNode.get("recordid")));
        piscine.setAdresse(setItem(jsonNode.get("fields").get("adresse")));
        piscine.setTelephones(listTel);
        piscine.setLatitude(setItem(jsonNode.get("fields").get("geo_shape").get("coordinates").get(1)));
        piscine.setLongitude(setItem(jsonNode.get("fields").get("geo_shape").get("coordinates").get(0)));
        piscine.setAccessibilite(setItem(jsonNode.get("fields").get("accessibilite")));
        piscine.setNomComplet(setItem(jsonNode.get("fields").get("nom_complet")));

        return piscine;
    }
    private String setItem(JsonNode item){
        if (item == null){
            return "";
        }
        return item.asText();
    }
}
