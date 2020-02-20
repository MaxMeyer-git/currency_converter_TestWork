package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.*;
import currencyconverter.core.repository.CurrencyRepository;
import currencyconverter.core.util.SomeDataConversionUtility;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrencyService {
    @Value("${priority.default.request.url.withdate}")
    private String URL_String;
    private static final int INCOMING_AMOUNT_OF_CURRENCY  = 35;
    private final CurrencyRepository currencyRepository;
    private final HolidayService holidayService;
    private final SomeDataConversionUtility dcu;


    public CurrencyService(CurrencyRepository currencyRepository, HolidayService holidayService, SomeDataConversionUtility dcu) {
        this.currencyRepository = currencyRepository;
        this.holidayService = holidayService;
        this.dcu = dcu;
    }

    public CurrInerTransport getCurrValue(ConversionRequest request) {
        LocalDate date = dcu.StringToDate(request.getDate());
        CurrInerTransport cur = new CurrInerTransport();

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

    @Transactional
    @SneakyThrows
    protected Currency check(LocalDate date, int numcode) {
        Optional<Currency> optionalCurrency = currencyRepository.findByNumcodeAndDate(numcode, date);
        if (optionalCurrency.isPresent()) {
            return optionalCurrency.get();
        }

        LocalDate lastWorkingDay = holidayService.isItHoliday(date);
        if (lastWorkingDay == null) {
            LocalDate dateOfPulledCurses = pullAndSave(date);
            holidayService.saveNewHoliday(date, dateOfPulledCurses);
            Optional<Currency> x = currencyRepository.findByNumcodeAndDate(numcode, dateOfPulledCurses);
            return x.orElseThrow(NoSuchElementException::new);
        } else {
            Optional<Currency> optionalCurrencyLWD = currencyRepository.findByNumcodeAndDate(numcode, lastWorkingDay);
            return optionalCurrencyLWD.orElseThrow(NoSuchElementException::new);
        }
    }

    @Transactional
    public LocalDate pullAndSave(LocalDate date) {
        var x = pullData(date);
        return saveCurrency(x);
    }

    @SneakyThrows
    private ValCurs pullData(LocalDate date) {
        JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        URL url = new URL(URL_String + dcu.DateToString(date));
        return (ValCurs) unmarshaller.unmarshal(url);
    }

    private LocalDate saveCurrency(ValCurs valCurs) {
        LocalDate dateOfPull = dcu.StringToDate(valCurs.getDate());
        int inDB = checkIsDatePresent(dateOfPull);

        if (inDB == 0) {
            currencyRepository.saveAll(convertToCurrency(valCurs, dateOfPull));
        }
        if (inDB == INCOMING_AMOUNT_OF_CURRENCY) {
            return dateOfPull;
        }
        if (inDB != 0 && inDB < INCOMING_AMOUNT_OF_CURRENCY) {
            var x = convertToCurrency(valCurs, dateOfPull);
            var y = currencyRepository.findByDate(dateOfPull)
                    .orElseThrow(() -> new NoSuchElementException("Something really wrong check: (CurrencyService - saveCurrency)!"));
            System.out.println("FUCK YOU MAN");
            var z = x.stream()
                    .filter(currencyX -> y.stream()
                            .allMatch(currencyY -> currencyY.equals(currencyX)))
                    .collect(Collectors.toList());
            currencyRepository.saveAll(z);
        }
        return dateOfPull;
    }

    private List<Currency> convertToCurrency(ValCurs valCurs, LocalDate dateOfPull) {
        return valCurs.getValuteDTOlist().stream()
                .map(valuteDTO -> new Currency(valuteDTO, dateOfPull))
                .collect(Collectors.toList());
    }

    private int checkIsDatePresent(LocalDate date) {
        return currencyRepository.countByDate(date);
    }
}
