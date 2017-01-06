package nl.lakedigital.djfc.testapp.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import nl.lakedigital.djfc.commons.json.*;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class DJFCClient {
    protected final static Logger LOGGER = LoggerFactory.getLogger(DJFCClient.class);

    //    private final String URL_BASIS_DJFC = "http://192.168.91.215:8080";
    private final String URL_BASIS_DJFC = "http://localhost:8080";
    private final String URL_RELATIE_LEES = URL_BASIS_DJFC + "/dejonge/rest/medewerker/gebruiker/lees?id=";
    private final String URL_RELATIE_OPSLAAN = URL_BASIS_DJFC + "/dejonge/rest/medewerker/gebruiker/opslaan";

    private final String URL_ADRES_OPSLAAN = URL_BASIS_DJFC + "/dejonge/rest/medewerker/adres/opslaan";
    private final String URL_ADRES_LEES = URL_BASIS_DJFC + "/dejonge/rest/medewerker/adres/alles";
    private final String URL_TELEFOONNUMMER_OPSLAAN = URL_BASIS_DJFC + "/dejonge/rest/medewerker/telefoonnummer/opslaan";
    private final String URL_TELEFOONNUMMER_LEES = URL_BASIS_DJFC + "/dejonge/rest/medewerker/telefoonnummer/alles";
    private final String URL_REKENINGNUMMER_OPSLAAN = URL_BASIS_DJFC + "/dejonge/rest/medewerker/rekeningnummer/opslaan";
    private final String URL_REKENINGNUMMER_LEES = URL_BASIS_DJFC + "/dejonge/rest/medewerker/rekeningnummer/alles";
    private final String URL_POLIS_OPSLAAN = URL_BASIS_DJFC + "/dejonge/rest/medewerker/polis/opslaan";
    private final String URL_POLIS_LEES = URL_BASIS_DJFC + "/dejonge/rest/medewerker/polis/lees?id=";
    private final String URL_ALLE_PARTICULIERE_POLIS_SOORTEN = URL_BASIS_DJFC +"/dejonge/rest/medewerker/polis/alleParticulierePolisSoorten";
    private final String URL_ALLE_PARTICULIERE_ZAKELIJKE_SOORTEN = URL_BASIS_DJFC +"/dejonge/rest/medewerker/polis/alleZakelijkePolisSoorten";

    private GsonBuilder builder = new GsonBuilder();
    protected Gson gson = new Gson();

    public void opslaanRelatie(JsonRelatie relatie, Long medewerkerId, String sessieCode, String trackAndTraceId) {
        String result = aanroepenUrlPost(URL_RELATIE_OPSLAAN, relatie, medewerkerId, trackAndTraceId, sessieCode);

        String id = result.substring(result.indexOf("foutmelding") + 14);
        id = id.substring(0, id.indexOf("\""));

        relatie.setId(Long.valueOf(id));
    }

    public JsonRelatie leesRelatie(Long id, Long medewerkerId, String sessieCode){
        return uitvoerenGet(URL_RELATIE_LEES+id.toString(),JsonRelatie.class,medewerkerId,sessieCode);
    }

    public void opslaanAdres(List<JsonAdres> adressen, Long medewerkerId, String sessieCode, String trackAndTraceId) {
        aanroepenUrlPost(URL_ADRES_OPSLAAN, adressen, medewerkerId, trackAndTraceId, sessieCode);
    }

    public void opslaanTelefoonnummer(JsonTelefoonnummer telefoonnummer, Long medewerkerId, String sessieCode, String trackAndTraceId) {
        aanroepenUrlPost(URL_TELEFOONNUMMER_OPSLAAN, newArrayList(telefoonnummer), medewerkerId, trackAndTraceId, sessieCode);
    }
    public List<JsonTelefoonnummer> leesTelefoonnummer(SoortEntiteit soortEntiteit, Long entiteitId, String sessie) {
        Type type = new TypeToken<ArrayList<JsonTelefoonnummer>>() {
        }.getType();

        return uitvoerenGetLijst(URL_TELEFOONNUMMER_LEES, JsonTelefoonnummer.class, type, sessie, soortEntiteit.name(), entiteitId.toString());
    }

    public void opslaanRekeningnummer(JsonRekeningNummer rekeningNummer, Long medewerkerId, String sessieCode, String trackAndTraceId) {
        aanroepenUrlPost(URL_REKENINGNUMMER_OPSLAAN, newArrayList(rekeningNummer), medewerkerId, trackAndTraceId, sessieCode);
    }
    public List<JsonRekeningNummer> leesRekeningnummer(SoortEntiteit soortEntiteit, Long entiteitId, String sessie) {
        Type type = new TypeToken<ArrayList<JsonRekeningNummer>>() {
        }.getType();

        return uitvoerenGetLijst(URL_REKENINGNUMMER_LEES, JsonRekeningNummer.class, type, sessie, soortEntiteit.name(), entiteitId.toString());
    }

    public void opslaanPolis(JsonPolis polis, Long medewerkerId, String sessieCode, String trackAndTraceId) {
        aanroepenUrlPost(URL_POLIS_OPSLAAN, polis, medewerkerId, trackAndTraceId, sessieCode);
    }
    public JsonPolis leesPolis(Long id, Long medewerkerId, String sessieCode){
        return uitvoerenGet(URL_POLIS_LEES+id.toString(),JsonPolis.class,medewerkerId,sessieCode);
    }

    public List<String> alleParticulierePolisSoorten(Long medewerkerId, String sessieCode) {
        String ret = uitvoerenGet(URL_ALLE_PARTICULIERE_POLIS_SOORTEN,medewerkerId,sessieCode);

        return new Gson().fromJson(ret, List.class);
    }

    public List<String> alleZakelijkePolisSoorten(Long medewerkerId, String sessieCode) {
        String ret = uitvoerenGet(URL_ALLE_PARTICULIERE_ZAKELIJKE_SOORTEN,medewerkerId,sessieCode);

        return new Gson().fromJson(ret, List.class);
    }

    public List<JsonAdres> leesAdres(SoortEntiteit soortEntiteit, Long entiteitId, String sessie) {
        Type type = new TypeToken<ArrayList<JsonAdres>>() {
        }.getType();

        return uitvoerenGetLijst(URL_ADRES_LEES, JsonAdres.class, type, sessie, soortEntiteit.name(), entiteitId.toString());
    }

    protected String aanroepenUrlPost(String adres, Object object, Long ingelogdeGebruiker, String trackAndTraceId) {
        Gson gson = builder.create();

        Client client = Client.create();

        WebResource webResource = client.resource(adres);
        String verstuurObject = gson.toJson(object);
        LOGGER.info("Versturen {} naar {}", verstuurObject, adres);

        ClientResponse cr = webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).header("trackAndTraceId", trackAndTraceId).post(ClientResponse.class, verstuurObject);

        return cr.getEntity(String.class);
    }

    protected String aanroepenUrlPost(String adres, Object object, Long ingelogdeGebruiker, String trackAndTraceId, String sessie) {
        Gson gson = builder.create();

        Client client = Client.create();

        client.addFilter(new ClientFilter() {

            @Override
            public ClientResponse handle(ClientRequest clientRequest) throws ClientHandlerException {
                clientRequest.getHeaders().putSingle("sessie", sessie);

                ClientResponse response = getNext().handle(clientRequest);


                return response;
            }
        });

        WebResource webResource = client.resource(adres);
        String verstuurObject = gson.toJson(object);
        LOGGER.info("Versturen {} naar {}", verstuurObject, adres);

        ClientResponse cr = webResource.accept("application/json").type("application/json").header("sessie", sessie).header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).header("trackAndTraceId", trackAndTraceId).post(ClientResponse.class, verstuurObject);

        return cr.getEntity(String.class);
    }

    protected void aanroepenUrlPostZonderBody(String adres, Long ingelogdeGebruiker, String trackAndTraceId, String... args) {
        Gson gson = builder.create();

        Client client = Client.create();

        if (args != null) {
            for (String arg : args) {
                adres = adres + "/" + arg;
            }
        }

        WebResource webResource = client.resource(adres);

        webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).header("trackAndTraceId", trackAndTraceId).post();
    }

    protected String uitvoerenGet(String adres,Long ingelogdeGebruiker,  String sessie) {
        //        adres = adres.replace("{{poort}}", bepaalPoort());
        LOGGER.info("Aanroepen via GET " + adres);
        System.out.println("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").header("sessie", sessie).header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        return response.getEntity(String.class);
    }

    protected <T> T uitvoerenGet(String adres, Class<T> clazz,Long ingelogdeGebruiker, String sessie, String... args) {
        LOGGER.debug("uitvoerenGet");

        Gson gson = builder.create();

        if (args != null) {
            for (String arg : args) {
                adres = adres + "/" + arg;
            }
        }
        LOGGER.info("Aanroepen via GET " + adres);
        System.out.println("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(adres);
        ClientResponse response;
        response = webResource.accept("application/json").type("application/json").header("sessie", sessie).header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        String result = response.getEntity(String.class);

        System.out.println(result);

        return gson.fromJson(result, clazz);
    }

    protected <T> List<T> uitvoerenGetLijst(String adres, Class<T> clazz, Type type,String sessie ,String... args) {
        Gson gson = builder.create();

        if (args != null) {
            for (String arg : args) {
                adres = adres + "/" + arg;
            }
        }
        LOGGER.info("Aanroepen via GET " + adres);
        System.out.println("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(adres);
        ClientResponse response = webResource.header("sessie", sessie).accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        //        Type listOfTestObject = new TypeToken<List<T>>() {
        //        }.getType();
        //        return gson.fromJson(response.getEntity(String.class), listOfTestObject);
        Type listType = type;
        List<T> yourClassList = new Gson().fromJson(response.getEntity(String.class), listType);

        return yourClassList;
    }

}
