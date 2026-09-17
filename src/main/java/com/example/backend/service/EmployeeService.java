package com.example.backend.service;

import com.example.backend.dto.EmployeeRequest;
import com.example.backend.model.Employee;
import com.example.backend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
    }

    public Employee createEmployee(EmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Employee email is already registered: " + request.getEmail());
        }

        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .department(request.getDepartment())
                .position(request.getPosition())
                .salary(request.getSalary() != null ? request.getSalary() : 0.0)
                .build();

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeeRequest request) {
        Employee existing = getEmployeeById(id);

        if (!existing.getEmail().equalsIgnoreCase(request.getEmail()) &&
                employeeRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Employee email is already registered: " + request.getEmail());
        }

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());
        existing.setDepartment(request.getDepartment());
        existing.setPosition(request.getPosition());
        if (request.getSalary() != null) {
            existing.setSalary(request.getSalary());
        }

        return employeeRepository.save(existing);
    }

    public void deleteEmployee(Long id) {
        Employee existing = getEmployeeById(id);
        employeeRepository.delete(existing);
    }
}
