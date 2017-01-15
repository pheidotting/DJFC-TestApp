package nl.lakedigital.djfc.selenide.pages.gegevens;


public interface HypotheekGegevens<T> {
    T bank();

    T select_hypotheekVorm();

    T koppelHypotheek();

    T leningNummer();

    T hypotheekBedrag();

    T boxI();

    T boxIII();

    T rente();

    T marktWaarde();

    T onderpand();

    T koopsom();

    T vrijeVerkoopWaarde();

    T taxatieDatum();

    T wozWaarde();

    T waardeVoorVerbouwing();

    T waardeNaVerbouwing();

    T ingangsDatum();

    T duur();

    T eindDatum();

    T ingangsDatumRenteVastePeriode();

    T duurRenteVastePeriode();

    T eindDatumRenteVastePeriode();

    T omschrijving();

    T opslaanBedrijf();
}
