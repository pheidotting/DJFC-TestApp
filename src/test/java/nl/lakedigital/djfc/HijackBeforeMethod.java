package nl.lakedigital.djfc;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

public class HijackBeforeMethod implements MethodInterceptor {
    private static Logger LOGGER = null;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        for (Annotation annotation : methodInvocation.getMethod().getDeclaredAnnotations()) {
            if (annotation instanceof TestCaseDJFC) {
                LOGGER = LoggerFactory.getLogger(methodInvocation.getThis().getClass());

                TestCaseDJFC
                 annotatie=(TestCaseDJFC)annotation;

                LOGGER.debug("{} - {}",annotatie.value().getValue(), annotatie.value().getOmschrijving());

//                LOGGER.debug(getOmschrijving(annotatie.value().getValue()));
            }
        }

        return methodInvocation.proceed();
    }


    //http://jira.djfc.local:8080/browse/DJFC-40

    private String getOmschrijving(String nummer){
        String adres = "http://jira.djfc.local:8080/browse/" + nummer;

        LOGGER.info("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource( adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").header("username", "admin").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        String a = response.getEntity(String.class);

        return a;
    }
}