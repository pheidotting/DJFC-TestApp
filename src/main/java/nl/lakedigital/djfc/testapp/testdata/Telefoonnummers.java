package nl.lakedigital.djfc.testapp.testdata;

import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.domain.SoortEntiteit;

public class Telefoonnummers extends AbstractTestData<JsonTelefoonnummer> {
    @Override
    public void maakEntiteiten() {
        JsonTelefoonnummer telefoonnummer=new JsonTelefoonnummer();

        telefoonnummer.setTelefoonnummer("0612345678");telefoonnummer.setSoort("MOBIEL");telefoonnummer.setOmschrijving("Omschrijving");

        entiteiten.add(telefoonnummer);
    }

    @Override
    public void setSoortEntiteit(JsonTelefoonnummer entiteit, SoortEntiteit soortEntiteit, Long entiteitId) {
        entiteit.setSoortEntiteit(soortEntiteit.name());entiteit.setEntiteitId(entiteitId);
    }
}
