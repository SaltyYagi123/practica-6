package com.icai.practicas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT);
public class ProcessControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void given_app_when_process_using_right_credentials_then_ok_legacy(){
        //Accedemos al link given en el process controller 
        //Observa en el process controller que hay 2 direcciones distintas en las que se puede hacer un POST
        //Given 
        String address_1 = "http://localhost:" + port + "/api/v1/process-step1-legacy";
       
    
    }


    @Test
    public void given_app_when_process_using_right_credentials_then_ok(){
        //Accedemos al link given en el process controller 
        //Observa en el process controller que hay 2 direcciones distintas en las que se puede hacer un POST
        //Given 
        String address_1 = "http://localhost:" + port + "/api/v1/process-step1";
       
        ResponseEntity<ProcessStep1Request> request = new ResponseEntity<>()
    
    }
    
}
