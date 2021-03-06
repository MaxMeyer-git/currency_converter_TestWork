package currencyconverter.core.entity.сurrency;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.NoSuchElementException;


@Getter
@AllArgsConstructor
public enum CurrencyENUM {

    AUD("R01010", 36, 1, "Австралийский доллар"),
    AZN("R01020A", 944, 1, "Азербайджанский манат"),
    GBP("R01035", 826, 1, "Фунт стерлингов Соединенного королевства"),
    AMD("R01060", 51, 100, "Армянских драмов"),
    BYN("R01090B", 933, 1, "Белорусский рубль"),
    BGN("R01100", 975, 1, "Болгарский лев"),
    BRL("R01115", 986, 1, "Бразильский реал"),
    HUF("R01135", 348, 100, "Венгерских форинтов"),
    HKD("R01200", 344, 10, "Гонконгских долларов"),
    DKK("R01215", 208, 10, "Датских крон"),
    USD("R01235", 840, 1, "Доллар США"),
    EUR("R01239", 978, 1, "Евро"),
    INR("R01270", 356, 100, "Индийских рупий"),
    KZT("R01335", 398, 100, "Казахстанских тенге"),
    CAD("R01350", 124, 1, "Канадский доллар"),
    KGS("R01370", 417, 100, "Киргизских сомов"),
    CNY("R01375", 156, 10, "Китайских юаней"),
    MDL("R01500", 498, 10, "Молдавских леев"),
    NOK("R01535", 578, 10, "Норвежских крон"),
    PLN("R01565", 985, 1, "Польский злотый"),
    RON("R01585F", 946, 1, "Румынский лей"),
    XDR("R01589", 960, 1, "СДР (специальные права заимствования)"),
    SGD("R01625", 702, 1, "Сингапурский доллар"),
    TJS("R01670", 972, 10, "Таджикских сомони"),
    TRY("R01700J", 949, 1, "Турецкая лира"),
    TMT("R01710A", 934, 1, "Новый туркменский манат"),
    UZS("R01717", 860, 10000, "Узбекских сумов"),
    UAH("R01720", 980, 10, "Украинских гривен"),
    CZK("R01760", 203, 10, "Чешских крон"),
    SEK("R01770", 752, 10, "Шведских крон"),
    CHF("R01775", 756, 1, "Швейцарский франк"),
    ZAR("R01810", 710, 10, "Южноафриканских рэндов"),
    KRW("R01815", 410, 1000, "Вон Республики Корея"),
    JPY("R01820", 392, 100, "Японских иен"),
    RUR("R12345", 810, 1, "Российский рубль");

    private final String id;
    private final int numCode;
    private final int nominal;
    private final String name;

    public static List<CurrencyENUM> getAllAvailable() {
        return List.of(RUR.getDeclaringClass().getEnumConstants());
    }

    public static CurrencyENUM findByNumCode(int numcode) {
        for (CurrencyENUM e : values()) {
            if (e.numCode == numcode) {
                return e;
            }
        }
        throw new NoSuchElementException("Cant find such CurrencyENUM by NumCode ");
    }

}
