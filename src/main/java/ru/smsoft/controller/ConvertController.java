package ru.smsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.smsoft.entity.Currency;
import ru.smsoft.service.CurrencyService;
import ru.smsoft.service.HistoryService;

import java.util.List;

@Controller
@RequestMapping("/convert")
public class ConvertController {
    private final CurrencyService currencyService;
    private final HistoryService historyService;

    public ConvertController(CurrencyService currencyService, HistoryService historyService) {
        this.currencyService = currencyService;
        this.historyService = historyService;
    }

    @GetMapping
    public String get(Model model) {
        List<Currency> currencyList = currencyService.getAllCurrencies();
        model.addAttribute("currency", currencyList);
        return "convert";
    }

    @PostMapping
    public String convert(String originCurrency, String targetCurrency, String originCurrencySum, Model model) {
        double targetCurrencySum = currencyService.convertAndGetTargetSum(originCurrency, targetCurrency, originCurrencySum);
        historyService.addRecord(originCurrency, targetCurrency, Double.parseDouble(originCurrencySum), targetCurrencySum);

        model.addAttribute("originCurrency", originCurrency);
        model.addAttribute("targetCurrency", targetCurrency);
        model.addAttribute("originCurrencySum", originCurrencySum);
        model.addAttribute("targetCurrencySum", targetCurrencySum);
        return "convert_result";
    }
}
