package ru.mifodiy67.project.dao;

import ru.mifodiy67.project.model.Box;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import static ru.mifodiy67.project.util.ParsingConst.DB_URL;
import static ru.mifodiy67.project.util.ParsingConst.JDBC_DRIVER;
import static ru.mifodiy67.project.util.ParsingConst.PASS;
import static ru.mifodiy67.project.util.ParsingConst.USER;

public class BoxDao {

    private static final String SAVE_BOXES = "INSERT INTO Box(id, CONTAINED_IN) VALUES (?, ?)";

    public void saveAll(Set<Box> boxes) throws ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = connection.prepareStatement(SAVE_BOXES)) {
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
}
