package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    @Autowired
    EmployeeService employeeService;

    @Override
    public ReportingStructure read(String id) {
        Employee employee = employeeService.read(id);

        // Basically performing a breadth first search to figure out
        // the number of direct reports
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
