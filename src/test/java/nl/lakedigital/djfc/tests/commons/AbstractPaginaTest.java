package nl.lakedigital.djfc.tests.commons;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.selenide.pages.*;
import nl.lakedigital.djfc.testapp.domein.Medewerker;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPaginaTest {
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractPaginaTest.class);

    protected LoginPagina loginPagina;
    protected Dashboard dashboard;
    protected LijstRelaties lijstRelaties;
    protected BeherenRelatie beherenRelatie;
    protected BeherenPolis beherenPolis;
    protected BeherenPolissen beherenPolissen;
    protected BeherenSchade beherenSchade;
    protected BeherenSchades beherenSchades;

    protected BeherenBedrijf beherenBedrijf;
    protected LijstBedrijven lijstBedrijven;

    private String basisUrl;
    private String basisUrlRest;

    protected Lorem lorem;
    protected List<String> opmerkingen;
    protected List<JsonAdres> adressen = new ArrayList<>();
    protected List<JsonRekeningNummer> rekeningNummers = new ArrayList<>();
    protected List<JsonTelefoonnummer> telefoonnummers = new ArrayList<>();

    protected boolean opServer = false;

    public AbstractPaginaTest(BeherenBedrijf beherenBedrijf, LijstBedrijven lijstBedrijven) {
        this.beherenBedrijf = beherenBedrijf;
        this.lijstBedrijven = lijstBedrijven;

        loginPagina = new LoginPagina();
        dashboard = new Dashboard();
        lijstRelaties = new LijstRelaties();
        beherenRelatie = new BeherenRelatie();
        beherenPolis = new BeherenPolis();
        beherenPolissen = new BeherenPolissen();
        beherenSchade = new BeherenSchade();
        beherenSchades = new BeherenSchades();
        lorem = LoremIpsum.getInstance();
        opmerkingen = new ArrayList<>();
    }

    protected String maakNaamMedewerker(Medewerker medewerker) {
        StringBuilder sb = new StringBuilder();
        sb.append(medewerker.getVoornaam());
        sb.append(" ");
        if (medewerker.getTussenvoegsel() != null) {
            sb.append(medewerker.getTussenvoegsel());
            sb.append(" ");
        }
        sb.append(medewerker.getAchternaam());

        return sb.toString();
    }


    protected String getStringOnMillis() {
        String s = String.valueOf(System.currentTimeMillis());
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return s;
    }

    protected JsonPolis maakPolis(String verzekeringsMaatschappij, String soortVerzekering, String status, String polisNummer, String kenmerk, String dekking, String verzekerdeZaak, String premie, LocalDate ingangsDatumString, LocalDate wijzigingsdatumString, LocalDate prolongatiedatumString, String betaalfrequentie, String omschrijvingVerzekering) {
        JsonPolis polis = new JsonPolis();

        polis.setMaatschappij(verzekeringsMaatschappij);
        polis.setSoort(soortVerzekering);
        polis.setStatus(status);
        polis.setPolisNummer(polisNummer);
        polis.setKenmerk(kenmerk);
        polis.setDekking(dekking);
        polis.setVerzekerdeZaak(verzekerdeZaak);
        polis.setPremie(premie);
        polis.setIngangsDatum(ingangsDatumString.toString("yyyy-MM-dd"));
        polis.setWijzigingsDatum(wijzigingsdatumString.toString("yyyy-MM-dd"));
        polis.setProlongatieDatum(prolongatiedatumString.toString("yyyy-MM-dd"));
        polis.setBetaalfrequentie(betaalfrequentie);
        polis.setOmschrijvingVerzekering(omschrijvingVerzekering);

        return polis;
    }

    protected String beginHoofdletters(String tekst) {
        String[] woorden = tekst.split(" ");
        StringBuffer sb = new StringBuffer();

        for (String woord : woorden) {
            sb.append(woord.substring(0, 1).toUpperCase() + woord.substring(1, woord.length()).toLowerCase());
            sb.append(" ");
        }

        return sb.toString().trim();
    }
}
