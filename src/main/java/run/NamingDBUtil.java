package run;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;

/**
 * Created by Oleg Dashkevych on 25.05.2015.
 */
public class NamingDBUtil extends ImprovedNamingStrategy implements NamingStrategy {

    @Override
    public String columnName(String columnName) {
        return addUnderscores(columnName).toUpperCase();
    }

    @Override
    public String tableName(String tableName) {
        return addUnderscores(tableName).toUpperCase();
    }
    @Override
    public String classToTableName(String className) {
        return "T_" + tableName(className);
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        return addUnderscores(propertyName).toUpperCase();
    }
}
