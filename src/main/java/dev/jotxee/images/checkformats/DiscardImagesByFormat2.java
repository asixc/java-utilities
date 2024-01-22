package dev.jotxee.images.checkformats;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

import static java.lang.String.format;

public interface DiscardImagesByFormat2 {

    Logger logger = Logger.getLogger(DiscardImagesByFormat2.class.getName());
    String UNKNOWN_FORMAT = "Desconocido";
    OkHttpClient httpClient = new OkHttpClient();

    static List<String> getValidImageUrls(final List<String> imageUrls) {
        // Para saber el número de hilos que se están ejecutando
        int defaultParallelism = ForkJoinPool.commonPool().getParallelism();
        // int parallelism = Integer.parseInt(System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism"));
        logger.info("Número predeterminado de hilos: " + defaultParallelism);

        return imageUrls.parallelStream()
                .filter(DiscardImagesByFormat2::isValidImage)
                .toList();
    }

    static boolean isValidImage(final String imageUrl) {
        final Request request = new Request.Builder()
                .url(imageUrl)
                .addHeader("Range", "bytes=0-10") // Solicita solo los primeros bytes
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                final byte[] imageBytes = response.body().bytes();
                final String format = getImageFormat(imageBytes);
                if (UNKNOWN_FORMAT.equals(format)) {
                    logger.info(String.format("Formato de imagen desconocido: %s", imageUrl));
                }
                return format.equals("JPEG") || format.equals("WebP") || format.equals("PNG");
            }
        } catch (IOException e) {
            logger.info("ERROR al verificar la imagen: " + imageUrl + e);
        }
        return false;
    }

    private static String getImageFormat(final byte[] imageBytes) {
        final String signature = bytesToHex(Arrays.copyOf(imageBytes, 4));

        return switch (signature) {
            case "FFD8FFE0" -> "JPEG";
            case "52494646" -> "WebP";
            case "89504E47" -> "PNG";
            default -> UNKNOWN_FORMAT;
        };
    }

    private static String bytesToHex(final byte[] bytes) {
        final StringBuilder result = new StringBuilder();
        for (final byte b : bytes) {
            result.append(format("%02X", b));
        }
        return result.toString();
    }
}
