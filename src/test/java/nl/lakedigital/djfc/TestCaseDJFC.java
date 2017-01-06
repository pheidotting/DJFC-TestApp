package nl.lakedigital.djfc;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface TestCaseDJFC {

    public enum Case{
        DJFC40("Verschijnen, bij het invoeren van een Bedrijf, de juiste foutmeldingen als niet alle verplichte velden worden gevuld?"),
        DJFC41("Kan ik, meteen bij het invoeren van een Bedrijf, een adres toevoegen?"),
        DJFC42("Zijn de foutmeldingen over verplichte velden weg als ik deze vul bij het invoeren van een nieuw Bedrijf?"),
        DJFC47("Kan ik, meteen bij het invoeren van een Bedrijf, een telefoonnummer toevoegen?"),
        DJFC48("Kan ik, meteen bij het invoeren van een Bedrijf, een contactpersoon (incl hun telefoonnummer) toevoegen?"),
        DJFC49("Kan ik, meteen bij het invoeren van een Bedrijf, een opmerking toevoegen?");

        private String omschrijving;
        private Case(String omschrijving){this.omschrijving=omschrijving;}

        public String getOmschrijving() {
            return omschrijving;
        }

        public  String getValue(){
            return this.name().replace("DJFC","DJFC-");
        }
    }

    Case value();
}
