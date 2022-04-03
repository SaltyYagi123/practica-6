package com.icai.practicas;

import com.icai.practicas.controller.ProcessController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProcessControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void given_app_when_login_using_right_credentials_then_OK() {

        // Given to the link
        String address = "http://localhost:" + port + "/api/v1/process-step1";

        // Le tenemos que pasar al controlador => El multivalue map
        String fullNameRaw = "Yago Tobio";
        String dniRaw = "06679111A";
        String telefonoRaw = "+44 7887636994";

        ProcessController.DataRequest data1 = new ProcessController.DataRequest(fullNameRaw, dniRaw, telefonoRaw);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProcessController.DataRequest> request = new HttpEntity<>(data1, headers);

        // When
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);

        // getBody
        then(result.getBody()).isEqualTo("{\"result\":\"OK\"}");
        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void given_app_when_login_using_right_credentials_then_KO() throws Exception {
        // Pasamos el puerto
        String address = "http://localhost" + port + "api/v1/process-step1";

        ProcessController.DataRequest dataErrorDNI = new ProcessController.DataRequest("Yago Tobio", "987654321",
                "619321455");
        ProcessController.DataRequest dataErrorTelf = new ProcessController.DataRequest("Yago Tobio", "06679111A", "");

        // Request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProcessController.DataRequest> requestErrorDNI = new HttpEntity<>(dataErrorDNI, headers);
        HttpEntity<ProcessController.DataRequest> requestErrorTelf = new HttpEntity<>(dataErrorTelf, headers);

        // Response
        ResponseEntity<String> resultErrorDNI = this.restTemplate.postForEntity(address, requestErrorDNI, String.class);
        ResponseEntity<String> resultErrorTelf = this.restTemplate.postForEntity(address, requestErrorTelf,
                String.class);

        // Final Check
        then(resultErrorDNI.getBody()).isEqualTo("{\"result\":\"KO\"}");
        then(resultErrorTelf.getBody()).isEqualTo("{\"result\":\"KO\"}");

        then(resultErrorDNI.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resultErrorTelf.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void given_app_when_login_using_right_credentials_then_ok_legacy() {
        // Given to the link
        String address = "http://localhost:" + port + "/api/v1/process-step1-legacy";

        // Construimos el data1 con el MultiValueMap como nos pone en la entrada para el
        // legacy

        /*
         * public ResponseEntity<String> processDataLegacy(@RequestBody
         * MultiValueMap<String, String> data1) {
         */

        // Ejemplo de Test donde todos los datos deben de funcionar bien
        MultiValueMap<String, String> data_correct = new LinkedMultiValueMap<>();
        data_correct.add("fullName", "Yago Tobio");
        data_correct.add("dni", "06679111A");
        data_correct.add("telefono", "+44 7887636994");

        // Request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        /*
         * consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, <- Como nos lo dice
         * aqui
         * produces = MediaType.TEXT_HTML_VALUE)
         */
        HttpEntity<MultiValueMap<String, String>> request_correct = new HttpEntity<>(data_correct, headers);
        // Ya que el MultiValueMap esta ahi, a contrario que el anterior
        // When
        ResponseEntity<String> result_correct = this.restTemplate.postForEntity(address, request_correct, String.class);

        then(result_correct.getBody()).contains("Gracias por enviar los datos");
        then(result_correct.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void given_app_when_login_using_right_credentials_then_KO_legacy() {
        // Pasamos el puerto
        // Le pasamos el puerto
        String address = "http://localhost:" + port + "/api/v1/process-step1-legacy";

        // El nombre introducido es incorrecto
        MultiValueMap<String, String> datosErrorName = new LinkedMultiValueMap<>();
        datosErrorName.add("fullName", "Y@g0 T&bÍo");
        datosErrorName.add("dni", "0679111A");
        datosErrorName.add("telefono", "619321455");

        // El nombre vacío
        MultiValueMap<String, String> datosNullName = new LinkedMultiValueMap<>();
        datosNullName.add("fullName", "");
        datosNullName.add("dni", "0679111A");
        datosNullName.add("telefono", "619321455");
        // Avisarle a Blanca sobre su error aquí!!!!!!

        // El DNI introducido es incorrecto
        MultiValueMap<String, String> datosErrorDni = new LinkedMultiValueMap<>();
        datosErrorDni.add("fullName", "Yago Tobio");
        datosErrorDni.add("dni", "987654321");
        datosErrorDni.add("telefono", "619321455");

        // El número introducido es incorrecto
        MultiValueMap<String, String> datosErrorTelf = new LinkedMultiValueMap<>();
        datosErrorTelf.add("fullName", "Yago Tobio");
        datosErrorTelf.add("dni", "0679111A");
        datosErrorTelf.add("telefono", "WAZAAAAAAAAP");

        // Request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestErrorName = new HttpEntity<>(datosErrorName, headers);
        HttpEntity<MultiValueMap<String, String>> requestNullName = new HttpEntity<>(datosNullName, headers);
        HttpEntity<MultiValueMap<String, String>> requestErrorDNI = new HttpEntity<>(datosErrorDni, headers);
        HttpEntity<MultiValueMap<String, String>> requestErrorTelf = new HttpEntity<>(datosErrorTelf,headers);
        // Response
        ResponseEntity<String> resultErrorName = this.restTemplate.postForEntity(address, requestErrorName,
                String.class);
        ResponseEntity<String> resultNullName = this.restTemplate.postForEntity(address, requestNullName,
                String.class);
        ResponseEntity<String> resultErrorDNI = this.restTemplate.postForEntity(address, requestErrorDNI, String.class);
        ResponseEntity<String> resultErrorTelf = this.restTemplate.postForEntity(address, requestErrorTelf,
                String.class);

        then(resultErrorDNI.getBody()).contains("Hemos tenido un problema con su solicitud");
        then(resultErrorTelf.getBody()).contains("Hemos tenido un problema con su solicitud");

        then(resultErrorName.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resultNullName.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        then(resultErrorDNI.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(resultErrorTelf.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}

/*
 * @PostMapping(
 * path="/process-step1-legacy",
 * consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
 * produces = MediaType.TEXT_HTML_VALUE)
 * public ResponseEntity<String> processDataLegacy(@RequestBody
 * MultiValueMap<String, String> data1) {
 * 
 * 
 * String fullNameRaw = data1.get("fullName").get(0);
 * String dniRaw = data1.get("dni").get(0);
 * String telefonoRaw = data1.get("telefono").get(0);
 * 
 * var processStep1Request = new ProcessService.ProcessStep1Request(fullNameRaw,
 * dniRaw, telefonoRaw);
 * var result = processService.processStep1(processStep1Request);
 * 
 * if(result.status()) {
 * return ResponseEntity.ok().body(ResponseHTMLGenerator.message1);
 * }
 * 
 * return ResponseEntity.ok().body(ResponseHTMLGenerator.message2);
 * }
 */

/*
 * @PostMapping(
 * path="/process-step1",
 * consumes = MediaType.APPLICATION_JSON_VALUE,
 * produces = MediaType.APPLICATION_JSON_VALUE)
 * public ResponseEntity<DataResponse> processData(
 * 
 * @Valid @RequestBody DataRequest data1, BindingResult bindingResult) {
 * 
 * 
 * if (bindingResult.hasErrors()) {
 * return ResponseEntity.badRequest().body(new DataResponse("KO"));
 * }
 * 
 * String fullNameRaw = data1.fullName();
 * String dniRaw = data1.dni();
 * String telefonoRaw = data1.telefono();
 * 
 * var processStep1Request = new ProcessService.ProcessStep1Request(fullNameRaw,
 * dniRaw, telefonoRaw);
 * var result = processService.processStep1(processStep1Request);
 * 
 * if(result.status()) {
 * return ResponseEntity.ok().body(new DataResponse("OK"));
 * }
 * 
 * return ResponseEntity.ok().body(new DataResponse("KO"));
 * }
 */