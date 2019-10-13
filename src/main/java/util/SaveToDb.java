package util;

import model.Box;
import model.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class SaveToDb {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";

    private static final String USER = "sa";
    private static final String PASS = "";

    private static final String SAVE_BOX = "INSERT INTO Box(id, CONTAINED_IN) VALUES (?, ?)";
    private static final String SAVE_ITEM = "INSERT INTO Item(id, CONTAINED_IN, COLOR) VALUES (?, ?, ?)";

    public void boxSave(Set<Box> boxes) throws ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = connection.prepareStatement(SAVE_BOX)) {
            int count = 0;
            for (Box box : boxes) {
                stmt.setInt(1, box.getId());
                if (box.getContainedIn() != null) {
                    stmt.setInt(2, box.getContainedIn());
                } else {
                    stmt.setString(2, null);
                }

                stmt.addBatch();
                if (count % 100 == 0 || count == boxes.size()) {
                    stmt.executeBatch();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void itemSave(Set<Item> items) throws ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = connection.prepareStatement(SAVE_ITEM)) {
            int count = 0;
            for (Item item : items) {
                stmt.setInt(1, item.getId());
                if (item.getContainedIn() != null) {
                    stmt.setInt(2, item.getContainedIn());
                } else {
                    stmt.setString(2, null);
                }
                stmt.setString(3, item.getCololr());

                stmt.addBatch();
                if (count % 100 == 0 || count == items.size()) {
                    stmt.executeBatch();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
