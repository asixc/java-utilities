package dev.jotxee.repository;

import dev.jotxee.file.FileLoader;
import dev.jotxee.model.Item;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.getenv;

public class EanRepository {
    private final static String GET_EANS = "/sql/selectEan.sql";
    private final static String INSERT_EANS = "/sql/insert_ean.sql";
    private static final Logger log = LoggerFactory.getLogger(EanRepository.class);
    private final String jdbcUrl;

    private final String username = getenv("DB_USERNAME");
    private final String password = getenv("DB_PASSWORD");

    public EanRepository(final String env) {
        this.jdbcUrl = getJdbcUrl(env);
        if (jdbcUrl == null || username == null || password == null) {
            throw new IllegalArgumentException("No environment variables provided");
        }
    }

    private String getJdbcUrl(final String env) {
        // jdbc:oracle:thin:@bd.ENV.mango.com:1521:SID
        final String jdbcUrl =  getenv("DB_JDBC_URL").replace("ENV", env.toLowerCase()).replace("SID", DataSourceEnvironment.getSid(env));
        log.info("JDBC URL: {}", jdbcUrl);
        return jdbcUrl;
    }

    public void getEans(final List<Item> items) {
        log.info("Inserting {} eans", items.size());
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)){
            // Establecer la conexión

/*
            // Ejemplo de inserción segura
            String insertQuery = "INSERT INTO mi_tabla (nombre, edad) VALUES (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, "Juan");
            insertStatement.setInt(2, 30);
            int rowsInserted = insertStatement.executeUpdate();
            System.out.println("Filas insertadas: " + rowsInserted);
*/
            // Ejemplo de selección segura
            PreparedStatement selectStatement = connection.prepareStatement(FileLoader.load(GET_EANS));
            selectStatement.setString(1, "6701038432TN");
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                String ean = resultSet.getString("EAN");
                int edad = resultSet.getInt("MARKETPLACE_ID");
                System.out.println("Ean: " + ean + ", MARKETPLACE ID: " + edad);
            }

            // Cerrar la conexión
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ImmutablePair<List<Item>, List<Item>> insertNewEans(final List<Item> items) {
        log.info("Inserting {} eans", items.size());
        List<Item> newItems = new ArrayList<>();
        List<Item> existingItems = new ArrayList<>();

        final int batchSize = 250;
        List<List<Item>> batches = createBatches(items, batchSize);
        log.info("batches {}", batches.size());
        for (List<Item> batch : batches) {
            long startTime = System.currentTimeMillis();
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)){
                String selectQuery = FileLoader.load(GET_EANS);
                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);

                String insertQuery = FileLoader.load(INSERT_EANS);
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

                connection.setAutoCommit(false);
                try {
                    for (Item item : batch) {
                        selectStatement.setString(1, item.getItemId());
                        selectStatement.setString(2, item.getEan());
                        ResultSet resultSet = selectStatement.executeQuery();

                        if (resultSet.next()) {
                            existingItems.add(item);
                        } else {
                            insertStatement.setString(1, "10");
                            insertStatement.setString(2, item.getItemId());
                            insertStatement.setString(3, item.getEan());
                            insertStatement.executeUpdate();
                            newItems.add(item);
                        }
                    }
                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            log.info("Batch processed in " + duration + " milliseconds");
            try {
                log.info("Esperamos 2 segundos...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return new ImmutablePair<>(newItems, existingItems);
    }

    private List<List<Item>> createBatches(List<Item> items, int batchSize) {
        int totalItems = items.size();
        int numBatches = (totalItems + batchSize - 1) / batchSize;

        return IntStream.range(0, numBatches)
                .mapToObj(i -> items.subList(i * batchSize, Math.min(totalItems, (i + 1) * batchSize)))
                .collect(Collectors.toList());
    }
}
