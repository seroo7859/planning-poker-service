package de.bht.planningpoker.service.util;

import de.bht.planningpoker.model.Backlog;
import de.bht.planningpoker.model.BacklogItem;
import de.bht.planningpoker.service.exception.OperationFailedException;
import de.bht.planningpoker.service.exception.ServiceException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Provides the functions to read/write backlog to CSV file.
 * Source: https://github.com/bezkoder/spring-boot-upload-csv-files/blob/master/src/main/java/com/bezkoder/spring/files/csv/helper/CSVHelper.java
 */
public class BacklogCSVHelper {

    private static final String CSV_CONTENT_TYPE = "text/csv";
    private static final String CSV_FILE_EXTENSION = ".csv";
    private static final String CSV_DELIMITER = ";";
    private static final CSVFormat CSV_FORMAT;

    private enum BacklogItemHeaders {
        Number, Title, Description, Estimation, Priority
    }

    static {
        CSV_FORMAT = CSVFormat.DEFAULT.builder()
                .setHeader(BacklogItemHeaders.class)
                .setIgnoreHeaderCase(true)
                .setDelimiter(CSV_DELIMITER)
                .build();
    }

    public static boolean hasCSVFormat(MultipartFile file) {
        return CSV_CONTENT_TYPE.equals(file.getContentType());
    }

    public static Backlog csvToBacklog(MultipartFile file) throws ServiceException {
        try (
            Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(reader, CSV_FORMAT)
        ) {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            List<BacklogItem> backlogItems = new ArrayList<>();
            for (CSVRecord csvRecord : csvRecords) {
                if (csvRecord.getRecordNumber() == 1) {     // Skip header record
                    continue;
                }

                BacklogItem backlogItem = BacklogItem.builder()
                        .number(csvRecord.get(BacklogItemHeaders.Number))
                        .title(csvRecord.get(BacklogItemHeaders.Title))
                        .description(csvRecord.get(BacklogItemHeaders.Description))
                        .estimation(csvRecord.get(BacklogItemHeaders.Estimation))
                        .priority(csvRecord.get(BacklogItemHeaders.Priority))
                        .build();
                backlogItems.add(backlogItem);
            }

            return Backlog.builder()
                    .name(Objects.requireNonNull(file.getOriginalFilename()).replaceFirst(CSV_FILE_EXTENSION + '$', Strings.EMPTY))
                    .items(backlogItems)
                    .build();
        } catch (IOException e) {
            throw new OperationFailedException("Fail to parse the CSV file " + file.getOriginalFilename(), e);
        }
    }

    public static Resource backlogToCsv(Backlog backlog) throws ServiceException {
        try (
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(outputStream, true, StandardCharsets.UTF_8), CSV_FORMAT)
        ) {
            for (BacklogItem backlogItem : backlog.getItems()) {
                List<String> csvRecord = List.of(
                    backlogItem.getNumber(),
                    backlogItem.getTitle(),
                    backlogItem.getDescription(),
                    backlogItem.getEstimation(),
                    backlogItem.getPriority()
                );
                csvPrinter.printRecord(csvRecord);
            }
            csvPrinter.flush();

            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            return new InputStreamResource(inputStream);
        } catch (IOException e) {
            throw new OperationFailedException("Fail to print data to CSV file", e);
        }
    }

}
