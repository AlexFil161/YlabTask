package FifthLesson.io.ylab.intensive.lesson05.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import FifthLesson.io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Component
public class PersonApiImpl implements PersonApi {

    private ConnectionFactory connectionFactory;
    private DataSource dataSource;

    private static final String EXCHANGE_NAME = "person-exchange";
    private static final String QUEUE_NAME = "person-queue";

    @Autowired
    public PersonApiImpl(ConnectionFactory connectionFactory, DataSource dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) {
        if(dataCheck(personId)) {
            try(Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()) {

                channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
                channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*");
                String message = "DELETE FROM person WHERE person_id=" + personId;
                channel.basicPublish(EXCHANGE_NAME, "*", null, message.getBytes());

            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Была соверешена попытка удаления данных, но данные не найдены!");
        }
    }

    private boolean dataCheck(Long personId) {
        try(java.sql.Connection connectionDB = dataSource.getConnection();
            PreparedStatement statement = connectionDB.prepareStatement("SELECT * FROM person WHERE person_id=?")) {

            statement.setLong(1, personId);
            try(ResultSet result = statement.executeQuery()){
                return result.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        if(dataCheck(personId)) {
            try(java.sql.Connection connectionDB = dataSource.getConnection();
                PreparedStatement statement = connectionDB.prepareStatement("UPDATE person SET first_name=?, last_name=?, middle_name=? WHERE person_id=?")) {

                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, middleName);
                statement.setLong(4, personId);
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            try(Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()) {

                channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
                channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*");
                String message = "INSERT INTO person (person_id, first_name, last_name, middle_name) VALUES ("
                        + personId + ", " + "'" + firstName +"'" + ", " + "'"
                        + lastName+ "'" + ", " + "'" + middleName + "'"+ ")";
                channel.basicPublish(EXCHANGE_NAME, "*", null, message.getBytes());


            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Person findPerson(Long personId) {
        try(java.sql.Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE person_id=?")) {

            statement.setLong(1, personId);
            try(ResultSet result = statement.executeQuery()) {
                if(result.next()) {
                    return new Person(result.getLong("person_id"), result.getString("first_name"),
                            result.getString("last_name"), result.getString("middle_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        try(java.sql.Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person");
            ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                persons.add(new Person(result.getLong("person_id"), result.getString("first_name"),
                        result.getString("last_name"), result.getString("middle_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }
}
