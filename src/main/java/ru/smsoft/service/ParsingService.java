package ru.smsoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${currencyUrl}")
    private String URI;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CurrencyService currencyService;

    public void parseByCurrentDate() {
        parse(URI);
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
