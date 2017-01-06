package nl.lakedigital.djfc.testapp.teststappen;

import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonRelatie;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.testapp.testdata.Adressen;
import nl.lakedigital.djfc.testapp.testdata.TestGegevens;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class WijzigAdresEnControleer extends AbstractTestStap{
    @Override
    public Long voerUit(Long relatieId, Long medewerkerId, String sessieCode) {
        List<JsonAdres> adressen = djfcClient.leesAdres(SoortEntiteit.RELATIE, relatieId, sessieCode);
        JsonAdres adres= adressen.get(0);
        JsonAdres adresGegevens = this.adressen.getEntiteit(SoortEntiteit.RELATIE,relatieId);
        adres.setPostcode(adresGegevens.getPostcode());
        adres.setToevoeging(adresGegevens.getToevoeging());
        adres.setHuisnummer(adresGegevens.getHuisnummer());
        adres.setStraat(adresGegevens.getStraat());
        adres.setPlaats(adresGegevens.getPlaats());

        djfcClient.opslaanAdres(adressen, medewerkerId, sessieCode, TestGegevens.trackAndTraceId);

        //Adres ophalen uit de database, AUD controleren en REVINFO controleren
        getTransactions();

        Object opgeslagenObject = getSessionOGA().createSQLQuery("select * from ADRES where soortentiteit = 'RELATIE' and entiteitid = " + relatieId).uniqueResult();
        List<Object> opgeslagenObjectAUDs = getSessionOGA().createSQLQuery("select * from ADRES_AUD where soortentiteit = 'RELATIE' and entiteitid = " + relatieId).list();
        Object opgeslagenObjectAUD = opgeslagenObjectAUDs.get(opgeslagenObjectAUDs.size() - 1);

        Long revId = Long.valueOf(((Object[]) opgeslagenObjectAUD)[0].toString());

        controleerAdresObject(opgeslagenObject, adres, false);
        controleerAdresObject(opgeslagenObjectAUD, adres, true);

        controleerRevOGA(revId);

        commit();

        //Adres ophalen via de REST service en controleren.
        JsonAdres opgehaaldAdres = djfcClient.leesAdres(SoortEntiteit.RELATIE, relatieId, sessieCode).get(0);
        assertThat(opgehaaldAdres, is(adres));

        return null;
    }
}
