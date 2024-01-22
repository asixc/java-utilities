package dev.jotxee;

import dev.jotxee.services.EanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainInsertNewEansByFile {
    private static final Logger log = LoggerFactory.getLogger(MainInsertNewEansByFile.class);
    public static void main(String[] args) {
        log.info("Starting creating eans");
        final EanService service = new EanService(args);
        service.createNewEans();
        log.info("Finished creating eans");
    }
}
