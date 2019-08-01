package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import controler.BnProperties;
import controler.ControlerPeriodo;
import io.swagger.model.Periodo;
import io.swagger.modelExternos.PeriodoPrevired;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-07-31T00:07:04.234Z")

@Controller
public class ApiApiController implements ApiApi {

    private static final Logger log = LoggerFactory.getLogger(ApiApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    static BnProperties prop = BnProperties.getInstancia();

    @org.springframework.beans.factory.annotation.Autowired
    public ApiApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Periodo> periodos() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                //String fooResourceUrl = "http://localhost:9090/periodos/api";
                String fooResourceUrl = prop.getPropiedad("URL_ORIGEN_DATOS");
                PeriodoPrevired periodo = restTemplate.getForObject(fooResourceUrl, PeriodoPrevired.class);

                ControlerPeriodo control = new ControlerPeriodo();
                Periodo periodo2 = control.periodosFaltantes(periodo);
                return new ResponseEntity<Periodo>(periodo2, HttpStatus.OK);
            } catch (Exception e) {
                System.out.println("ERROR : " + e.toString());
                return new ResponseEntity<Periodo>(HttpStatus.CONFLICT);
            }
        }

        return new ResponseEntity<Periodo>(HttpStatus.NOT_IMPLEMENTED);
    }

}
