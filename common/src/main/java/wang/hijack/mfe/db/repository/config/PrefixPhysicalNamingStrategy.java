package wang.hijack.mfe.db.repository.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.stereotype.Component;

/**
 * @author Jack
 */
@Component
public class PrefixPhysicalNamingStrategy extends SpringPhysicalNamingStrategy {
    @Value("${prefix.table:mfe}")
    private String tablePrefix;
    @Value("${prefix.sequence:mfe}")
    private String sequencePrefix;

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        Identifier identifier = super.toPhysicalTableName(name, jdbcEnvironment);
        return getIdentifier(tablePrefix, identifier, jdbcEnvironment);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        Identifier identifier = super.toPhysicalTableName(name, jdbcEnvironment);
        return getIdentifier(sequencePrefix, identifier, jdbcEnvironment);
    }

    private Identifier getIdentifier(String prefix, Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        String name = String.format("%s_%s", prefix, identifier.getText());
        return getIdentifier(name, identifier.isQuoted(), jdbcEnvironment);
    }
}
