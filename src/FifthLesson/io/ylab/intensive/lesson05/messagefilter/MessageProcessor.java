package FifthLesson.io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MessageProcessor {

    private ConnectionFactory connectionFactory;
    private DataSource dataSource;

    private static final String QUEUE_NAME_INPUT = "input";
    private static final String QUEUE_NAME_OUTPUT = "output";

    @Autowired
    public MessageProcessor(ConnectionFactory connectionFactory, DataSource dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    public void loadData(File file) {
        String insertSql = "INSERT INTO bad_words (word_meaning) VALUES (?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(insertSql);
            BufferedReader lineReader = new BufferedReader(new FileReader(file))) {

            while(lineReader.ready()) {
                String line = lineReader.readLine();
                statement.setString(1, line);
                statement.executeUpdate();
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void messageHandler() {
        try(com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME_INPUT, true, false, false, null);
            channel.queueDeclare(QUEUE_NAME_OUTPUT, true, false, false, null);

            while (!Thread.currentThread().isInterrupted()) {
                GetResponse input = channel.basicGet(QUEUE_NAME_INPUT, true);
                if(input != null) {
                    String received = new String(input.getBody());
                    String result = checkingMessage(received);
                    System.out.println(result);
                    channel.basicPublish("", QUEUE_NAME_OUTPUT, null, result.getBytes());
                } else {
                    System.err.println("Сообщений нет.");
                    Thread.sleep(1000);
                }
            }

        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String checkingMessage(String input) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM bad_words WHERE UPPER(word_meaning) LIKE UPPER(?)")) {

//            Pattern pattern = Pattern.compile("(?<=\\W)|(?=\\W)|\\\\s+");
//            Matcher matcher = pattern.matcher(input);
            String[] words = input.split("[\\s.,;!?$]+");
            StringBuilder output = new StringBuilder();
            for (String word : words) {
                statement.setString(1, word);
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next() && word.equalsIgnoreCase(result.getString(1))) {
                        output.append(censored(word));
                    } else {
                        output.append(word);
                    }
                }
            }
            return output.toString();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String censored(String censor) {
        StringBuilder result = new StringBuilder();
        result.append(censor.charAt(0));
        for (int i = 1; i < censor.length() - 1; i++) {
            result.append("*");
        }
        result.append(censor.charAt(censor.length() - 1));
        return result.toString();
    }

}
