package currencyconverter.core.repository;

import currencyconverter.core.entity.сurrency.ValCurs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ParseXML {

    @Value("${priority.default.request.url.nodate}")
    String urlstring;

    @Test
    public void fromXMLtoObj() throws JAXBException, MalformedURLException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        URL url2 = new URL(urlstring);

        ValCurs valCurs = (ValCurs) unmarshaller.unmarshal(url2);

        System.out.println(valCurs.getName());
        System.out.println(valCurs.getDate());

//        valCurs.getValDtoList().forEach(valuteDTO -> System.out.println(valuteDTO.toString()));

//      this how i did CurrencyENUM
        valCurs.getValDtoList().forEach(valuteDTO -> System.out.println(
                valuteDTO.getCharcode() + "(\"" +
                        valuteDTO.getId() + "\", " +
                        valuteDTO.getNumcode() + ", " +
                        valuteDTO.getNominal() + ", \"" +
                        valuteDTO.getName() + "\"),"
        ));
    }
}
