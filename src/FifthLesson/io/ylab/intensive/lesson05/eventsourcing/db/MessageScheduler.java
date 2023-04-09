package FifthLesson.io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeoutException;

@Component
public class MessageScheduler {

    private ConnectionFactory connectionFactory;
    private DataSource dataSource;

    @Autowired
    public MessageScheduler(ConnectionFactory connectionFactory, DataSource dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    public void messageProcessor() {
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
        } catch (IOException | TimeoutException | SQLException e) {
            e.printStackTrace();
        }
    }
}
