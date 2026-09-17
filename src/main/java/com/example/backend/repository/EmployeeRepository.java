package com.example.backend.repository;

import com.example.backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email); // SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM employee_info
                                         // WHERE email = ?

    Optional<Employee> findByEmail(String email); // SELECT * FROM employees_info WHERE email = ?
}
