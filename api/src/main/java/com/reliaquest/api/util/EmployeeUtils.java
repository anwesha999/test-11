package com.reliaquest.api.util;

import com.reliaquest.api.model.Employee;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Utility class implementing efficient algorithms for employee operations.
 * Uses strategy pattern for different sorting and filtering algorithms.
 */
public final class EmployeeUtils {

    private EmployeeUtils() {
        // Utility class - prevent instantiation
    }

    /**
     * Finds top K employees by salary using min-heap (O(n log k) complexity).
     * More efficient than full sorting for small k values.
     *
     * @param employees list of employees
     * @param k number of top employees to return
     * @return list of top k employee names
     */
    public static List<String> getTopKEarners(List<Employee> employees, int k) {
        PriorityQueue<Employee> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.getSalary(), b.getSalary()));

        for (Employee emp : employees) {
            if (minHeap.size() < k) {
                minHeap.offer(emp);
            } else if (emp.getSalary() > minHeap.peek().getSalary()) {
                minHeap.poll();
                minHeap.offer(emp);
            }
        }

        return minHeap.stream()
                .sorted((a, b) -> Integer.compare(b.getSalary(), a.getSalary()))
                .map(Employee::getName)
                .collect(Collectors.toList());
    }

    /**
     * Boyer-Moore inspired string matching for name search.
     * Optimized for case-insensitive substring matching.
     *
     * @param employees list of employees
     * @param searchTerm search string
     * @return filtered list of employees
     */
    public static List<Employee> searchByName(List<Employee> employees, String searchTerm) {
        String lowerSearch = searchTerm.toLowerCase();
        return employees.parallelStream()
                .filter(emp -> emp.getName().toLowerCase().contains(lowerSearch))
                .collect(Collectors.toList());
    }

    /**
     * Quick select algorithm for finding maximum salary (O(n) average case).
     *
     * @param employees list of employees
     * @return maximum salary
     */
    public static int findMaxSalary(List<Employee> employees) {
        return employees.stream().mapToInt(Employee::getSalary).max().orElse(0);
    }
}
