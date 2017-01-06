package nl.lakedigital.djfc.tests;

import com.codeborne.selenide.Condition;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ZakelijkTest extends AbstractTest{
    private final static Logger LOGGER = LoggerFactory.getLogger(ZakelijkTest.class);

    public ZakelijkTest() {
        super(LOGGER);
    }
    private String naam;

    @Test
    public void voeruitTestZakelijk(){

        inloggen("djfc.bene", "bene", dashboard.getNaarParticulier());

        dashboard.klikNaarZakelijk();

        naam = lorem.getWords(2);

        lijstBedrijven.isZoekTermAanwezig();
        assertNull(lijstBedrijven.zoekBedrijf(naam, false));
        lijstBedrijven.klikToevoegenNieuwBedrijf();

        beherenBedrijf.testFoutmeldingBijNietAlleVerplichteVelden();
        beherenBedrijf.testFoutmeldingWegBijAlleVerplichteVeldenGevuld(naam);
        beherenBedrijf.invullenAlLeVelden(null,getStringOnMillis(),"rechtsvorm",lorem.getEmail(),lorem.getUrl(),"hoedanigheid","caoverplichtingen");
        beherenBedrijf.        adresToevoegen();
        beherenBedrijf.voegTelefoonnummerToe();
        beherenBedrijf.voegOpmerkingToeBijRelatie();
        beherenBedrijf.voegContactpersoonToe();
        beherenBedrijf.        klikOpslaan();

        lijstBedrijven.zoekEnSelecteer(naam, beherenBedrijf.getOpslaanBedrijf());
        beherenSchades.klikHomeKnop(LOGGER);

        testInvoerenNieuwePolissen();
        testInvoerenSchades();



        //                beherenRelatie.getValidationMessages();//DUMMY
//
//        moi();
//
//        beherenBedrijf.blabla();
//        beherenBedrijf.blabla2();
//        beherenBedrijf.blabla3();
    }

    private void testInvoerenSchades() {
        dashboard.klikNaarZakelijk();
        lijstBedrijven.selecteer(lijstBedrijven.zoekBedrijf(naam,false),beherenBedrijf.getOpslaanBedrijf());

        beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.SCHADE_TOEVOEGEN, LOGGER, beherenSchade.getSchadeMeldingOpslaan());

        List<String> polissen = beherenSchade.getPolisVoorSchademelding();
        List<String> statussen = beherenSchade.getStatusSchade();

        for (String polis : polissen) {
            for (String status : statussen) {
                beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.SCHADE_TOEVOEGEN, LOGGER, beherenSchade.getSchadeMeldingOpslaan());

                String schadeNummerMij = getStringOnMillis();
                String schadeNummerTP = getStringOnMillis();
                beherenSchade.vulAlleVelden(polis, schadeNummerMij, schadeNummerTP, "gevallen", "straat", status, LocalDateTime.now(), LocalDateTime.now(), LocalDate.now(), "100", "zo ineens lag ik op straat");
            }
        }
        beherenSchades.klikHomeKnop(LOGGER);
    }

    private void testInvoerenNieuwePolissen() {
        dashboard.klikNaarZakelijk();
        lijstBedrijven.selecteer(lijstBedrijven.zoekBedrijf(naam,false),beherenBedrijf.getOpslaanBedrijf());

        beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.POLIS_TOEVOEGEN, LOGGER, beherenPolis.getOpslaanPolis());

        List<JsonPolis> aanwezigePolissen = new ArrayList<>();

        //        for (String soortVerzekering : beherenPolis.getSoortVerzekering()) {
        String soortVerzekering = "Auto";
        //            for (String maatschappij : beherenPolis.getVerzekeringsMaatschappij()) {
        String maatschappij = "Aegon";
        beherenRelatie.klikMenuItem(AbstractPagina.MenuItem.POLIS_TOEVOEGEN, LOGGER, beherenPolis.getOpslaanPolis());

        Long polisnummer = System.currentTimeMillis();
        String kenmerk = maatschappij + "-" + soortVerzekering;

        JsonPolis polis = maakPolis(maatschappij, soortVerzekering, "Actief", polisnummer + "", kenmerk, "dedekking", "verzzaak", "123", LocalDate.now(), LocalDate.now(), LocalDate.now(), "Kwartaal", "omschrijving");

        aanwezigePolissen.add(polis);
        beherenPolis.vulVelden(polis);

        beherenPolissen.getTitel().get(0).waitUntil(Condition.appears, 2000);

        LOGGER.debug("Controleren {} polissen", aanwezigePolissen.size());

        assertThat(beherenPolissen.getTitelList().size(), is(aanwezigePolissen.size()));

        List<JsonPolis> gecontroleerd = new ArrayList<>();
        for (int i = 0; i < beherenPolissen.getTitelList().size(); i++) {
            for (JsonPolis teControlerenPolis : aanwezigePolissen) {
                String titel = beherenPolissen.getTitel().get(i).getText();

                if (titel.equals(teControlerenPolis.getSoort() + " (" + teControlerenPolis.getPolisNummer() + ")")) {

                    beherenPolissen.controleerPolis(i, teControlerenPolis, LOGGER);

                    gecontroleerd.add(polis);
                }
            }
        }

        assertThat(gecontroleerd.size(), is(aanwezigePolissen.size()));
        //            }
        //        }
        beherenPolissen.klikHomeKnop(LOGGER);
    }
}
