package nl.lakedigital.djfc.testapp.testdata;

import nl.lakedigital.djfc.commons.json.JsonRelatie;
import nl.lakedigital.djfc.domain.SoortEntiteit;

import java.util.ArrayList;
import java.util.List;

public class Personen extends  AbstractTestData<JsonRelatie>{
    @Override
    public void maakEntiteiten() {
        JsonRelatie relatie = new JsonRelatie();

        relatie.setVoornaam("Bennie");
        relatie.setAchternaam("Jolink");
        relatie.setTussenvoegsel("van der");
        relatie.setRoepnaam("Ben");
        relatie.setOverlijdensdatum("05-04-2015");
        relatie.setEmailadres("bennie@normaal.nl");
        relatie.setGeslacht("Man");
        relatie.setBsn("123456789");
        relatie.setBurgerlijkeStaat("Gehuwd GVG");
        relatie.setGeboorteDatum("06-09-1950");

        entiteiten.add(relatie);
    }

    @Override
    public void setSoortEntiteit(JsonRelatie entiteit, SoortEntiteit soortEntiteit, Long entiteitId) {

    }
}
