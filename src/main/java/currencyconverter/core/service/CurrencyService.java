package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.*;
import currencyconverter.core.repository.CurrencyRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final RequestLogUnitService requestLogUnitService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Value("${priority.default.request.url.withdate}")
    private String urlstring;

    public CurrencyService(CurrencyRepository currencyRepository, RequestLogUnitService requestLogUnitService) {
        this.currencyRepository = currencyRepository;
        this.requestLogUnitService = requestLogUnitService;
    }

    public ResultDTO calculate(ConversionRequest request) {
        Cur currency = getCurrValue(request);

        Double res = (currency.getValFrom() / currency.getNominalFrom() * request.getAmount())
                / (currency.getValTo() / currency.getNominalTo());

        res = roundD(res, 2);

        logSave(currency, request, res);

        return new ResultDTO(request.getCurrencyFrom().getName(), request.getCurrencyTo().getName(),
                res , currency.getDate().format(formatter));
    }

    private Cur getCurrValue(ConversionRequest request) {
        LocalDate date = parseDate(request.getDate());
        Cur cur = new Cur();

        if (request.getCurrencyFrom() == CurrencyENUM.RUR) {

            cur.setValFrom(1.);
            cur.setNominalFrom(1);

            var currency = check(date, request.getCurrencyTo().getNumcode());
            cur.setValTo(currency.getValue());
            cur.setNominalTo(request.getCurrencyTo().getNominal());
            cur.setDate(currency.getDate());
        }

        if (request.getCurrencyTo() == CurrencyENUM.RUR) {
            var currency = check(date, request.getCurrencyFrom().getNumcode());
            cur.setValFrom(currency.getValue());
            cur.setNominalFrom(request.getCurrencyTo().getNominal());
            cur.setDate(currency.getDate());

            cur.setValTo(1.);
            cur.setNominalTo(1);
        }

        if (request.getCurrencyFrom() != CurrencyENUM.RUR && request.getCurrencyTo() != CurrencyENUM.RUR) {
            var currency = check(date, request.getCurrencyFrom().getNumcode());
            cur.setValFrom(currency.getValue());
            cur.setNominalFrom(request.getCurrencyFrom().getNominal());
            cur.setDate(currency.getDate());

            var currency2 = check(currency.getDate(), request.getCurrencyTo().getNumcode());
            cur.setValTo(currency2.getValue());
            cur.setNominalTo(request.getCurrencyTo().getNominal());
        }
        return cur;
    }

    private Currency check(LocalDate date, int numcode) {
        Optional<Currency> optionalCurrency = currencyRepository.findByNumcodeAndDate(numcode, date);
        if (optionalCurrency.isPresent()) {
            return optionalCurrency.get();

        } else {
            LocalDate dateOfPulledCurses = pullAndSave(date.format(formatter));
            Optional<Currency> x = currencyRepository.findByNumcodeAndDate(numcode, dateOfPulledCurses);
            return x.orElseThrow(NoSuchElementException::new);
        }
    }

    @Transactional
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
                .collect(Collectors.toList());

        currencyRepository.saveAll(currencyList);
        return localDate;
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date, formatter);
    }

    public String parseFromDateToString(LocalDate date) {
        return date.format(formatter);
    }

    private boolean checkIsDatePresent(LocalDate date) {
        Long count = currencyRepository.countByDate(date);
        return count > 0;
    }

    private static double roundD(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    @Transactional
    protected void logSave (Cur currency, ConversionRequest request, Double result){

        var requestLogUnit = new RequestLogUnit(
                request.getCurrencyFrom().getNumcode(),
                currency.getValFrom()/currency.getNominalFrom(),
                request.getCurrencyTo().getNumcode(),
                currency.getValTo()/currency.getNominalTo(),
                request.getAmount(),
                result,
                currency.getDate());
        requestLogUnitService.save(requestLogUnit);
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
}
