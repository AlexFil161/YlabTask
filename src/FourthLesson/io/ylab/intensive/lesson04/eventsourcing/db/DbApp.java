package FourthLesson.io.ylab.intensive.lesson04.eventsourcing.db;

import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

import FourthLesson.io.ylab.intensive.lesson04.DbUtil;
import FourthLesson.io.ylab.intensive.lesson04.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

public class DbApp {
  public static void main(String[] args) throws Exception {
    DataSource dataSource = initDb();
    ConnectionFactory connectionFactory = initMQ();

    try(Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel()) {

      while (!Thread.currentThread().isInterrupted()) {
        GetResponse message = channel.basicGet("person-queue", true);
        if(message == null) {
          System.err.println("Сообщений нет.");
        } else {
          try(java.sql.Connection connectionDB = dataSource.getConnection();
              Statement statement = connectionDB.createStatement()) {

            String received = new String(message.getBody());
            statement.executeUpdate(received);
          }
        }
      }
    }
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
