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
public class CurrencyService {

    @Value("${priority.default.request.url.withdate}")
    String urlstring;

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }


    public ResultDTO calc (ConversionRequest request) {
        Cur currency = getCurrValue(request);

        Double res = (currency.getValFrom() / currency.getNominalFrom() * request.getAmount())
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

    private Cur getCurrValue(ConversionRequest request){

        LocalDate date = parseDate(request.getDate());
        Cur cur = new Cur();

        if (request.getCurrencyFrom() == VALCODEENUM.RUR){

            cur.setValFrom(1.);
            cur.setNominalFrom(1);

            var currency = check (date, request.getCurrencyTo().getNumcode());
            cur.setValTo(currency.getValue());
            cur.setNominalTo(request.getCurrencyTo().getNominal());
            cur.setDate(currency.getDate());
        }

        if (request.getCurrencyTo() == VALCODEENUM.RUR){
            var currency = check (date, request.getCurrencyTo().getNumcode());
            cur.setValFrom(currency.getValue());
            cur.setNominalFrom(request.getCurrencyTo().getNominal());
            cur.setDate(currency.getDate());

            cur.setValTo(1.);
            cur.setNominalTo(1);
        }

        if (request.getCurrencyFrom() != VALCODEENUM.RUR && request.getCurrencyTo() != VALCODEENUM.RUR){
            var currency = check (date, request.getCurrencyFrom().getNumcode());
            cur.setValFrom(currency.getValue());
            cur.setNominalFrom(request.getCurrencyFrom().getNominal());
            cur.setDate(currency.getDate());

            var currency2 = check (currency.getDate(), request.getCurrencyTo().getNumcode());
            cur.setValTo(currency2.getValue());
            cur.setNominalTo(request.getCurrencyTo().getNominal());
        }
        return cur;
    }

    private Currency  check (LocalDate  date , int numcode){
        Optional<Currency> optionalCurrency = currencyRepository.findByNumcodeAndDate(numcode, date);
        if (optionalCurrency.isPresent()) {
            return optionalCurrency.get();

        } else {
            LocalDate dateOfPulledCurses = pullAndSave(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            Optional<Currency> x = currencyRepository.findByNumcodeAndDate(numcode, dateOfPulledCurses);
            return x.orElseThrow(NoSuchElementException::new);
        }
    }

    public LocalDate pullAndSave(String date) {
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
        if (checkIsDatePresent(localDate)) {
            return localDate;
        }

        var currencyList = valCurs.getValuteDTOlist().stream()
                .map(valuteDTO -> new Currency(valuteDTO, localDate))
//                .map(valuteDTO -> new Currency(valuteDTO.getNumcode(), valuteDTO.getValue(), localDate))
                .collect(Collectors.toList());

        currencyRepository.saveAll(currencyList);
        return localDate;
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private boolean checkIsDatePresent(LocalDate date) {
        Long count = currencyRepository.countByDate(date);
        return count > 0;
    }


}
