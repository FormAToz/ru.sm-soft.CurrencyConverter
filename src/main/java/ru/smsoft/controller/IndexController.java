package ru.smsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.smsoft.service.ParsingService;

@Controller
public class IndexController {
    private final ParsingService parsingService;

    public IndexController(ParsingService parsingService) {
        this.parsingService = parsingService;
    }

    /**
     * При запуске - парсинг текущей даты
     */
    @RequestMapping("/")
    public String index() {
        parsingService.parseByCurrentDate();
        return "index";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
}
