package currencyconverter.core.entity.сurrency;

import lombok.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "ValuteDTO")
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ValuteDTO {

    @XmlAttribute(name = "ID")
    private String id;

    @XmlElement(name = "NumCode")
    private int numcode;

    @XmlElement(name = "CharCode")
    private String charcode;

    @XmlElement(name = "Nominal")
    private int nominal;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Value")
    @XmlJavaTypeAdapter(CurseAdaptor.class) // just for fun
    private Double value;


}
