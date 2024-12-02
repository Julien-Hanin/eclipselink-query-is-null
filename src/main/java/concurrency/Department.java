package concurrency;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
@Cache(type = CacheType.FULL)
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;

  @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
  private List<Employee> employees = new ArrayList<>();

  public Department() {
  }

  public Department(String name) {
    this.name = name;
  }

  public void addEmployee(Employee employee) {
    employees.add(employee);
    employee.setDepartment(this);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }

}
