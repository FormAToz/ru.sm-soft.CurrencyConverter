package ru.smsoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smsoft.handler.XMLHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Класс управления парсингом.
 * Значение атрибута даты можно изменять.
 */
@Service
public class ParsingService {
    private final String URI = "http://www.cbr.ru/scripts/XML_daily.asp";
    @Autowired
    private CourseService courseService;
    @Autowired
    private CurrencyService currencyService;

    public void parseByCurrentDate() {
        parse(URI);
    }

    public void parseByDate(LocalDate date) {
        String dateQuery = "?date_req=" + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        parse(URI + dateQuery);
    }

    private void parse(String uri) {
        try {
            XMLHandler handler = new XMLHandler(courseService, currencyService);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(uri, handler);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
