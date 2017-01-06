package nl.lakedigital.djfc.testapp.teststappen;

import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.testapp.testdata.TestGegevens;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ToevoegenRekeningEnControleer extends AbstractTestStap {
    @Override
    public Long voerUit(Long relatieId, Long medewerkerId, String sessieCode) {
        JsonRekeningNummer rekeningNummer= rekeningnummers.getEntiteit(SoortEntiteit.RELATIE,relatieId);
        djfcClient.opslaanRekeningnummer(rekeningNummer, medewerkerId, sessieCode, TestGegevens.trackAndTraceId);

        //rekeningNummer ophalen uit de database, AUD controleren en REVINFO controleren
        getTransactions();

        Object opgeslagenObject = getSessionOGA().createSQLQuery("select * from REKENINGNUMMER where soortentiteit = 'RELATIE' and entiteitid = " + relatieId).uniqueResult();
        List<Object> opgeslagenObjectAUDs = getSessionOGA().createSQLQuery("select * from REKENINGNUMMER_AUD where soortentiteit = 'RELATIE' and entiteitid = " + relatieId).list();
        Object opgeslagenObjectAUD = opgeslagenObjectAUDs.get(opgeslagenObjectAUDs.size() - 1);

        Long revId = Long.valueOf(((Object[]) opgeslagenObjectAUD)[0].toString());

        controleerRekeningNummerObject(opgeslagenObject, rekeningNummer, false);
        controleerRekeningNummerObject(opgeslagenObjectAUD, rekeningNummer, true);

        controleerRevOGA(revId);

        commit();

        //Adres ophalen via de REST service en controleren.
        JsonRekeningNummer opgehaaldRekeningNummer = djfcClient.leesRekeningnummer(SoortEntiteit.RELATIE, relatieId, sessieCode).get(0);
        opgehaaldRekeningNummer.setId(null);
        assertThat(opgehaaldRekeningNummer, is(rekeningNummer));

        return null;
    }
}
