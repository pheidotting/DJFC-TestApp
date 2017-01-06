package nl.lakedigital.djfc.testapp.testdata;

import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.domain.SoortEntiteit;

import java.util.List;

public class Polissen extends  AbstractTestData<JsonPolis> {
    @Override
    public void maakEntiteiten() {
        JsonPolis polis = new JsonPolis();
        polis.setStatus("ACT");
        polis.setPolisNummer("1234567890");
        polis.setKenmerk("123Kenmerk321");
        polis.setIngangsDatum("01-04-2016");
        polis.setEindDatum("02-05-2017");
        polis.setPremie("123,45");
        polis.setWijzigingsDatum("03-05-2016");
        polis.setProlongatieDatum("06-07-2017");
        polis.setBetaalfrequentie("K");
        polis.setDekking("23Dekking944");
        polis.setVerzekerdeZaak("Dit is de verzekerde zaak");
        polis.setMaatschappij("2");
        polis.setSoort("Annulerings");
        //    polis.setBedrijf();
        //    polis.setRelatie();
        polis.setOmschrijvingVerzekering("Omschrijvingverzekering");
        //    polis.setSoortEntiteit();

entiteiten.add(polis);
    }

    @Override
    public void setSoortEntiteit(JsonPolis entiteit, SoortEntiteit soortEntiteit, Long entiteitId) {
//entiteit.setRelatie(entiteitId.toString());
    }
}
