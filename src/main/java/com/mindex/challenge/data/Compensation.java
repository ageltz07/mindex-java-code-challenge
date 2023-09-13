package com.mindex.challenge.data;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class Compensation {
    private Employee employee;
    private int salary;
    private LocalDate effectiveDate;

    public Compensation() {

    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return "Compensation{" +
                "employee=" + employee +
                ", salary=" + salary +
                ", effectiveDate=" + effectiveDate +
                '}';
    }
}
