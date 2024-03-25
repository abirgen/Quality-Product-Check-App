package com.example.quality;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Certificate {

    private String department;
    private String firstName;
    private String lastName;
    private String matNumber;

    public Certificate() {
    }

    public Certificate(String department, String firstName, String lastName, String matNumber) {
        this.department = department;
        this.firstName = firstName;
        this.lastName = lastName;
        this.matNumber = matNumber;
    }

    public String getDepartment() {
        return department;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMatNumber() {
        return matNumber;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("department", department);
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("matNumber", matNumber);
        return  result;
    }
}
