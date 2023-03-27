package FourthLesson.io.ylab.intensive.lesson04.filesort;

import java.io.*;
import java.sql.*;
import javax.sql.DataSource;

public class FileSortImpl implements FileSorter {
  private DataSource dataSource;
  private static final int BATCH_SIZE = 1000;

  public FileSortImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public File sort(File data) {
    try {
      fillingDB(data);
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
    try {
      return fillingFileSortedData(data);
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
    return data;
  }

  private void fillingDB(File data) throws SQLException, IOException {
    try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO numbers (val) VALUES (?)");
        BufferedReader lineReader = new BufferedReader(new FileReader(data))) {

      int count = 0;
      while (lineReader.ready()) {
        String line = lineReader.readLine();
        statement.setLong(1, Long.parseLong(line));
        statement.addBatch();
        if (++count % BATCH_SIZE == 0) {
          statement.executeBatch();
        }
      }

      if (count % BATCH_SIZE != 0) {
        statement.executeBatch();
      }
    }
  }

  private File fillingFileSortedData(File data) throws SQLException, IOException {
    File sortedFile = new File(data.getParent(), "sorted_" + data.getName());
    try(Connection connection = dataSource.getConnection();
        Statement statementConnect = connection.createStatement();
        ResultSet resultSQL = statementConnect.executeQuery("SELECT val FROM numbers ORDER BY val DESC");
        BufferedWriter lineWriter = new BufferedWriter(new FileWriter(sortedFile))) {


      while (resultSQL.next()) {
        lineWriter.write(Long.toString(resultSQL.getLong("val")));
        lineWriter.write('\n');
      }
      lineWriter.flush();
    }
    return sortedFile;
  }
}