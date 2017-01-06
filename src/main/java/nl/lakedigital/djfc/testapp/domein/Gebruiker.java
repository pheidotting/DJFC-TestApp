package nl.lakedigital.djfc.testapp.domein;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorColumn(name = "SOORT", length = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AttributeOverrides({@AttributeOverride(name = "identificatie", column = @Column(name = "GEBRUIKERSNAAM"))})
@NamedQueries({//
        @NamedQuery(name = "Gebruiker.zoekOpEmail", query = "select g from Gebruiker g where g.emailadres = :emailadres"), //
        @NamedQuery(name = "Gebruiker.zoekOpIdentificatie", query = "select g from Gebruiker g where g.identificatie = :identificatie"), //
        @NamedQuery(name = "Gebruiker.zoekOpSessieEnIpAdres", query = "select distinct g from Gebruiker g join g.sessies as s where s.sessie = :sessie and s.ipadres = :ipadres"), //
        @NamedQuery(name = "Gebruiker.zoekOpCookieCode", query = "select distinct g from Gebruiker g join g.sessies as s where s.cookieCode = :cookieCode"), //
        @NamedQuery(name = "Gebruiker.zoekOpNaam", query = "select g from Gebruiker g where g.voornaam like :naam or g.achternaam like :naam")//
})
public abstract class Gebruiker  {
    private static final long serialVersionUID = -643848502264838675L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "IDENTIFICATIE")
    private String identificatie;
    @Column(name = "WACHTWOORD")
    private String wachtwoord;
    @Column(name = "SALT")
    private String salt;
    @Column(name = "VOORNAAM")
    private String voornaam;
    @Column(name = "TUSSENVOEGSEL")
    private String tussenvoegsel;
    @Column(name = "ACHTERNAAM")
    private String achternaam;
    @Column(name = "EMAILADRES")
    private String emailadres;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "gebruiker", orphanRemoval = true, targetEntity = Sessie.class)
    private Set<Sessie> sessies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public Set<Sessie> getSessies() {
        if (sessies == null) {
            sessies = new HashSet<>();
        }
        return sessies;
    }

    public void setSessies(Set<Sessie> sessies) {
        this.sessies = sessies;
    }

}
