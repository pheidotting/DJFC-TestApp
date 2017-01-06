package nl.lakedigital.djfc.testapp.helpers;

import com.google.common.base.Function;
import nl.lakedigital.djfc.testapp.testcases.AbstractTestCase;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.transform;

public class VerwijderPersoon extends AbstractTestCase {

    public void voeruit(Long id) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        ApplicationContext applicationContextOGA = new ClassPathXmlApplicationContext("classpath:applicationContextOGA.xml");
        sessionFactoryDJFC = (SessionFactory) applicationContext.getBean("sessionFactoryDJFC");
        sessionFactoryOGA = (SessionFactory) applicationContextOGA.getBean("sessionFactoryOGA");

        opruimen(id);
    }

    protected void opruimen(Long id) {
        Set<BigInteger> revs = new HashSet<>();
        Set<String> trackAndTraceIds = new HashSet<>();
        List<String> queries = new ArrayList<>();

        getTransactions();

        //OGA
        revs.addAll(transform(getSessionOGA().createSQLQuery("select rev from ADRES_AUD where entiteitid = " + id + " and soortentiteit = 'RELATIE'").list(), new ObjectToBigIntegerTransformer()));
        revs.addAll(transform(getSessionOGA().createSQLQuery("select rev from BIJLAGE_AUD where entiteitid = " + id + " and soortentiteit = 'RELATIE'").list(), new ObjectToBigIntegerTransformer()));
        revs.addAll(transform(getSessionOGA().createSQLQuery("select rev from OPMERKING_AUD where entiteitid = " + id + " and soortentiteit = 'RELATIE'").list(), new ObjectToBigIntegerTransformer()));
        revs.addAll(transform(getSessionOGA().createSQLQuery("select rev from REKENINGNUMMER_AUD where entiteitid = " + id + " and soortentiteit = 'RELATIE'").list(), new ObjectToBigIntegerTransformer()));
        revs.addAll(transform(getSessionOGA().createSQLQuery("select rev from TELEFOONNUMMER_AUD where entiteitid = " + id + " and soortentiteit = 'RELATIE'").list(), new ObjectToBigIntegerTransformer()));

        queries.add("delete from ADRES where entiteitid = " + id + " and soortentiteit = 'RELATIE'");
        queries.add("delete from ADRES_AUD where entiteitid = " + id + " and soortentiteit = 'RELATIE'");
        queries.add("delete from BIJLAGE where entiteitid = " + id + " and soortentiteit = 'RELATIE'");
        queries.add("delete from BIJLAGE_AUD where entiteitid = " + id + " and soortentiteit = 'RELATIE'");
        queries.add("delete from OPMERKING where entiteitid = " + id + " and soortentiteit = 'RELATIE'");
        queries.add("delete from OPMERKING_AUD where entiteitid = " + id + " and soortentiteit = 'RELATIE'");
        queries.add("delete from REKENINGNUMMER where entiteitid = " + id + " and soortentiteit = 'RELATIE'");
        queries.add("delete from REKENINGNUMMER_AUD where entiteitid = " + id + " and soortentiteit = 'RELATIE'");
        queries.add("delete from TELEFOONNUMMER where entiteitid = " + id + " and soortentiteit = 'RELATIE'");
        queries.add("delete from TELEFOONNUMMER_AUD where entiteitid = " + id + " and soortentiteit = 'RELATIE'");
        for (BigInteger rev : revs) {
            if (rev != null) {
                trackAndTraceIds.add((String) getSessionOGA().createSQLQuery("select trackandtraceid from REVINFO where id = " + rev).uniqueResult());
                queries.add("delete from REVINFO where id = " + rev);
            }
        }

        for (String query : queries) {
            System.out.println(query);
            getSessionOGA().createSQLQuery(query).executeUpdate();
        }


        revs = new HashSet<>();
        queries = new ArrayList<>();

        //DJFC
        revs.addAll(transform(getSessionDJFC().createSQLQuery("select rev from GEBRUIKER_AUD where id = " + id).list(), new ObjectToBigIntegerTransformer()));
        revs.addAll(transform(getSessionDJFC().createSQLQuery("select rev from POLIS_AUD where relatie = " + id).list(), new ObjectToBigIntegerTransformer()));

        queries.add("delete from GEBRUIKER where id = " + id);
        queries.add("delete from GEBRUIKER_AUD where id = " + id);
        queries.add("delete from POLIS where relatie = " + id);
        queries.add("delete from POLIS_AUD where relatie = " + id);
        for (BigInteger rev : revs) {
            if (rev != null) {
                trackAndTraceIds.add((String) getSessionDJFC().createSQLQuery("select trackandtraceid from REVINFO where id = " + rev).uniqueResult());

                queries.add("delete from REVINFO where id = " + rev);
            }
        }

        for (String trackAndTraceId : trackAndTraceIds) {
            queries.add("delete from INKOMENDREQUEST where trackandtraceid = '" + trackAndTraceId + "'");
        }

        for (String query : queries) {
            System.out.println(query);
            getSessionDJFC().createSQLQuery(query).executeUpdate();
        }

        commit();
    }

    public class ObjectToBigIntegerTransformer implements Function<Object, BigInteger> {
        @Override
        public BigInteger apply(Object o) {
            if (o != null) {
                return new BigInteger(o.toString());
            }

            return new BigInteger("0");
        }
    }

    @Override
    public void initTestStappen() {

    }
}
