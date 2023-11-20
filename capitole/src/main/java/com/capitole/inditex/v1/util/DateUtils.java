package com.capitole.inditex.v1.util;

import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Objects;

/**
 * Helper to handle and parse {@link Date} to {@link LocalDateTime}
 *
 * @author Matias Scalerandi
 */
@Component
public class DateUtils {
    private final static String SIMPLE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT);

    /**
     * Convert {@link Date} to {@link LocalDateTime}
     *
     * @param dateToConvert the request {@link Date} date
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date parseStringToDateType(ProductRetrievalRequest retrievalRequest) throws ParseException {
        Preconditions.checkArgument(Objects.nonNull(retrievalRequest.getApplicationStartDate()), "'date' cannot be null");
        isValidLocalDate(retrievalRequest.getApplicationStartDate(), DATE_TIME_FORMATTER);
        return new SimpleDateFormat(SIMPLE_DATE_FORMAT).parse(retrievalRequest.getApplicationStartDate());
    }

    public static void isValidLocalDate(String dateStr, DateTimeFormatter dateFormatter) throws ParseException {
        try {
            LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            String message = String.format("the date pattern %s is invalid, try with %s", dateStr, SIMPLE_DATE_FORMAT);
            throw new ParseException(message, e.getErrorIndex());
        }
    }
}
