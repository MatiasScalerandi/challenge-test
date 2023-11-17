package com.capitole.inditex.v1.util;

import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class PriceUtils {
    private final static String SIMPLE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date parseStringToDateType(ProductRetrievalRequest retrievalRequest) throws ParseException {
        return new SimpleDateFormat(SIMPLE_DATE_FORMAT).parse(retrievalRequest.getApplicationStartDate());
    }
}
