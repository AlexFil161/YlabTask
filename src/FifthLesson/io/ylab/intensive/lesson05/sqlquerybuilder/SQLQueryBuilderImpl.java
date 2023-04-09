package FifthLesson.io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {

    private DataSource dataSource;

    @Autowired
    public SQLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public String queryForTable(String tableName) throws SQLException {
        try {
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            ResultSet result = metaData.getColumns(null, null, tableName, "%");

            if(!result.next()) {
                return null;
            }

            List<String> columns = new ArrayList<>();
            while(result.next()) {
                columns.add(result.getString("COLUMN_NAME"));
            }

            return "SELECT " + String.join(", ", columns) + " FROM " + tableName;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getTables() throws SQLException {
        try {
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            ResultSet result = metaData.getTables(null, null, null, null);

            List<String> tables = new ArrayList<>();
            while (result.next()) {
                tables.add(result.getString("TABLE_NAME"));
            }

            return tables;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
