package currencyconverter.core.entity.сurrency;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CurseAdaptor extends XmlAdapter<String , Double> {

    @Override
    public Double unmarshal(String s) throws Exception {
        return Double.parseDouble(s.replaceAll(",", "."));
    }

    @Override
    public String marshal(Double aDouble) throws Exception {
        return aDouble.toString();
    }
}
