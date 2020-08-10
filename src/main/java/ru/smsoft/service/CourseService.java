package ru.smsoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smsoft.entity.Course;
import ru.smsoft.entity.Currency;
import ru.smsoft.repository.CourseRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CurrencyService currencyService;

    public boolean courseIsExist(Course course) {
        return courseRepository.existsByDateAndCurrency(course.getDate(), course.getCurrency());
    }

    public void save(Course course) {
        courseRepository.save(course);
    }

    public Course getActualCourseByCurrencyCharCode(String charCode) {
        Currency currency = currencyService.getByCharCode(charCode);
        return courseRepository.findFirstByCurrencyOrderByDateDesc(currency);
    }
}