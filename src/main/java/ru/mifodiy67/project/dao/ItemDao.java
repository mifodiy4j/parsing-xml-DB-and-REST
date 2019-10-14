package ru.mifodiy67.project.dao;

import ru.mifodiy67.project.model.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ru.mifodiy67.project.util.ParsingConst.DB_URL;
import static ru.mifodiy67.project.util.ParsingConst.JDBC_DRIVER;
import static ru.mifodiy67.project.util.ParsingConst.PASS;
import static ru.mifodiy67.project.util.ParsingConst.USER;

public class ItemDao {

    private static final String SAVE_ITEMS = "INSERT INTO Item(id, CONTAINED_IN, COLOR) VALUES (?, ?, ?)";
    private static final String GET_ITEMS_IN_BOX = "SELECT ITEM.ID FROM ITEM LEFT JOIN BOX ON ITEM.CONTAINED_IN=BOX" +
            ".ID WHERE COLOR=? AND (BOX.ID=? OR BOX.ID IN (WITH LINK(ID) AS (\n" +
            "    SELECT ID FROM BOX WHERE CONTAINED_IN=?\n" +
            "    UNION ALL\n" +
            "    SELECT BOX.ID\n" +
            "    FROM LINK INNER JOIN BOX ON LINK.ID = BOX.CONTAINED_IN\n" +
            ")\n" +
            "SELECT ID FROM LINK ORDER BY ID))";

    public void saveAll(Set<Item> items) throws ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = connection.prepareStatement(SAVE_ITEMS)) {
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

    public List<Integer> getItemsInBoxWithColor(String color, int boxId) throws ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        List<Integer> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = connection.prepareStatement(GET_ITEMS_IN_BOX)) {
            stmt.setString(1, color);
            stmt.setInt(2, boxId);
            stmt.setInt(3, boxId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
