package com.icai.practicas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.icai.practicas.model.DNI;

import org.junit.jupiter.api.Test;

public class DNITest {

    @Test
    public void testDNI(){
        DNI dni_test_true = new DNI("06679111A");
        DNI dni_test_false = new DNI("1234567891");

        boolean valid_DNI_false= dni_test_false.validar();
        
        boolean valid_DNI_true = dni_test_true.validar();
        assertEquals(true,valid_DNI_true);
        assertEquals(false, valid_DNI_false);

    }


}
