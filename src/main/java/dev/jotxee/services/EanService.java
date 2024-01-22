package dev.jotxee.services;

import dev.jotxee.MainInsertNewEansByFile;
import dev.jotxee.file.FileLoader;
import dev.jotxee.model.Item;
import dev.jotxee.repository.EanRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EanService {

    private static final Logger log = LoggerFactory.getLogger(EanService.class);
    private final EanRepository repository;
    private final String env;
    private final String fileName;

    public EanService(final String[] args) {
        env = getEnv(args);
        fileName = getFileName(args);
        repository = new EanRepository(env);
    }

    public void createNewEans() {
        final List<Item> items = FileLoader.loadExcelAsList("/".concat(fileName));
        log.info("eans: {}", items.size());
        ImmutablePair<List<Item>, List<Item>> result = repository.insertNewEans(items);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = LocalDateTime.now().format(formatter);

        try {
            if (!result.getLeft().isEmpty()) {
                PrintWriter writer = new PrintWriter("nuevos_eans_" + currentDateTime + ".txt", "UTF-8");
                writer.println(currentDateTime);
                for (Item item : result.getLeft()) {
                    writer.println(item.toString());
                }
                writer.close();

                writer = new PrintWriter("script_" + currentDateTime + ".sql", "UTF-8");
                writer.println("-- " +currentDateTime);
                for (Item item : result.getLeft()) {
                    writer.println("INSERT INTO MNGBD.MARKETPLACE_SENDED_ITEMS VALUES ('10', '" + item.getItemId() + "', '" + item.getEan() + "', sysdate);");
                }
                writer.close();
            }

            if (!result.getRight().isEmpty()) {
                PrintWriter writer = new PrintWriter("eans_existentes_" + currentDateTime + ".txt", "UTF-8");
                writer.println(currentDateTime);
                for (Item item : result.getRight()) {
                    writer.println(item.toString());
                }
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // logs de resultados:
        int totalNewItems = result.getLeft().size();
        int totalExistingItems = result.getRight().size();
        int totalItemsRead = items.size();
        log.info("Total new items: {}", totalNewItems);
        log.info("Total existing items: {}", totalExistingItems);

        int totalItemsProcessed = totalNewItems + totalExistingItems;
        log.info("Total items processed: {}", totalItemsProcessed);

        if (totalItemsRead == totalItemsProcessed) {
            log.info("The total items read from the xlsx file matches the total items processed.");
        } else {
            log.info("The total items read from the xlsx file does not match the total items processed.");
        }
        //
    }

    private String getEnv(final String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments provided");
        }
        return args[0];
    }
    private String getFileName(final String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No arguments provided");
        }
        return args[1];
    }
}
