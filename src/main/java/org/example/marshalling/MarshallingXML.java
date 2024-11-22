package org.example.marshalling;

import org.example.DAOimp.*;
import org.example.entidades.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class MarshallingXML {
    public static void marshall(campañaAceituna campañaAceituna, String xmlFilePath) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(campañaAceituna.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(campañaAceituna, new File(xmlFilePath));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static campañaAceituna unmarshall(String ruta) {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(campañaAceituna.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            campañaAceituna campañaAceituna = (org.example.entidades.campañaAceituna) unmarshaller.unmarshal(new File(ruta));

            System.out.println("Objeto campañaAceituna unmarshalleado");

            return campañaAceituna;

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
