package m2.miage.miagesousleau.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class ErrorHandler implements ResponseErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return new DefaultResponseErrorHandler().hasError(clientHttpResponse);
    }

    @SneakyThrows
    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) {
        if (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            logger.error("Une erreur de serveur est survenue : " + clientHttpResponse.getBody());
            throw new GeneralErreurException();
        }else if (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readValue(clientHttpResponse.getBody(), JsonNode.class);
            String message = jsonNode.asText();
            if (jsonNode.get("messageErreur") != null){
                message = jsonNode.get("messageErreur").asText();
            }
            logger.error(jsonNode.asText());
            if (clientHttpResponse.getRawStatusCode() == 404) {
                logger.error(message);
                throw new NotFoundException(message);
            }
            if (clientHttpResponse.getRawStatusCode() == 403) {
                logger.error(message);
                throw new ForbiddenException(message);
            }
            else {
                logger.error("Une erreur est survenue : " + clientHttpResponse.getBody());
                throw new GeneralErreurException();
            }
        }
    }
}
