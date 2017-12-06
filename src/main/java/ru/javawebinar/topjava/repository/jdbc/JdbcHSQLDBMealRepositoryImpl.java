package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Profile(Profiles.HSQL_DB)
public class JdbcHSQLDBMealRepositoryImpl extends JdbcMealRepositoryImpl {

    public JdbcHSQLDBMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate,
                                        NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Timestamp convertDate(LocalDateTime date) {
        return Timestamp.valueOf(date);
    }
}
