package nl.lakedigital.djfc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TestCase {

    public enum Case{
        Case1("Controleer foutmelding wanneer er een onbekende gebruikersnaam wordt ingevuld");

        private String omschrijving;
        private Case(String omschrijving){this.omschrijving=omschrijving;}

        public String getOmschrijving() {
            return omschrijving;
        }
    }

    Case testcase();
}
