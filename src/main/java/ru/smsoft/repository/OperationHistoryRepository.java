package ru.smsoft.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.smsoft.entity.OperationHistory;

@Repository
public interface OperationHistoryRepository extends CrudRepository<OperationHistory, Integer> {
}
