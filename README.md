# employee-management-system

### Uses 
  - Java Stream API
  - Optional
  - Rest
  - Spring Boot
  - PostgreSQL


### Class Diagram

class Employee {
  - Long Id
  - String name
  - String department
  - Double salary
  - LocalDate joiningDate
  - Address address
  + getters()
  + setters()
}

class Address {
  - String addressLine1
  - String addressLine2
  - String city
  - String country
  + getters()
  + setters()
}

class Department {
  - String name
  - List<Employee> employees
  + getters()
  + setters()
}

Employee "1" --> "1" Address

Department "1" --> "*" Employee
