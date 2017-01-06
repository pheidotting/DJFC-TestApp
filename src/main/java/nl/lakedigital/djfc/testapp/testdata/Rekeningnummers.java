package nl.lakedigital.djfc.testapp.testdata;

import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.domain.SoortEntiteit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Rekeningnummers extends AbstractTestData<JsonRekeningNummer>{

    @Override
    public void maakEntiteiten() {
        JsonRekeningNummer rekeningNummer = new JsonRekeningNummer();

        rekeningNummer.setRekeningnummer(UUID.randomUUID().toString().replace("-","").substring(0,18));

        entiteiten.add(rekeningNummer);
    }

    @Override
    public void setSoortEntiteit(JsonRekeningNummer entiteit, SoortEntiteit soortEntiteit, Long entiteitId) {
        entiteit.setSoortEntiteit(soortEntiteit.name());entiteit.setEntiteitId(entiteitId);
    }
    }
