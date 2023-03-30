package FourthLesson.io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import FourthLesson.io.ylab.intensive.lesson04.DbUtil;

public class PersistenceMapTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);

    persistentMap.init("TestMap");

    persistentMap.put("KeyOne", "ValueOne");
    persistentMap.put("KeyTwo", "ValueTwo");
    persistentMap.put("KeyThree", "ValueThree");

    String value = persistentMap.get("KeyOne");
    System.out.println(value);

    System.out.println(persistentMap.containsKey("KeyOne"));

    List<String> keys= persistentMap.getKeys();
    System.out.println(keys);

    persistentMap.remove("KeyOne");
    keys = persistentMap.getKeys();
    System.out.println(keys);

    persistentMap.clear();
    keys = persistentMap.getKeys();
    System.out.println(keys);
  }
  
  public static DataSource initDb() throws SQLException {
    String createMapTable = "" 
                                + "drop table if exists persistent_map; " 
                                + "CREATE TABLE if not exists persistent_map (\n"
                                + "   map_name varchar,\n"
                                + "   KEY varchar,\n"
                                + "   value varchar\n"
                                + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createMapTable, dataSource);
    return dataSource;
  }
}
