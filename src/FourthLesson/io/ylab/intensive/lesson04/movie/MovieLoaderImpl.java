package FourthLesson.io.ylab.intensive.lesson04.movie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import javax.sql.DataSource;

public class MovieLoaderImpl implements MovieLoader {
  private DataSource dataSource;

  public MovieLoaderImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void loadData(File file) {
    String insertSql = "INSERT INTO movie (year, length, title, subject, actors, actress, director, popularity, awards) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try(Connection connection = dataSource.getConnection();
        PreparedStatement statment = connection.prepareStatement(insertSql);
        BufferedReader lineReader = new BufferedReader(new FileReader(file))) {

      lineReader.readLine();
      lineReader.readLine();

      while (lineReader.ready()) {
        String line = lineReader.readLine();
        String[] lineInfo = line.split(";");

        Movie movie = createMovie(lineInfo);

        statment.setObject(1, movie.getYear(), Types.INTEGER);
        statment.setObject(2, movie.getLength(), Types.INTEGER);
        statment.setObject(3, movie.getTitle(), Types.VARCHAR);
        statment.setObject(4, movie.getSubject(), Types.VARCHAR);
        statment.setObject(5, movie.getActors(), Types.VARCHAR);
        statment.setObject(6, movie.getActress(), Types.VARCHAR);
        statment.setObject(7, movie.getDirector(), Types.VARCHAR);
        statment.setObject(8, movie.getPopularity(), Types.INTEGER);
        statment.setObject(9, movie.getAwards(), Types.BOOLEAN);

        statment.executeUpdate();
      }

    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  private Movie createMovie(String[] lineInfo) {
    Movie movie = new Movie();

    movie.setYear(lineInfo[0].equals("") ? null : Integer.parseInt(lineInfo[0]));
    movie.setLength(lineInfo[1].equals("") ? null : Integer.parseInt(lineInfo[1]));
    movie.setTitle(lineInfo[2].equals("") ? null : lineInfo[2]);
    movie.setSubject(lineInfo[3].equals("") ? null : lineInfo[3]);
    movie.setActors(lineInfo[4].equals("") ? null : lineInfo[4]);
    movie.setActress(lineInfo[5].equals("") ? null : lineInfo[5]);
    movie.setDirector(lineInfo[6].equals("") ? null : lineInfo[6]);
    movie.setPopularity(lineInfo[7].equals("") ? null : Integer.parseInt(lineInfo[7]));
    movie.setAwards(lineInfo[8].equals("") ? null : Boolean.valueOf(lineInfo[8]));

    return movie;
  }
}
