package FourthLesson.io.ylab.intensive.lesson04.eventsourcing.api;

import FourthLesson.io.ylab.intensive.lesson04.RabbitMQUtil;
import com.rabbitmq.client.ConnectionFactory;
import FourthLesson.io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ApiApp {
  public static void main(String[] args) throws Exception {
    ConnectionFactory connectionFactory = initMQ();
    DataSource dataSource = initDb();

    PersonApiImpl personApi = new PersonApiImpl(connectionFactory, dataSource);
    personApi.savePerson(1L, "Emily", "Elizabeth", "Johnson");
    Thread.sleep(1000);
    personApi.deletePerson(1L);
    personApi.savePerson(2L, "Benjamin", "James", "Parker");
    personApi.savePerson(3L, "Samantha", "Grace", "Thompson");
    Thread.sleep(1000);
    personApi.savePerson(3L, "William", "Edward", "Robinson");
    System.out.println(personApi.findPerson(2L));
    System.out.println(personApi.findPerson(1L));
    personApi.savePerson(4L, "Victoria", "Louise", "Adams");
    Thread.sleep(1000);
    System.out.println(personApi.findAll());
  }

  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }

  private static DataSource initDb() throws SQLException {
    String ddl = ""
            + "drop table if exists person;"
            + "create table if not exists person (\n"
            + "person_id bigint primary key,\n"
            + "first_name varchar,\n"
            + "last_name varchar,\n"
            + "middle_name varchar\n"
            + ")";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(ddl, dataSource);
    return dataSource;
  }
}
