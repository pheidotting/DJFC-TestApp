package nl.lakedigital.djfc.selenide.pages;

import com.codeborne.selenide.SelenideElement;
import nl.lakedigital.djfc.selenide.pages.commons.AbstractPagina;
import nl.lakedigital.djfc.selenide.pages.gegevens.HypotheekGegevens;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;

public class Hypotheek extends AbstractPagina implements HypotheekGegevens<SelenideElement> {
    @Override
    public SelenideElement bank() {
        return $(By.id("bank"));
    }

    @Override
    public SelenideElement select_hypotheekVorm() {
        return $(By.id("hypotheekVorm"));
    }

    @Override
    public SelenideElement koppelHypotheek() {
        return $(By.id("koppelHypotheek"));
    }

    @Override
    public SelenideElement leningNummer() {
        return $(By.id("leningNummer"));
    }

    @Override
    public SelenideElement hypotheekBedrag() {
        return $(By.id("hypotheekBedrag"));
    }

    @Override
    public SelenideElement boxI() {
        return $(By.id("boxI"));
    }

    @Override
    public SelenideElement boxIII() {
        return $(By.id("boxIII"));
    }

    @Override
    public SelenideElement rente() {
        return $(By.id("rente"));
    }

    @Override
    public SelenideElement marktWaarde() {
        return $(By.id("marktWaarde"));
    }

    @Override
    public SelenideElement onderpand() {
        return $(By.id("onderpand"));
    }

    @Override
    public SelenideElement koopsom() {
        return $(By.id("koopsom"));
    }

    @Override
    public SelenideElement vrijeVerkoopWaarde() {
        return $(By.id("vrijeVerkoopWaarde"));
    }

    @Override
    public SelenideElement taxatieDatum() {
        return $(By.id("taxatieDatum"));
    }

    @Override
    public SelenideElement wozWaarde() {
        return $(By.id("wozWaarde"));
    }

    @Override
    public SelenideElement waardeVoorVerbouwing() {
        return $(By.id("waardeVoorVerbouwing"));
    }

    @Override
    public SelenideElement waardeNaVerbouwing() {
        return $(By.id("waardeNaVerbouwing"));
    }

    @Override
    public SelenideElement ingangsDatum() {
        return $(By.id("ingangsDatum"));
    }

    @Override
    public SelenideElement duur() {
        return $(By.id("duur"));
    }

    @Override
    public SelenideElement eindDatum() {
        return $(By.id("eindDatum"));
    }

    @Override
    public SelenideElement ingangsDatumRenteVastePeriode() {
        return $(By.id("ingangsDatumRenteVastePeriode"));
    }

    @Override
    public SelenideElement duurRenteVastePeriode() {
        return $(By.id("duurRenteVastePeriode"));
    }

    @Override
    public SelenideElement eindDatumRenteVastePeriode() {
        return $(By.id("eindDatumRenteVastePeriode"));
    }

    @Override
    public SelenideElement omschrijving() {
        return $(By.id("omschrijving"));
    }

    @Override
    public SelenideElement opslaanBedrijf() {
        return $(By.id("opslaanBedrijf"));
    }

    //    private SelenideElement bank = $(By.id("bank"));
    //    private SelenideElement hypotheekVorm = $(By.id("hypotheekVorm"));
    //    private SelenideElement koppelHypotheek = $(By.id("koppelHypotheek"));
    //    private SelenideElement leningNummer = $(By.id("leningNummer"));
    //    private SelenideElement hypotheekBedrag = $(By.id("hypotheekBedrag"));
    //    private SelenideElement boxI = $(By.id("boxI"));
    //    private SelenideElement boxIII = $(By.id("boxIII"));
    //    private SelenideElement rente = $(By.id("rente"));
    //    private SelenideElement marktWaarde = $(By.id("marktWaarde"));
    //    private SelenideElement onderpand = $(By.id("onderpand"));
    //    private SelenideElement koopsom = $(By.id("koopsom"));
    //    private SelenideElement vrijeVerkoopWaarde = $(By.id("vrijeVerkoopWaarde"));
    //    private SelenideElement taxatieDatum = $(By.id("taxatieDatum"));
    //    private SelenideElement wozWaarde = $(By.id("wozWaarde"));
    //    private SelenideElement waardeVoorVerbouwing = $(By.id("waardeVoorVerbouwing"));
    //    private SelenideElement waardeNaVerbouwing = $(By.id("waardeNaVerbouwing"));
    //    private SelenideElement ingangsDatum = $(By.id("ingangsDatum"));
    //    private SelenideElement duur = $(By.id("duur"));
    //    private SelenideElement eindDatum = $(By.id("eindDatum"));
    //    private SelenideElement ingangsDatumRenteVastePeriode = $(By.id("ingangsDatumRenteVastePeriode"));
    //    private SelenideElement duurRenteVastePeriode = $(By.id("duurRenteVastePeriode"));
    //    private SelenideElement eindDatumRenteVastePeriode = $(By.id("eindDatumRenteVastePeriode"));
    //    private SelenideElement omschrijving = $(By.id("omschrijving"));
    private SelenideElement opslaanBedrijf = $(By.id("opslaanBedrijf"));

    //    public void invullen(Logger LOGGER, String bank, String hypotheekVorm, String koppelHypotheek, String leningNummer, String hypotheekBedrag, String boxI, String boxIII, String rente, String marktWaarde, String onderpand, String koopsom, String vrijeVerkoopWaarde, LocalDate taxatieDatum, String wozWaarde, String waardeVoorVerbouwing, String waardeNaVerbouwing, LocalDate ingangsDatum, Long duur, LocalDate eindDatum, LocalDate ingangsDatumRenteVastePeriode, Long duurRenteVastePeriode, LocalDate eindDatumRenteVastePeriode, String omschrijving) {
    //        setBank(LOGGER,bank);
    //        setHypotheekVorm(LOGGER,hypotheekVorm);
    //        setKoppelHypotheek(LOGGER,koppelHypotheek);
    //        setLeningNummer(LOGGER,leningNummer);
    //        setHypotheekBedrag(LOGGER,hypotheekBedrag);
    //        setBoxI(LOGGER,boxI);
    //        setBoxIII(LOGGER,boxIII);
    //        setRente(LOGGER,rente);
    //        setMarktWaarde(LOGGER,marktWaarde);
    //        setOnderpand(LOGGER,onderpand);
    //        setKoopsom(LOGGER,koopsom);
    //        setVrijeVerkoopWaarde(LOGGER,vrijeVerkoopWaarde);
    //        setTaxatieDatum(LOGGER,taxatieDatum);
    //        setWozWaarde(LOGGER,wozWaarde);
    //        setWaardeVoorVerbouwing(LOGGER,waardeVoorVerbouwing);
    //        setWaardeNaVerbouwing(LOGGER,waardeNaVerbouwing);
    //        setIngangsDatum(LOGGER,ingangsDatum);
    //        setDuur(LOGGER,duur);
    //        setEindDatum(LOGGER,eindDatum);
    //        setIngangsDatumRenteVastePeriode(LOGGER,ingangsDatumRenteVastePeriode);
    //        setDuurRenteVastePeriode(LOGGER,duurRenteVastePeriode);
    //        setEindDatumRenteVastePeriode(LOGGER,eindDatumRenteVastePeriode);
    //        setOmschrijving(LOGGER,omschrijving);
    //    }
    //
    //    public void setBank(Logger LOGGER, String bank) {
    //        logInvullen(LOGGER,this.bank,bank);
    //        this.bank.setValue(bank);
    //    }
    //
    //    public void setHypotheekVorm(Logger LOGGER, String hypotheekVorm) {
    //        logInvullen(LOGGER,this.hypotheekVorm,hypotheekVorm);
    //        this.hypotheekVorm.setValue(hypotheekVorm);
    //    }
    //
    //    public void setKoppelHypotheek(Logger LOGGER, String koppelHypotheek) {
    //        logInvullen(LOGGER,this.koppelHypotheek,koppelHypotheek);
    //        this.koppelHypotheek.setValue(koppelHypotheek);
    //    }
    //
    //    public void setLeningNummer(Logger LOGGER, String leningNummer) {
    //        logInvullen(LOGGER,this.leningNummer,leningNummer);
    //        this.leningNummer.setValue(leningNummer);
    //    }
    //
    //    public void setHypotheekBedrag(Logger LOGGER, String hypotheekBedrag) {
    //        logInvullen(LOGGER,this.hypotheekBedrag,hypotheekBedrag);
    //        this.hypotheekBedrag.setValue(hypotheekBedrag);
    //    }
    //
    //    public void setBoxI(Logger LOGGER, String boxI) {
    //        logInvullen(LOGGER,this.boxI,boxI);
    //        this.boxI.setValue(boxI);
    //    }
    //
    //    public void setBoxIII(Logger LOGGER, String boxIII) {
    //        logInvullen(LOGGER,this.boxIII,boxIII);
    //        this.boxIII.setValue(boxIII);
    //    }
    //
    //    public void setRente(Logger LOGGER, String rente) {
    //        logInvullen(LOGGER,this.rente,rente);
    //        this.rente.setValue(rente);
    //    }
    //
    //    public void setMarktWaarde(Logger LOGGER, String marktWaarde) {
    //        logInvullen(LOGGER,this.marktWaarde,marktWaarde);
    //        this.marktWaarde.setValue(marktWaarde);
    //    }
    //
    //    public void setOnderpand(Logger LOGGER, String onderpand) {
    //        logInvullen(LOGGER,this.onderpand,onderpand);
    //        this.onderpand.setValue(onderpand);
    //    }
    //
    //    public void setKoopsom(Logger LOGGER, String koopsom) {
    //        logInvullen(LOGGER,this.koopsom,koopsom);
    //        this.koopsom.setValue(koopsom);
    //    }
    //
    //    public void setVrijeVerkoopWaarde(Logger LOGGER, String vrijeVerkoopWaarde) {
    //        logInvullen(LOGGER,this.vrijeVerkoopWaarde,vrijeVerkoopWaarde);
    //        this.vrijeVerkoopWaarde.setValue(vrijeVerkoopWaarde);
    //    }
    //
    //    public void setTaxatieDatum(Logger LOGGER, LocalDate taxatieDatum) {
    //        logInvullen(LOGGER,this.taxatieDatum,taxatieDatum.toString("DDMMYYYY"));
    //        this.taxatieDatum.setValue(taxatieDatum.toString("DDMMYYYY"));
    //        this.taxatieDatum.sendKeys(Keys.TAB);
    //        logIsGevuldMet(LOGGER,this.taxatieDatum,taxatieDatum.toString("DD-MM-YYYY"));
    //    }
    //
    //    public void setWozWaarde(Logger LOGGER, String wozWaarde) {
    //        logInvullen(LOGGER,this.wozWaarde,wozWaarde);
    //        this.wozWaarde.setValue(wozWaarde);
    //    }
    //
    //    public void setWaardeVoorVerbouwing(Logger LOGGER, String waardeVoorVerbouwing) {
    //        logInvullen(LOGGER,this.waardeVoorVerbouwing,waardeVoorVerbouwing);
    //        this.waardeVoorVerbouwing.setValue(waardeVoorVerbouwing);
    //    }
    //
    //    public void setWaardeNaVerbouwing(Logger LOGGER, String waardeNaVerbouwing) {
    //        logInvullen(LOGGER,this.waardeNaVerbouwing,waardeNaVerbouwing);
    //        this.waardeNaVerbouwing.setValue(waardeNaVerbouwing);
    //    }
    //
    //    public void setIngangsDatum(Logger LOGGER, LocalDate ingangsDatum) {
    //        logInvullen(LOGGER,this.ingangsDatum,ingangsDatum.toString("DDMMYYYY"));
    //        this.ingangsDatum.setValue(ingangsDatum.toString("DDMMYYYY"));
    //        this.ingangsDatum.sendKeys(Keys.TAB);
    //        logIsGevuldMet(LOGGER,this.ingangsDatum,ingangsDatum.toString("DD-MM-YYYY"));
    //    }
    //
    //    public void setDuur(Logger LOGGER, Long duur) {
    //        logInvullen(LOGGER,this.duur,String.valueOf(duur));
    //        this.duur.setValue(String.valueOf(duur));
    //    }
    //
    //    public void setEindDatum(Logger LOGGER, LocalDate eindDatum) {
    //        logInvullen(LOGGER,this.eindDatum,eindDatum.toString("DDMMYYYY"));
    //        this.eindDatum.setValue(eindDatum.toString("DDMMYYYY"));
    //        this.eindDatum.sendKeys(Keys.TAB);
    //        logIsGevuldMet(LOGGER,this.eindDatum,eindDatum.toString("DD-MM-YYYY"));
    //    }
    //
    //    public void setIngangsDatumRenteVastePeriode(Logger LOGGER, LocalDate ingangsDatumRenteVastePeriode) {
    //        logInvullen(LOGGER,this.ingangsDatumRenteVastePeriode,ingangsDatumRenteVastePeriode.toString("DDMMYYYY"));
    //        this.ingangsDatumRenteVastePeriode.setValue(ingangsDatumRenteVastePeriode.toString("DDMMYYYY"));
    //        this.ingangsDatumRenteVastePeriode.sendKeys(Keys.TAB);
    //        logIsGevuldMet(LOGGER,this.ingangsDatumRenteVastePeriode,ingangsDatumRenteVastePeriode.toString("DD-MM-YYYY"));
    //    }
    //
    //    public void setDuurRenteVastePeriode(Logger LOGGER, Long duurRenteVastePeriode) {
    //        logInvullen(LOGGER,this.duurRenteVastePeriode,String.valueOf(duurRenteVastePeriode));
    //        this.duurRenteVastePeriode.setValue(String.valueOf(duurRenteVastePeriode));
    //    }
    //
    //    public void setEindDatumRenteVastePeriode(Logger LOGGER, LocalDate eindDatumRenteVastePeriode) {
    //        logInvullen(LOGGER,this.eindDatumRenteVastePeriode,eindDatumRenteVastePeriode.toString("DDMMYYYY"));
    //        this.eindDatumRenteVastePeriode.setValue(eindDatumRenteVastePeriode.toString("DDMMYYYY"));
    //        this.eindDatumRenteVastePeriode.sendKeys(Keys.TAB);
    //        logIsGevuldMet(LOGGER,this.eindDatumRenteVastePeriode,eindDatumRenteVastePeriode.toString("DD-MM-YYYY"));
    //    }
    //
    //    public void setOmschrijving(Logger LOGGER, String omschrijving) {
    //        logInvullen(LOGGER,this.omschrijving,omschrijving);
    //        this.omschrijving.setValue(omschrijving);
    //    }
    //
    public void klikOpslaan(Logger LOGGER) {
        logKlik(LOGGER, this.opslaanBedrijf);
        this.opslaanBedrijf.click();
    }
}