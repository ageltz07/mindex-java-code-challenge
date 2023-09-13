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
        // the number of direct reports by traversing layer by layer from top to bottom
        ArrayList<String> reports = new ArrayList<>();
        ArrayDeque<Employee> queue = new ArrayDeque<>();

        queue.add(employee);

        // Continues while there are still employees in the queue
        while(!queue.isEmpty()) {
            // Storing the queue length of the current level
            int length = queue.size();
            for (int i = 0; i < length; i++) {

                Employee current = employeeService.read(queue.poll().getEmployeeId());

                // Add employee to our list of reports
                reports.add(current.getEmployeeId());

                // If the current employee has reports, add them all to the end of the queue
                // this will be the next level of reports to process
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
