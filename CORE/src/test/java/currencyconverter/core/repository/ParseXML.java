package currencyconverter.core.repository;

import currencyconverter.core.Currency.ValCurs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ParseXML {

    @Value("${priority.default.request.url.nodate}")
    String urlstring;


    @Test
    public void fromXMLtoObj() throws JAXBException, FileNotFoundException, MalformedURLException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

//        InputStream inputStream = new FileInputStream("R:\\Java\\for resume\\currency_converter\\CORE\\src\\test\\java\\currencyconverter\\core\\repository\\IncomeObj.xml");

//        String url = "http://www.cbr.ru/scripts/XML daily.asp?date_req=01/02/2020";
//        URL url = new URL ("http://www.cbr.ru/scripts/XML_daily.asp");
//        URL url2 = new URL ("http://www.cbr.ru/scripts/XML_daily.asp?date_req=05/02/2019");

//        ValCurs valCurs = (ValCurs) unmarshaller.unmarshal(inputStream);
//        ValuteDTO[] valute = (ValuteDTO[]) unmarshaller.unmarshal(inputStream);


        URL url2 = new URL(urlstring);

        ValCurs valCurs = (ValCurs) unmarshaller.unmarshal(url2);

        System.out.println(valCurs.getName());
        System.out.println(valCurs.getDate());
//        valCurs.getValuteDTOlist().forEach(valuteDTO -> System.out.println(valuteDTO.toString()));
//
        valCurs.getValuteDTOlist().forEach(valuteDTO -> System.out.println(
                valuteDTO.getCharcode() + "(\"" +
                        valuteDTO.getId() + "\", " +
                        valuteDTO.getNumcode() + ", " +
                        valuteDTO.getNominal() + ", \"" +
                        valuteDTO.getName() + "\"),"
        ));

    }

}
