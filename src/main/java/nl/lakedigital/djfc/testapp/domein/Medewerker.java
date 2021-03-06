package nl.lakedigital.djfc.testapp.domein;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GEBRUIKER")
@DiscriminatorValue(value = "M")
@AttributeOverrides({ @AttributeOverride(name = "identificatie", column = @Column(name = "GEBRUIKERSNAAM")) })
//@NamedQueries({ @NamedQuery(name = "Medewerker.zoekOpEmail", query = "select m from Medewerker m where m.identificatie = :emailadres") })
public class Medewerker extends Gebruiker implements Serializable {
    private static final long serialVersionUID = -4313251874716582151L;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true, targetEntity = Kantoor.class)
    @JoinColumn(name = "KANTOOR")
    private Kantoor kantoor;

    public Kantoor getKantoor() {
        return kantoor;
    }

    public void setKantoor(Kantoor kantoor) {
        this.kantoor = kantoor;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Medewerker [getVoornaam()=");
        builder.append(getVoornaam());
        builder.append(", getTussenvoegsel()=");
        builder.append(getTussenvoegsel());
        builder.append(", getAchternaam()=");
        builder.append(getAchternaam());
        builder.append(", getId()=");
        builder.append(getId());
        builder.append(", getIdentificatie()=");
        builder.append(getIdentificatie());
        builder.append(", getWachtwoord()=");
        builder.append(getWachtwoord());
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append(", hashCode()=");
        builder.append(hashCode());
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }

}
