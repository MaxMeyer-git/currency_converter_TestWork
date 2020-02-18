package currencyconverter.core.service;

import currencyconverter.core.Currency.ValCurs;
import currencyconverter.core.entity.ConversionRequest;
import currencyconverter.core.entity.Currency;
import currencyconverter.core.entity.ResultDTO;
import currencyconverter.core.entity.VALCODEENUM;
import currencyconverter.core.repository.CurrencyRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Huina {

    @Value("${priority.default.request.url.withdate}")
    String urlstring;

    @Autowired
    private CurrencyRepository currencyRepository;


    public ResultDTO calc (ConversionRequest reqest) {
        Cur currency = getCurrValue(reqest);

        Double res = (currency.getValFrom() / currency.getNominalFrom() * reqest.getAmount())
                / (currency.getValTo() / currency.getNominalTo());

        return new ResultDTO(res, currency.getDate());

    }

    @Setter
    @Getter
    @NoArgsConstructor
    private static class Cur {
        Double valFrom;
        int nominalFrom;

        Double valTo;
        int nominalTo;

        LocalDate date;


    }

    private Cur getCurrValue(ConversionRequest reqest){

        LocalDate date = parseDate(reqest.getDate());
        Cur cur = new Cur();

        if (reqest.getCurrencyFrom() == VALCODEENUM.RUR){

            cur.setValFrom(1.);
            cur.setNominalFrom(1);

            var carrancy = check (date, reqest.getCurrencyTo().getNumcode());
            cur.setValTo(carrancy.getValue());
            cur.setNominalTo(reqest.getCurrencyTo().getNominal());
            cur.setDate(carrancy.getDate());
        }

        if (reqest.getCurrencyTo() == VALCODEENUM.RUR){
            var carrancy = check (date, reqest.getCurrencyTo().getNumcode());
            cur.setValFrom(carrancy.getValue());
            cur.setNominalFrom(reqest.getCurrencyTo().getNominal());
            cur.setDate(carrancy.getDate());

            cur.setValTo(1.);
            cur.setNominalTo(1);
        }

        if (reqest.getCurrencyFrom() != VALCODEENUM.RUR && reqest.getCurrencyTo() != VALCODEENUM.RUR){
            var carrancy = check (date, reqest.getCurrencyFrom().getNumcode());
            cur.setValFrom(carrancy.getValue());
            cur.setNominalFrom(reqest.getCurrencyFrom().getNominal());
            cur.setDate(carrancy.getDate());

            var currency2 = check (carrancy.getDate(), reqest.getCurrencyTo().getNumcode());
            cur.setValTo(currency2.getValue());
            cur.setNominalTo(reqest.getCurrencyTo().getNominal());
        }
        return cur;
    }

    private Currency  check (LocalDate  date , int numcode){
        Optional<Currency> optionalCurrency = currencyRepository.findByNumcodeAndDate(numcode, date);
        if (optionalCurrency.isPresent()) {
            return optionalCurrency.get();

        } else {
            LocalDate dateOfPulledCurses = vseSrazu(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            Optional<Currency> x = currencyRepository.findByNumcodeAndDate(numcode, dateOfPulledCurses);
            return x.orElseThrow(NoSuchElementException::new);
        }
    }

    public LocalDate vseSrazu(String date) {
        var x = pullData(date);
        return saveCurr(x);
    }

    @SneakyThrows
    private ValCurs pullData(String date)// throws JAXBException, MalformedURLException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        URL url2 = new URL(urlstring + date);

        return (ValCurs) unmarshaller.unmarshal(url2);
    }


    // TODO custom exception
    private LocalDate saveCurr(ValCurs valCurs) {
        LocalDate localDate = parseDate(valCurs.getDate());
        if (chekIsDatePresent(localDate)) {
            return localDate;
        }

        var currencyList = valCurs.getValuteDTOlist().stream()
//                .map(valuteDTO -> new Currency(valuteDTO, localDate))
                .map(valuteDTO -> new Currency(valuteDTO.getNumcode(), valuteDTO.getValue(), localDate))
                .collect(Collectors.toList());

        currencyRepository.saveAll(currencyList);
        return localDate;
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private boolean chekIsDatePresent(LocalDate date) {
        Long count = currencyRepository.countByDate(date);
        return count > 0;
    }


}
