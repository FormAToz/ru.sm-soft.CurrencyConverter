package ru.smsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.smsoft.service.CurrencyService;
import ru.smsoft.service.HistoryService;

@Controller
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;
    private final CurrencyService currencyService;

    public HistoryController(HistoryService historyService, CurrencyService currencyService) {
        this.historyService = historyService;
        this.currencyService = currencyService;
    }

    @GetMapping
    public String get(Model model) {
        model.addAttribute("currency", currencyService.getAllCurrencies());
        model.addAttribute("allRecords", historyService.getAllRecords());          // Все записи в истории
        return "history";
    }

    @GetMapping("/search")
    public String getByAttributes(String originCurrency, String targetCurrency, String date, Model model) {
        model.addAttribute("currency", currencyService.getAllCurrencies());
        model.addAttribute("allRecords", historyService.getFilteredRecords(originCurrency, targetCurrency, date));     // Все отфильтрованные записи в истории
        return "history";
    }
}
