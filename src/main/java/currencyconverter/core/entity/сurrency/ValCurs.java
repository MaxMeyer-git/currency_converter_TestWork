package currencyconverter.core.entity.сurrency;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlRootElement (name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@ToString
public class ValCurs {

    @XmlAttribute (name = "Date")
    private String date;

    @XmlAttribute (name = "name")
    private String name;

    @XmlElement (name = "Valute")
    List<ValuteDTO> valDtoList;

}
