package dev.jotxee.file;

import dev.jotxee.model.Item;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileLoader {
    public static String load(String path) {
        return new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(FileLoader.class.getResourceAsStream(path)), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    public static List<String> loadAsList(String path) {
        return new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(FileLoader.class.getResourceAsStream(path)), StandardCharsets.UTF_8))
                .lines()
                .toList();
    }

    public static List<Item> loadExcelAsList(String filePath) {
        IOUtils.setByteArrayMaxOverride(200_000_000);
        List<Item> items = new ArrayList<>();
        try (InputStream inputStream = FileLoader.class.getResourceAsStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int rowCount = 0;
            while (rowIterator.hasNext()) {
                rowCount++;
                Row row = rowIterator.next();
                if (rowCount == 1 || isRowEmpty(row)) {
                    continue; // Saltar la primera línea y las líneas vacías
                }
                Cell cell1 = row.getCell(0); // Primera columna
                Cell cell2 = row.getCell(1); // Segunda columna
                if (cell1 != null && cell2 != null) {
                    String firstColumnValue = getCellValue(cell1);
                    String secondColumnValue = getCellValue(cell2);
                    String itemId, ean;
                    if (firstColumnValue.length() == 12) {
                        itemId = firstColumnValue;
                        ean = secondColumnValue;
                    } else {
                        itemId = secondColumnValue;
                        ean = firstColumnValue;
                    }
                    items.add(new Item(itemId, ean));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    private static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    private static String getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case STRING -> cell.getStringCellValue();
            default -> "";
        };
    }
}
