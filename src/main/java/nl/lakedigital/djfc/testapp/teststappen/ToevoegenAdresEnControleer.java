package nl.lakedigital.djfc.testapp.teststappen;

import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.testapp.testdata.TestGegevens;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ToevoegenAdresEnControleer extends AbstractTestStap {
    @Override
    public Long voerUit(Long relatieId, Long medewerkerId, String sessieCode) {
        JsonAdres adres = adressen.getEntiteit(SoortEntiteit.RELATIE, relatieId);

        List<JsonAdres> adressen = djfcClient.leesAdres(SoortEntiteit.RELATIE, relatieId, sessieCode);
        adressen.add(adres);

        djfcClient.opslaanAdres(adressen, medewerkerId, sessieCode, TestGegevens.trackAndTraceId);

        //Adres ophalen uit de database, AUD controleren en REVINFO controleren
        getTransactions();

        List<Object> opgeslagenObjects = getSessionOGA().createSQLQuery("select * from ADRES where soortentiteit = 'RELATIE' and entiteitid = " + relatieId).list();
        Object opgeslagenObject = opgeslagenObjects.get(opgeslagenObjects.size()-1);
        List<Object> opgeslagenObjectAUDs = getSessionOGA().createSQLQuery("select * from ADRES_AUD where soortentiteit = 'RELATIE' and entiteitid = " + relatieId).list();
        Object opgeslagenObjectAUD = opgeslagenObjectAUDs.get(opgeslagenObjectAUDs.size() - 1);

        Long revId = Long.valueOf(((Object[]) opgeslagenObjectAUD)[0].toString());

        controleerAdresObject(opgeslagenObject, adres, false);
        controleerAdresObject(opgeslagenObjectAUD, adres, true);

        controleerRevOGA(revId);

        commit();

        //Adres ophalen via de REST service en controleren.
        JsonAdres opgehaaldAdres = djfcClient.leesAdres(SoortEntiteit.RELATIE, relatieId, sessieCode).get(0);
        opgehaaldAdres.setId(null);
        assertThat(opgehaaldAdres, is(adres));

        return null;
    }
}
