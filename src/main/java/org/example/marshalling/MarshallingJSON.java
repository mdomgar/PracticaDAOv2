package org.example.marshalling;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.entidades.Produccion;
import org.example.entidades.campañaAceituna;

import java.io.File;
import java.io.IOException;

public class MarshallingJSON {
    public static void marshall(campañaAceituna campañaAceituna, String jsonPath){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.writeValue(new File(jsonPath),campañaAceituna);
            System.out.println("Objeto campañaAceituna serializado a JSON");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static campañaAceituna unmarshall(String jsonPath) {
        campañaAceituna campañaAceituna = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            campañaAceituna = objectMapper.readValue(new File(jsonPath), org.example.entidades.campañaAceituna.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return campañaAceituna;
    }
}
