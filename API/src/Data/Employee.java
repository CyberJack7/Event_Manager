package Data;

import java.util.HashMap;
import java.io.Serializable;
public class Employee implements Serializable{

    private int employeeId;
    private String email;
    private String name;
    private String surname;
    private String patronymic;
    private String dateBirth;
    private String phone;
    private String employmentDate;
    private String post;
    private String dismissalDate;

    public Employee(int employee_id, HashMap<String, String> employee){
        this.employeeId = employee_id;
        this.email = employee.get("email");
        this.name = employee.get("name");
        this.surname = employee.get("surname");
        this.patronymic = employee.get("patronymic");
        this.dateBirth = employee.get("dateBirth");
        this.phone = employee.get("phone");
        this.employmentDate = employee.get("employmentDate");
        this.post = employee.get("post");
        this.dismissalDate = employee.get("dismissalDate");
    }

    public Employee(String name, String surname, String patronymic, String email, String phone, String dateBirth, String employmentDate, String post) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.phone = phone;
        this.dateBirth = dateBirth;
        this.employmentDate = employmentDate;
        this.post = post;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(String employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDismissalDate() {
        return dismissalDate;
    }

    public void setDismissalDate(String dismissalDate) {
        this.dismissalDate = dismissalDate;
    }
}
