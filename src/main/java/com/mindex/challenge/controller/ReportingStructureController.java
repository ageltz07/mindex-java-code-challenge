package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

@RestController
public class ReportingStructureController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/reportingStructure/{id}")
    public ReportingStructure getReportingStructure(@PathVariable String id) {

        Employee employee = employeeService.read(id);

        ArrayList<String> list = new ArrayList<>();
        ArrayDeque<Employee> queue = new ArrayDeque<>();

        queue.add(employee);

        while(!queue.isEmpty()) {
            int len = queue.size();
            for (int i = 0; i < len; i++) {

                Employee current = employeeService.read(queue.poll().getEmployeeId());
                list.add(current.getFirstName() + " " + current.getLastName());

                if (current.getDirectReports() != null) {
                    queue.addAll(current.getDirectReports());
                }
            }
        }

        int numOfDirectReports = list.size() > 1 ? list.size() - 1 : 0;

        return new ReportingStructure(employee, numOfDirectReports);
    }
}
