package dev.jotxee.images.checkformats;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

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
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final HttpGet httpGet = new HttpGet(imageUrl);
            final HttpResponse response = httpClient.execute(httpGet);
            final HttpEntity entity = response.getEntity();

            if (entity != null) {
                final byte[] imageBytes = EntityUtils.toByteArray(entity);
                final String format = getImageFormat(imageBytes);
                if (UNKNOWN_FORMAT.equals(format)) {
                    logger.log(Level.INFO, () -> format("Formato de imagen desconocido: %s", imageUrl));
                }
                return format.equals("JPEG") || format.equals("WebP") || format.equals("PNG");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
