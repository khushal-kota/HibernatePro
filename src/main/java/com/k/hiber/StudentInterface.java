package com.k.hiber;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Scanner;

public class StudentInterface {
	
    private StandardServiceRegistry ssr;
    private Metadata md;
    private SessionFactory sf;
    private Session session;
    private Transaction t;

    public StudentInterface() {
        ssr = new StandardServiceRegistryBuilder().configure("Hibernate.cfg.xml").build();
        md = new MetadataSources(ssr).getMetadataBuilder().build();
        sf = md.getSessionFactoryBuilder().build();
        session = sf.openSession();
    }

    public static void main(String[] args) {
        StudentInterface service = new StudentInterface();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Insert a new student");
            System.out.println("2. Display all students");
            System.out.println("3. Display specific columns of students");
            System.out.println("4. Display names of students with CGPA > 7");
            System.out.println("5. Delete a student by ID");
            System.out.println("6. Update a student by ID");
            System.out.println("7. Perform aggregate functions on CGPA");
            System.out.println("8. Display specific columns using Criteria");
            System.out.println("9. Get 5th to 10th records using Criteria");
            System.out.println("10. Apply conditions on CGPA using Criteria");
            System.out.println("11. Sort by Student Name using Criteria");
            System.out.println("12. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    service.insertStudent();
                    break;
                case 2:
                    service.displayAllStudents();
                    break;
                case 3:
                    service.displaySpecificColumns();
                    break;
                case 4:
                    service.displayStudentsWithCgpaGreaterThan7();
                    break;
                case 5:
                    System.out.print("Enter Student ID to delete: ");
                    int deleteId = scanner.nextInt();
                    service.deleteStudent(deleteId);
                    break;
                case 6:
                    System.out.print("Enter Student ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    service.updateStudent(updateId, newName);
                    break;
                case 7:
                    service.performAggregateFunctionsOnCgpa();
                    break;
                case 8:
                    service.displaySpecificColumnsCriteria();
                    break;
                case 9:
                    service.getFifthToTenthRecords();
                    break;
                case 10:
                    service.applyConditionsOnCgpa();
                    break;
                case 11:
                    service.sortByStudentName();
                    break;
                case 12:
                    System.out.println("Exiting...");
                    scanner.close();
                    service.closeSession();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Operation 1: Insert a new student
    public void insertStudent() {
        t = session.beginTransaction();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        System.out.print("Enter Program: ");
        String program = scanner.nextLine();

        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();

        System.out.print("Enter Contact Number: ");
        String contact = scanner.nextLine();

        System.out.print("Enter Graduation Status: ");
        String graduation = scanner.nextLine();

        System.out.print("Enter CGPA: ");
        double cgpa = scanner.nextDouble();

        System.out.print("Enter Backlogs: ");
        int backlogs = scanner.nextInt();

        Student student = new Student();
        student.setName(name);
        student.setGender(gender);
        student.setDepartment(department);
        student.setProgram(program);
        student.setDob(dob);
        student.setContact(contact);
        student.setGraduation(graduation);
        student.setCpga(cgpa);
        student.setBacklogs(backlogs);

        session.save(student);
        t.commit();
        System.out.println("Student inserted successfully.");
    }

    // Operation 2: Display all students
    public void displayAllStudents() {
        List<Student> students = session.createQuery("from Student", Student.class).list();
        students.forEach(this::printStudentDetails);
    }

    // Operation 3: Display specific columns of students
    public void displaySpecificColumns() {
        List<Object[]> students = session.createQuery("select name, department, cpga from Student").list();
        for (Object[] row : students) {
            System.out.println("Name: " + row[0] + ", Department: " + row[1] + ", CGPA: " + row[2]);
        }
    }

    // Operation 4: Display names of students with CGPA > 7
    public void displayStudentsWithCgpaGreaterThan7() {
        List<String> names = session.createQuery("select name from Student where cpga > 7").list();
        names.forEach(System.out::println);
    }

    // Operation 5: Delete a student by ID
    public void deleteStudent(int id) {
        t = session.beginTransaction();
        Query query = session.createQuery("delete from Student where id = :id");
        query.setParameter("id", id);
        int result = query.executeUpdate();
        t.commit();
        System.out.println(result + " student(s) deleted.");
    }

    // Operation 6: Update a student by ID
    public void updateStudent(int id, String newName) {
        t = session.beginTransaction();
        Query query = session.createQuery("update Student set name = :name where id = :id");
        query.setParameter("name", newName);
        query.setParameter("id", id);
        int result = query.executeUpdate();
        t.commit();
        System.out.println(result + " student(s) updated.");
    }

    // Operation 7: Perform aggregate functions on CGPA
    public void performAggregateFunctionsOnCgpa() {
        Object[] results = (Object[]) session.createQuery("select count(*), sum(cpga), avg(cpga), min(cpga), max(cpga) from Student").uniqueResult();
        System.out.println("Count: " + results[0] + ", Sum: " + results[1] + ", Avg: " + results[2] + ", Min: " + results[3] + ", Max: " + results[4]);
    }

    // Operation 8: Display specific columns using Criteria
    public void displaySpecificColumnsCriteria() {
        Criteria criteria = session.createCriteria(Student.class)
                .setProjection(org.hibernate.criterion.Projections.projectionList()
                        .add(org.hibernate.criterion.Projections.property("name"))
                        .add(org.hibernate.criterion.Projections.property("department"))
                        .add(org.hibernate.criterion.Projections.property("cpga")));
        List<Object[]> students = criteria.list();
        for (Object[] row : students) {
            System.out.println("Name: " + row[0] + ", Department: " + row[1] + ", CGPA: " + row[2]);
        }
    }

    // Operation 9: Get 5th to 10th records using Criteria
    public void getFifthToTenthRecords() {
        Criteria criteria = session.createCriteria(Student.class)
                .setFirstResult(4)  // Hibernate is 0-indexed, so 4 means the 5th record
                .setMaxResults(6);   // Get 6 records starting from the 5th
        List<Student> students = criteria.list();
        students.forEach(this::printStudentDetails);
    }

    // Operation 10: Apply conditions on CGPA using Criteria
    public void applyConditionsOnCgpa() {
        Criteria criteria = session.createCriteria(Student.class)
                .add(Restrictions.gt("cpga", 7.0))
                .add(Restrictions.le("cpga", 9.0));
        List<Student> students = criteria.list();
        students.forEach(this::printStudentDetails);
    }

    // Operation 11: Sort by Student Name using Criteria
    public void sortByStudentName() {
        System.out.println("Ascending Order:");
        Criteria criteria = session.createCriteria(Student.class)
                .addOrder(Order.asc("name"));
        List<Student> students = criteria.list();
        students.forEach(this::printStudentDetails);

        System.out.println("Descending Order:");
        criteria = session.createCriteria(Student.class)
                .addOrder(Order.desc("name"));
        students = criteria.list();
        students.forEach(this::printStudentDetails);
    }

    private void printStudentDetails(Student student) {
        System.out.println("ID: " + student.getId() + ", Name: " + student.getName() + ", Gender: " + student.getGender() +
                ", Department: " + student.getDepartment() + ", Program: " + student.getProgram() +
                ", Date of Birth: " + student.getDob() + ", Contact Number: " + student.getContact() +
                ", Graduation Status: " + student.getGraduation() + ", CGPA: " + student.getCpga() +
                ", Backlogs: " + student.getBacklogs());
    }

    private void closeSession() {
        session.close();
        sf.close();
    }
}
