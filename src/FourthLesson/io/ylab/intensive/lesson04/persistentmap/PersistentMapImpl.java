package FourthLesson.io.ylab.intensive.lesson04.persistentmap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Класс, методы которого надо реализовать 
 */
public class PersistentMapImpl implements PersistentMap {

  private DataSource dataSource;
  private String mapName;

  public PersistentMapImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void init(String name) {
    this.mapName = name;
  }

  @Override
  public boolean containsKey(String key) throws SQLException {
    try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT VALUE FROM persistent_map WHERE map_name=? AND KEY=?")) {

      statement.setString(1, mapName);
      statement.setString(2, key);

      try(ResultSet result = statement.executeQuery()) {
        return result.next();
      }
    }
  }

  @Override
  public List<String> getKeys() throws SQLException {
    try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT KEY FROM persistent_map WHERE map_name=?")) {

      statement.setString(1, mapName);

      try(ResultSet result = statement.executeQuery()) {
        List<String> keys =new ArrayList<>();
        while (result.next()) {
          keys.add(result.getString(1));
        }
        return keys;
      }
    }
  }

  @Override
  public String get(String key) throws SQLException {
    try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT value FROM persistent_map WHERE map_name=? AND KEY=?")) {

      statement.setString(1, mapName);
      statement.setString(2, key);

      try(ResultSet result = statement.executeQuery()) {
        if(result.next()) {
          return result.getString(1);
        } else {
          return null;
        }
      }
    }
  }

  @Override
  public void remove(String key) throws SQLException {
    try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM persistent_map WHERE map_name=? AND KEY=?")) {

      statement.setString(1, mapName);
      statement.setString(2, key);
      statement.executeUpdate();
    }
  }

  @Override
  public void put(String key, String value) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      connection.setAutoCommit(false);
      try {
        try (PreparedStatement deleteStmt = connection.prepareStatement(
                "DELETE FROM persistent_map WHERE map_name=? AND key=?")) {
          deleteStmt.setString(1, mapName);
          deleteStmt.setString(2, key);
          deleteStmt.executeUpdate();
        }

        try (PreparedStatement insertStmt = connection.prepareStatement(
                "INSERT INTO persistent_map (map_name, key, value) VALUES (?, ?, ?)")) {
          insertStmt.setString(1, mapName);
          insertStmt.setString(2, key);
          insertStmt.setString(3, value);
          insertStmt.executeUpdate();
        }
        connection.commit();
      } catch (SQLException ex) {
        connection.rollback();
        ex.printStackTrace();
      } finally {
        connection.setAutoCommit(true);
      }
    }
  }

  @Override
  public void clear() throws SQLException {
    try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM persistent_map WHERE map_name=?")) {

      statement.setString(1, mapName);
      statement.executeUpdate();
    }
  }
}
