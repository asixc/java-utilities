package dev.jotxee;

import dev.jotxee.file.FileLoader;
import dev.jotxee.file.SaveFileInProjectPath;
import dev.jotxee.images.checkformats.DiscardImagesByFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class MainCheckImg {
    private static final Logger log = LoggerFactory.getLogger(SaveFileInProjectPath.class);
    public static void main(String[] args) {
        List<String> imageUrls = FileLoader.loadAsList("/images.txt");
        log.info("imageUrls: {}", imageUrls.size());
        DiscardImagesByFormat.getValidImageUrls(imageUrls);
    }
}
