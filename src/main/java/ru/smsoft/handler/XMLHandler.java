package ru.smsoft.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.smsoft.entity.Course;
import ru.smsoft.entity.Currency;
import ru.smsoft.service.CourseService;
import ru.smsoft.service.CurrencyService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Логика парсинга XML.
 * Парсит ссылку на сайт и сохраняет записи в БД
 */
public class XMLHandler extends DefaultHandler {
    private final CourseService courseService;
    private final CurrencyService currencyService;

    public XMLHandler(CourseService courseService, CurrencyService currencyService) {
        this.courseService = courseService;
        this.currencyService = currencyService;
    }

    private LocalDate date;
    private String valuteId, numCode, charCode, nominal, name, value;
    private boolean foundNumCode = false;
    private boolean foundCharCode = false;
    private boolean foundNominal = false;
    private boolean foundName = false;
    private boolean foundValue = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("ValCurs") && date == null) {
            String stringDate = attributes.getValue("Date");
            date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }

        if (qName.equals("Valute") && valuteId == null) {
            valuteId = attributes.getValue("ID");
        }

        if (qName.equals("NumCode")) {
            foundNumCode = true;
        }

        if (qName.equals("CharCode")) {
            foundCharCode = true;
        }

        if (qName.equals("Nominal")) {
            foundNominal = true;
        }

        if (qName.equals("Name")) {
            foundName = true;
        }

        if (qName.equals("Value")) {
            foundValue = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("Valute")) {
            Currency currency;
            Course course = new Course();

            // Создаем новую валюту или берем из репозитория (если существует)
            if (currencyService.currencyIsExist(valuteId)) {
                currency = currencyService.getByValuteId(valuteId);
            }
            else {
                currency = new Currency();

                currency.setValuteId(valuteId);
                currency.setNumCode(Integer.parseInt(numCode));
                currency.setCharCode(charCode);
                currency.setNominal(Integer.parseInt(nominal));
                currency.setName(name);
                currencyService.save(currency);
            }

            course.setValue(Double.parseDouble(value.replaceAll(",", ".")));
            course.setDate(date);
            course.setCurrency(currency);

            // Сохраняем курс, если не существует
            if (!courseService.courseIsExist(course)) {
                courseService.save(course);
            }

            valuteId = null;
        }
        if (qName.equals("ValCurs")) {
            date = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (foundNumCode) {
            numCode = new String(ch, start, length);
            foundNumCode = false;
        }

        if (foundCharCode) {
            charCode = new String(ch, start, length);
            foundCharCode = false;
        }

        if (foundNominal) {
            nominal = new String(ch, start, length);
            foundNominal = false;
        }

        if (foundName) {
            name = new String(ch, start, length);
            foundName = false;
        }

        if (foundValue) {
            value = new String(ch, start, length);
            foundValue = false;
        }
    }
}