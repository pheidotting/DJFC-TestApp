package nl.lakedigital.djfc.testapp.teststappen;

import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.testapp.testdata.TestGegevens;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ToevoegenPolissenEnControleer extends AbstractTestStap {
    @Override
    public Long voerUit(Long relatieId, Long medewerkerId, String sessieCode) {
        List<String> soorten = djfcClient.alleParticulierePolisSoorten(medewerkerId, sessieCode);
        //        soorten.addAll(djfcClient.alleZakelijkePolisSoorten(medewerkerId,sessieCode));

        for (String soort : soorten) {
            JsonPolis polis = maakPolis(soort, relatieId);

            nieuweTrackAndTraceId();
            djfcClient.opslaanPolis(polis, medewerkerId, sessieCode, TestGegevens.trackAndTraceId);

            //Polis ophalen uit de database, AUD controleren en REVINFO controleren
            getTransactions();

            List<Object> opgeslagenObjects = getSessionDJFC().createSQLQuery("select * from POLIS where relatie = " + relatieId + " order by id asc").list();
            Object opgeslagenObject = opgeslagenObjects.get(opgeslagenObjects.size() - 1);
            List<Object> opgeslagenObjectAUDs = getSessionDJFC().createSQLQuery("select * from POLIS_AUD where relatie = " + relatieId + " order by id asc").list();
            Object opgeslagenObjectAUD = opgeslagenObjectAUDs.get(opgeslagenObjectAUDs.size() - 1);

            Long revId = Long.valueOf(((Object[]) opgeslagenObjectAUD)[0].toString());

            controleerPolisObject(opgeslagenObject, polis, false);
            controleerPolisObject(opgeslagenObjectAUD, polis, true);

            controleerRevOGA(revId);

            commit();

            //Polis ophalen via de REST service en controleren.
            Object[] o = (Object[]) opgeslagenObject;

            JsonPolis opgehaaldePolis = djfcClient.leesPolis(Long.valueOf(o[1].toString()), medewerkerId, sessieCode);
            opgehaaldePolis.setId(null);
            opgehaaldePolis.setStatus(opgehaaldePolis.getStatus().substring(0, 3).toUpperCase());
            opgehaaldePolis.setProlongatieDatum(zetDatumOm(opgehaaldePolis.getProlongatieDatum()));
            opgehaaldePolis.setIngangsDatum(zetDatumOm(opgehaaldePolis.getIngangsDatum()));
            opgehaaldePolis.setWijzigingsDatum(zetDatumOm(opgehaaldePolis.getWijzigingsDatum()));
            opgehaaldePolis.setEindDatum(zetDatumOm(opgehaaldePolis.getEindDatum()));
            opgehaaldePolis.setBetaalfrequentie(opgehaaldePolis.getBetaalfrequentie().substring(0, 1));
//            opgehaaldePolis.setOpmerkingenModel(null);
            opgehaaldePolis.setPremie((opgehaaldePolis.getPremie().replace(".",",")));
//            opgehaaldePolis.setOpmerkingenModel(null);polis.setOpmerkingenModel(null);

            assertThat(opgehaaldePolis, is(polis));
        }

        return null;
    }

    private JsonPolis maakPolis(String soort, Long relatieId) {
        String dateformat = "dd-MM-yyyy";

        JsonPolis polis = new JsonPolis();

        polis.setSoort(soort);
//        polis.setRelatie(relatieId.toString());
        polis.setKenmerk(randomString(100));
        polis.setBetaalfrequentie("H");//alf jaar");
        polis.setPolisNummer(randomString(25));
        polis.setDekking(randomString(200));
        polis.setEindDatum(LocalDate.now().toString(dateformat));
        polis.setIngangsDatum(LocalDate.now().toString(dateformat));
        polis.setOmschrijvingVerzekering(randomString(200));
        polis.setPremie("123,45");
        polis.setProlongatieDatum(LocalDate.now().toString(dateformat));
        polis.setStatus("ACT");
        polis.setVerzekerdeZaak(randomString(200));
        polis.setWijzigingsDatum(LocalDate.now().toString(dateformat));
        polis.setMaatschappij("1");

        return polis;
    }

    private String randomString(int lengte) {
        StringBuilder stringBuilder = new StringBuilder();

        while (stringBuilder.toString().length() < lengte) {
            stringBuilder.append(UUID.randomUUID().toString().replace("-", ""));
        }

        return stringBuilder.toString().substring(0, lengte);
    }

}
