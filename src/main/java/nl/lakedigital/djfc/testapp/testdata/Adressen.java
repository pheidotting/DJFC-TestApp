package nl.lakedigital.djfc.testapp.testdata;

import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.domain.SoortEntiteit;

import java.util.ArrayList;
import java.util.List;

public class Adressen extends AbstractTestData<JsonAdres>{
    @Override
    public void maakEntiteiten() {
        JsonAdres adres = new JsonAdres();

        adres.setToevoeging("A");
        adres.setHuisnummer(41L);
        adres.setPostcode("7894AB");
        adres.setStraat("Eemslandweg");
        adres.setPlaats("Zwartemeer");
        adres.setSoortAdres("POSTADRES");

        entiteiten.add(adres);

        JsonAdres adres1=new JsonAdres();

        adres1.setHuisnummer(65L);
        adres1.setPostcode("7891RB");
        adres1.setStraat("Herderstraat");
        adres1.setPlaats("Klazienaveen");
        adres1.setSoortAdres("WOONADRES");

        entiteiten.add(adres1);

        JsonAdres adres2=new JsonAdres();

        adres2.setHuisnummer(2L);
        adres2.setPostcode("7891JT");
        adres2.setStraat("Loperstraat");
        adres2.setPlaats("Klazienaveen");
        adres2.setSoortAdres("WOONADRES");

        entiteiten.add(adres2);
    }

    @Override
    public void setSoortEntiteit(JsonAdres entiteit, SoortEntiteit soortEntiteit, Long entiteitId) {
        entiteit.setSoortEntiteit(soortEntiteit.name());entiteit.setEntiteitId(entiteitId);
    }
}
