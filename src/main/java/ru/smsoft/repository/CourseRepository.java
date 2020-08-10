package ru.smsoft.repository;

import org.springframework.data.repository.CrudRepository;
import ru.smsoft.entity.Course;
import ru.smsoft.entity.Currency;

import java.time.LocalDate;

public interface CourseRepository extends CrudRepository<Course, Integer> {
    boolean existsByDateAndCurrency(LocalDate date, Currency currency);

    Course findFirstByCurrencyOrderByDateDesc(Currency currency);
}
