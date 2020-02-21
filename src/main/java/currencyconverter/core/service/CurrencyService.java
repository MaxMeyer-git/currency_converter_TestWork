package currencyconverter.core.service;

import currencyconverter.core.entity.сurrency.*;
import currencyconverter.core.repository.CurrencyRepository;
import currencyconverter.core.util.DataConversionUtility;
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
    //  TODO cache
    private final CurrencyRepository currencyRepository;
    private final HolidayService holidayService;
    private final DataConversionUtility dcu;

    public CurrencyService(CurrencyRepository currencyRepository, HolidayService holidayService, DataConversionUtility dcu) {
        this.currencyRepository = currencyRepository;
        this.holidayService = holidayService;
        this.dcu = dcu;
    }

    @Transactional
    public CurrencyInnerDTO getCurrencyValues(ConversionRequest request) {
        LocalDate date = dcu.StringToDate(request.getDate());
        CurrencyInnerDTO result = new CurrencyInnerDTO();

        if (request.getCurrencyFrom() == CurrencyENUM.RUR) {
            result.setValFrom(1.);
            result.setNominalFrom(1);
            var currency = findCurrency(date, request.getCurrencyTo().getNumCode());
            result.setValTo(currency.getValue());
            result.setNominalTo(request.getCurrencyTo().getNominal());
            result.setDate(currency.getDate());
        }
        if (request.getCurrencyTo() == CurrencyENUM.RUR) {
            var currency = findCurrency(date, request.getCurrencyFrom().getNumCode());
            result.setValFrom(currency.getValue());
            result.setNominalFrom(request.getCurrencyTo().getNominal());
            result.setDate(currency.getDate());
            result.setValTo(1.);
            result.setNominalTo(1);
        }
        if (request.getCurrencyFrom() != CurrencyENUM.RUR && request.getCurrencyTo() != CurrencyENUM.RUR) {
            var currency = findCurrency(date, request.getCurrencyFrom().getNumCode());
            result.setValFrom(currency.getValue());
            result.setNominalFrom(request.getCurrencyFrom().getNominal());
            result.setDate(currency.getDate());
            var currency2 = findCurrency(currency.getDate(), request.getCurrencyTo().getNumCode());
            result.setValTo(currency2.getValue());
            result.setNominalTo(request.getCurrencyTo().getNominal());
        }
        return result;
    }


    @SneakyThrows
    private Currency findCurrency(LocalDate date, int numcode) {
        Optional<Currency> optionalCurrency = currencyRepository.findByNumcodeAndDate(numcode, date);
        if (optionalCurrency.isPresent()) {
            return optionalCurrency.get();
        }

        LocalDate lastWorkingDay = holidayService.isItHoliday(date);
        if (lastWorkingDay == null) {
            LocalDate dateOfPulled = pullAndSave(date);
            holidayService.saveNewHoliday(date, dateOfPulled);
            return currencyRepository.findByNumcodeAndDate(numcode, dateOfPulled).orElseThrow(NoSuchElementException::new);
        } else {
            var x = currencyRepository.findByNumcodeAndDate(numcode, lastWorkingDay);
            if (x.isPresent()) {
                return x.get();
            } else {
                LocalDate dateOfPulled = pullAndSave(date);
                return currencyRepository.findByNumcodeAndDate(numcode, dateOfPulled).orElseThrow(NoSuchElementException::new);
            }
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
        LocalDate dateOfPullResponse = dcu.StringToDate(valCurs.getDate());
        int inDB = currencyRepository.countByDate(dateOfPullResponse);
        int amountOfCurr = valCurs.getValuteDTOlist().size();

        if (inDB == 0) {
            currencyRepository.saveAll(convertToCurrency(valCurs, dateOfPullResponse));
        }
        if (inDB == amountOfCurr) {
            return dateOfPullResponse;
        }
        if (inDB > 0 && inDB < amountOfCurr) {
            var x = convertToCurrency(valCurs, dateOfPullResponse);
            var y = currencyRepository.findByDate(dateOfPullResponse)
                    .orElseThrow(NoSuchElementException::new);
            var z = x.stream()
                    .filter(currencyX -> !y.contains(currencyX))
                    .collect(Collectors.toList());
            currencyRepository.saveAll(z);
            return dateOfPullResponse;
        }
        throw new RuntimeException("Probably duplicates in Currency SQL table");
    }

    private List<Currency> convertToCurrency(ValCurs valCurs, LocalDate dateOfPull) {
        return valCurs.getValuteDTOlist().stream()
                .map(valuteDTO -> new Currency(valuteDTO, dateOfPull))
                .collect(Collectors.toList());
    }
}
