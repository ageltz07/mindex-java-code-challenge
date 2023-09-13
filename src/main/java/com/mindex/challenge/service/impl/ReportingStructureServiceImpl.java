package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    EmployeeService employeeService;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Reading Reporting Structure with id [{}]", id);

        Employee employee = employeeService.read(id);

        // Basically performing a breadth first search to figure out
        // the number of direct reports
        ArrayList<String> reports = new ArrayList<>();
        ArrayDeque<Employee> queue = new ArrayDeque<>();

        queue.add(employee);

        while(!queue.isEmpty()) {
            int length = queue.size();
            for (int i = 0; i < length; i++) {

                Employee current = employeeService.read(queue.poll().getEmployeeId());
                reports.add(current.getFirstName() + " " + current.getLastName());

                if (current.getDirectReports() != null) {
                    queue.addAll(current.getDirectReports());
                }
            }
        }

        // If the reports size is greater than two there are direct reports
        // otherwise none so assign value of 0.
        int numOfDirectReports = reports.size() > 1 ? reports.size() - 1 : 0;

        return new ReportingStructure(employee, numOfDirectReports);
    }
}
