package nl.lakedigital.djfc.testapp.teststappen;

import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.testapp.testdata.TestGegevens;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ToevoegenTelefoonnummerEnControleer extends AbstractTestStap {
    @Override
    public Long voerUit(Long relatieId, Long medewerkerId, String sessieCode) {
        JsonTelefoonnummer telefoonnummer= telefoonnummers.getEntiteit(SoortEntiteit.RELATIE,relatieId);
        djfcClient.opslaanTelefoonnummer(telefoonnummer, medewerkerId, sessieCode, TestGegevens.trackAndTraceId);

        //rekeningNummer ophalen uit de database, AUD controleren en REVINFO controleren
        getTransactions();

        Object opgeslagenObject = getSessionOGA().createSQLQuery("select * from TELEFOONNUMMER where soortentiteit = 'RELATIE' and entiteitid = " + relatieId).uniqueResult();
        List<Object> opgeslagenObjectAUDs = getSessionOGA().createSQLQuery("select * from TELEFOONNUMMER_AUD where soortentiteit = 'RELATIE' and entiteitid = " + relatieId).list();
        Object opgeslagenObjectAUD = opgeslagenObjectAUDs.get(opgeslagenObjectAUDs.size() - 1);

        Long revId = Long.valueOf(((Object[]) opgeslagenObjectAUD)[1].toString());

        controleerTelefoonnummerObject(opgeslagenObject, telefoonnummer, false);
        controleerTelefoonnummerObject(opgeslagenObjectAUD, telefoonnummer, true);

        controleerRevOGA(revId);

        commit();

        //Adres ophalen via de REST service en controleren.
        JsonTelefoonnummer opgehaaldTelefoonnummer = djfcClient.leesTelefoonnummer(SoortEntiteit.RELATIE, relatieId, sessieCode).get(0);
        opgehaaldTelefoonnummer.setId(null);
        assertThat(opgehaaldTelefoonnummer, is(telefoonnummer));

        return null;
    }
}
