package nl.lakedigital.djfc.testapp.teststappen;

import nl.lakedigital.djfc.commons.json.*;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.testapp.testdata.Personen;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;

public class OpslaanRelatieEnControleer extends AbstractTestStap {
    protected Personen personen = new Personen();

    @Override
    public Long voerUit(Long id, Long medewerkerId, String sessieCode) {
        JsonRelatie         relatie = personen.getEntiteit(null, null);

        djfcClient.opslaanRelatie(relatie, medewerkerId, sessieCode, nieuweTrackAndTraceId());
relatie.setKantoor(1L);

        //Opgeslagen Relatie opzoeken in de database, deze, de AUD tabel en de REVINFO tabel controleren
        getTransactions();

        Object opgeslagenObject =         getSessionDJFC().createSQLQuery("select * from GEBRUIKER where id = "+relatie.getId()).uniqueResult();
        List<Object> opgeslagenObjectAUDs =         getSessionDJFC().createSQLQuery("select * from GEBRUIKER_AUD where id = "+relatie.getId()).list();
        Object opgeslagenObjectAUD=opgeslagenObjectAUDs.get(opgeslagenObjectAUDs.size()-1);

        Long revId =Long.valueOf (((Object[]) opgeslagenObjectAUD)[0].toString());

        controleerRelatieObject(opgeslagenObject,relatie,false);
        controleerRelatieObject(opgeslagenObjectAUD,relatie,true);

        controleerRevDJFC(revId);

        commit();

        //Relatie ophalen via de REST service
        JsonRelatie opgehaaldeRelatie = djfcClient.leesRelatie(relatie.getId(),medewerkerId,sessieCode);
        opgehaaldeRelatie.setKantoor(1L);
        assertThat(opgehaaldeRelatie,is(relatie));

        return relatie.getId();
    }
}
