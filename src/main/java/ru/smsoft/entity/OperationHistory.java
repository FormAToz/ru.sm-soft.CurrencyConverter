package ru.smsoft.entity;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * История операций
 */
@Entity
@Table(name = "operation_history")
public class OperationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;

    @Column(name = "origin_currency")
    private String originCurrency;

    @Column(name = "target_currency")
    private String targetCurrency;

    @Column(name = "origin_sum")
    private double originSum;

    @Column(name = "target_sum")
    private double targetSum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getOriginCurrency() {
        return originCurrency;
    }

    public void setOriginCurrency(String originCurrency) {
        this.originCurrency = originCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getOriginSum() {
        return originSum;
    }

    public void setOriginSum(double originSum) {
        this.originSum = originSum;
    }

    public double getTargetSum() {
        return targetSum;
    }

    public void setTargetSum(double targetSum) {
        this.targetSum = targetSum;
    }
}
