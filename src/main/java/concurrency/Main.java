package concurrency;

import java.util.List;

import org.eclipse.persistence.expressions.ExpressionBuilder;
import org.eclipse.persistence.queries.ReadAllQuery;
import org.eclipse.persistence.sessions.UnitOfWork;

public class Main {

  public static void main(String[] args) {
    createDepartmentWithEmployees();

    DBSessionProvider.getInstance().getEntityManagerFactory().getCache().evictAll();

    for (int i = 0; i < 2; i++) {
      new Thread(() -> {
        UnitOfWork uow = DBSessionProvider.getInstance().getNewClientSession().acquireUnitOfWork();
        try {
          ReadAllQuery query = new ReadAllQuery(Employee.class, new ExpressionBuilder());
          Employee employee = ((List<Employee>) uow.executeQuery(query)).get(0);

          System.out.println(employee.getDepartment().getName());

          uow.commit();
        } finally {
          uow.release();
        }
      }).start();
    }
  }

  private static void createDepartmentWithEmployees() {
    UnitOfWork uow = DBSessionProvider.getInstance().getNewClientSession().acquireUnitOfWork();
    try {
      Employee bob = new Employee("Bob");
      uow.registerNewObject(bob);
      Employee paul = new Employee("Paul");
      uow.registerNewObject(paul);

      Department it = new Department("IT");
      it.addEmployee(bob);
      it.addEmployee(paul);
      uow.registerNewObject(it);

      uow.commit();
    } finally {
      uow.release();
    }
  }

}
