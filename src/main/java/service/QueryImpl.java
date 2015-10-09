package service;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Oleg Dashkevych on 15.05.2015.
 */
public class QueryImpl {

    public List<HashMap> executeQuery(String query) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SQLQuery sqlQuery = session.createSQLQuery(query);
        List<HashMap> result = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return result;
    }
}
