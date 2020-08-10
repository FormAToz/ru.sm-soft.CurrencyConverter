package ru.smsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.smsoft.service.ParsingService;

import java.time.LocalDate;

@Controller
public class IndexController {
    private final ParsingService parsingService;

    public IndexController(ParsingService parsingService) {
        this.parsingService = parsingService;
    }

    /**
     * При запуске - парсинг текущей даты и даты, 5 днями раньше
     */
    @RequestMapping("/")
    public String index() {
        parsingService.parseByCurrentDate();
        parsingService.parseByDate(LocalDate.now().minusDays(5));
        return "index";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
}
