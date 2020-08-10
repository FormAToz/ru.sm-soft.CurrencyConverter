package ru.smsoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smsoft.entity.OperationHistory;
import ru.smsoft.repository.OperationHistoryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryService {
    @Autowired
    private OperationHistoryRepository historyRepository;

    public void addRecord(String originCurrency, String targetCurrency, double originCurrencySum, double targetCurrencySum) {
        OperationHistory operationHistory = new OperationHistory();
        operationHistory.setOriginCurrency(originCurrency);
        operationHistory.setTargetCurrency(targetCurrency);
        operationHistory.setOriginSum(originCurrencySum);
        operationHistory.setTargetSum(targetCurrencySum);
        operationHistory.setDate(LocalDate.now());
        historyRepository.save(operationHistory);
    }

    public List<OperationHistory> getAllRecords() {
        return (List<OperationHistory>) historyRepository.findAll();
    }

    /**
     * Проверяем пустые ли входящие фильтры. Если да, то возврашаем все записи в истории.
     */
    public List<OperationHistory> getFilteredRecords(String originCurrency, String targetCurrency, String date) {
        List<OperationHistory> allRecords = (List<OperationHistory>) historyRepository.findAll();
        List<OperationHistory> filteredRecords = new ArrayList<>();
        boolean originCurrIsEmpty = checkIsEmpty(originCurrency);
        boolean targetCurrIsEmpty = checkIsEmpty(targetCurrency);
        boolean dateIsEmpty = checkIsEmpty(date);
        boolean allAttributesIsEmpty = false;

        // проверяем все ли аттрибуты пусты
        if (originCurrIsEmpty && targetCurrIsEmpty && dateIsEmpty) {
            allAttributesIsEmpty = true;
        }

        if (!originCurrIsEmpty) {
            filteredRecords.addAll(filterListByOriginCurrency(allRecords, originCurrency));
        }

        if (!targetCurrIsEmpty) {
            if (originCurrIsEmpty) {        // если первый фильтр(исходной валюты) пустой, фильтруем все записи
                filteredRecords.addAll(filterListByTargetCurrency(allRecords, targetCurrency));
            }
            else {                          // иначе фильтруем записи после первого фильтра
                filteredRecords = filterListByTargetCurrency(filteredRecords, targetCurrency);
            }
        }

        if (!dateIsEmpty) {                 // если первый и/или второй фильтры пустые, фильтруем все записи
            if (originCurrIsEmpty && targetCurrIsEmpty) {
                filteredRecords.addAll(filterListByDate(allRecords, date));
            }
            else {
                filteredRecords = filterListByDate(filteredRecords, date);
            }
        }

        return allAttributesIsEmpty
                ? allRecords
                : filteredRecords;
    }

    private boolean checkIsEmpty(String string) {
        return string.isEmpty();
    }

    private List<OperationHistory> filterListByOriginCurrency(List<OperationHistory> list, String originCurrency) {
        return list
                .stream()
                .filter(el -> el.getOriginCurrency()
                        .substring(0, 3)
                        .equalsIgnoreCase(originCurrency))
                .collect(Collectors.toList());
    }

    private List<OperationHistory> filterListByTargetCurrency(List<OperationHistory> list, String targetCurrency) {
        return list.stream()
                .filter(el -> el.getTargetCurrency()
                        .substring(0, 3)
                        .equalsIgnoreCase(targetCurrency))
                .collect(Collectors.toList());
    }

    private List<OperationHistory> filterListByDate(List<OperationHistory> list, String date) {
        return list.stream()
                .filter(el -> el.getDate().isEqual(LocalDate.parse(date)))
                .collect(Collectors.toList());
    }
}
