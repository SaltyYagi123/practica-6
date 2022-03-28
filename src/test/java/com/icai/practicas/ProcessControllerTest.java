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
    public void given_app_when_login_using_right_credentials_then_ok() {

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

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);

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
        MultiValueMap<String, String> data1 = new LinkedMultiValueMap<>();
        data1.add("fullNameRaw", "Yago Tobio");
        data1.add("dniRaw", "06679111A");
        data1.add("telefonoRaw", "+44 7887636994");

        // Ahora debemos de construir el request y algo mas
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        /*
         * consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, <- Como nos lo dice
         * aqui
         * produces = MediaType.TEXT_HTML_VALUE)
         */
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(data1, headers);

        // Ya que el MultiValueMap esta ahi, a contrario que el anterior
        // When
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);

        then(result.getStatusCode()).isEqualTo(HttpStatus.OK);

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