package FifthLesson.io.ylab.intensive.lesson05.messagefilter;

import javax.sql.DataSource;

import com.rabbitmq.client.ConnectionFactory;
import FifthLesson.io.ylab.intensive.lesson05.DbUtil;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@ComponentScan("FifthLesson.io.ylab.intensive.lesson05.messagefilter")
public class Config {

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDatabaseName("postgres");
        dataSource.setPortNumber(5432);

        String ddl = ""
                + "drop table if exists bad_words;"
                + "create table if not exists bad_words (\n"
                + "\tword_meaning varchar\n"
                + ");";
        DbUtil.applyDdl(ddl, dataSource);

        return dataSource;
    }
}
