package ru.smsoft.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.smsoft.entity.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Integer> {
    boolean existsByValuteId(String valuteId);

    Currency findByValuteId(String valuteId);

    Currency findByCharCode(String charCode);
}
