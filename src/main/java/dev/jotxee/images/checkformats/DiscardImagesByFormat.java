package dev.jotxee.images.checkformats;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

public interface DiscardImagesByFormat {
    Logger logger = Logger.getLogger(DiscardImagesByFormat.class.getName());
    String UNKNOWN_FORMAT = "Desconocido";

    static List<String> getValidImageUrls(final List<String> imageUrls) {
        return imageUrls.stream()
                .filter(DiscardImagesByFormat::isValidImage)
                .toList();
    }

    private static boolean isValidImage(final String imageUrl) {
        final OkHttpClient httpClient = new OkHttpClient();

           final Request request = new Request.Builder()
                    .url(imageUrl)
                    .build();

        try (final Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                final byte[] imageBytes = response.body().bytes();
                final String format = getImageFormat(imageBytes);
                if (UNKNOWN_FORMAT.equals(format)) {
                    logger.log(Level.INFO, () -> format("Formato de imagen desconocido: %s", imageUrl));
                }
                return format.equals("JPEG") || format.equals("WebP") || format.equals("PNG");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.dispatcher().executorService().shutdown();
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
