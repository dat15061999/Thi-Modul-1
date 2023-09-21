package com.example.demo1.service;

import com.example.demo1.model.Student;
import com.example.demo1.model.enumration.EGender;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private static final List<Student> students;
    private static int idCurrent;

    static {
        students = new ArrayList<>();
        students.add(new Student(++idCurrent, "Thang", Date.valueOf(LocalDate.now()), EGender.MALE));
        students.add(new Student(++idCurrent, "Duc", Date.valueOf(LocalDate.now()), EGender.MALE));
        students.add(new Student(++idCurrent, "Huy", Date.valueOf(LocalDate.now()), EGender.MALE));
    }

    public List<Student> getStudents(boolean deleted){
        return students.stream().filter(e -> e.isDeleted() == deleted).collect(Collectors.toList());
    }

    public void addStudent(String name, String dob, String gender){
        Student student = new Student(++idCurrent,name, Date.valueOf(dob), EGender.valueOf(gender));
        students.add(student);
    }

    public void remove(int id){
        for (Student student: students) {
            if(student.getId() == id){
                student.setDeleted(true);
            }
        }
    }
    public void restore(int id){
        for (Student student: students) {
            if(student.getId() == id){
                student.setDeleted(false);
            }
        }
    }

}