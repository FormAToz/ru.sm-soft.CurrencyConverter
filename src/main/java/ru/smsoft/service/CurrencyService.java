package ru.smsoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smsoft.entity.Currency;
import ru.smsoft.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CurrencyService {
    @Autowired
    private ParsingService parsingService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CurrencyRepository currencyRepository;

    public boolean currencyIsExist(String valuteId) {
        return currencyRepository.existsByValuteId(valuteId);
    }

    public Currency getByValuteId(String valuteId) {
        return currencyRepository.findByValuteId(valuteId);
    }

    public Currency getByCharCode(String charCode) {
        return currencyRepository.findByCharCode(charCode);
    }

    public void save(Currency currency) {
        currencyRepository.save(currency);
    }

    public List<Currency> getAllCurrencies() {
        return (List<Currency>) currencyRepository.findAll();
    }

    public double convertAndGetTargetSum(String originCurrency, String targetCurrency, String originCurrencySum) {
        String originCharCode = parseCharCode(originCurrency);
        String targetCharCode = parseCharCode(targetCurrency);
        double originValue = courseService.getActualCourseByCurrencyCharCode(originCharCode).getValue();
        double targetValue = courseService.getActualCourseByCurrencyCharCode(targetCharCode).getValue();
        double originSum = Double.parseDouble(originCurrencySum);
        // Конвертация
        double relationPercent = (originValue * originSum) / (targetValue * originSum);
        double targetSum = originSum * relationPercent;

        return new BigDecimal(targetSum).setScale(4, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Преобразование к виду "ААА"
     */
    private String parseCharCode(String currencyString) {
        return currencyString.substring(0,3).trim();
    }
}
