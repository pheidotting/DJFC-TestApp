package nl.lakedigital.djfc.testapp.domein;

import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorValue(value = "R")
@AttributeOverrides({@AttributeOverride(name = "identificatie", column = @Column(name = "GEBRUIKERSNAAM"))})
@NamedQueries({
//         @NamedQuery(name = "Relatie.zoekAllesVoorKantoor", query = "select r from Relatie r where r.kantoor = :kantoor"), //
        @NamedQuery(name = "Relatie.zoekOpEmail", query = "select r from Relatie r where r.identificatie = :emailadres"), //
        @NamedQuery(name = "Relatie.zoekOpBsn", query = "select r from Relatie r where r.bsn = :bsn") //
        //        @NamedQuery(name = "Relatie.zoekOpTelefoonnummer", query = "select r from Relatie r inner join r.telefoonnummers t where t.telefoonnummer = :telefoonnummer"), //
//        @NamedQuery(name = "Relatie.zoekOpBedrijfsnaam", query = "select r from Relatie r inner join r.bedrijven b where b.naam LIKE :bedrijfsnaam")//
})
public class Relatie extends Gebruiker implements Serializable {
    private static final long serialVersionUID = -1920949633670770763L;

    @Column(name = "ROEPNAAM")
    private String roepnaam;

    @Column(name = "BSN")
    private String bsn;


    @Column(name = "GEBOORTEDATUM")
    @Temporal(TemporalType.DATE)
    private Date geboorteDatum;

    @Column(name = "OVERLIJDENSDATUM")
    @Temporal(TemporalType.DATE)
    private Date overlijdensdatum;

//    @Column(name = "GESLACHT", length = 1)
//    @Enumerated(EnumType.STRING)
//    private Geslacht geslacht;
//
//    @Column(name = "BURGERLIJKESTAAT", length = 1)
//    @Enumerated(EnumType.STRING)
//    private BurgerlijkeStaat burgerlijkeStaat;
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = OnderlingeRelatie.class, mappedBy = "relatie")
//    @NotAudited
//    private Set<OnderlingeRelatie> onderlingeRelaties;
//
//    @NotAudited
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Bedrijf.class, mappedBy = "relatie")
//    private Set<Bedrijf> bedrijven;
//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Hypotheek.class, mappedBy = "relatie", orphanRemoval = true)
//    @NotAudited
//    private Set<Hypotheek> hypotheken;
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = HypotheekPakket.class, mappedBy = "relatie", orphanRemoval = true)
//    @NotAudited
//    private Set<HypotheekPakket> hypotheekPakketten;

    public String getRoepnaam() {
        return roepnaam;
    }

    public void setRoepnaam(String roepnaam) {
        this.roepnaam = roepnaam;
    }

    public String getBsn() {
        return bsn;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

//    public Kantoor getKantoor() {
//        return kantoor;
//    }
//
//    public void setKantoor(Kantoor kantoor) {
//        this.kantoor = kantoor;
//    }

    public LocalDate getGeboorteDatum() {
        if (geboorteDatum == null) {
            return null;
        }
        return new LocalDate(geboorteDatum);
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum.toDateMidnight().toDate();
    }

    public LocalDate getOverlijdensdatum() {
        if (overlijdensdatum == null) {
            return null;
        }
        return new LocalDate(overlijdensdatum);
    }

    public void setOverlijdensdatum(LocalDate overlijdensdatum) {
        this.overlijdensdatum = overlijdensdatum.toDateMidnight().toDate();
    }

//    public Geslacht getGeslacht() {
//        return geslacht;
//    }
//
//    public void setGeslacht(Geslacht geslacht) {
//        this.geslacht = geslacht;
//    }
//
//    public BurgerlijkeStaat getBurgerlijkeStaat() {
//        return burgerlijkeStaat;
//    }
//
//    public void setBurgerlijkeStaat(BurgerlijkeStaat burgerlijkeStaat) {
//        this.burgerlijkeStaat = burgerlijkeStaat;
//    }
//
//    public Set<OnderlingeRelatie> getOnderlingeRelaties() {
//        if (onderlingeRelaties == null) {
//            onderlingeRelaties = new HashSet<OnderlingeRelatie>();
//        }
//        return onderlingeRelaties;
//    }
//
//    public void setOnderlingeRelaties(Set<OnderlingeRelatie> onderlingeRelaties) {
//        this.onderlingeRelaties = onderlingeRelaties;
//    }

    //    public Set<Bedrijf> getBedrijven() {
    //        if (bedrijven == null) {
    //            bedrijven = new HashSet<>();
    //        }
    //        return bedrijven;
    //    }
    //
    //    public void setBedrijven(Set<Bedrijf> bedrijven) {
    //        this.bedrijven = bedrijven;
    //    }

//    public Set<Hypotheek> getHypotheken() {
//        if (hypotheken == null) {
//            hypotheken = new HashSet<Hypotheek>();
//        }
//        return hypotheken;
//    }
//
//    public void setHypotheken(Set<Hypotheek> hypotheken) {
//        this.hypotheken = hypotheken;
//    }
//
//    public Set<HypotheekPakket> getHypotheekPakketten() {
//        if (hypotheekPakketten == null) {
//            hypotheekPakketten = new HashSet<HypotheekPakket>();
//        }
//        return hypotheekPakketten;
//    }
//
//    public void setHypotheekPakketten(Set<HypotheekPakket> hypotheekPakketten) {
//        this.hypotheekPakketten = hypotheekPakketten;
//    }

    public String getName() {
        return this.getIdentificatie();
    }

}
