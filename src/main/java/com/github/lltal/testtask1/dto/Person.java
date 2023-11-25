package com.github.lltal.testtask1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Person {
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;

    @Override
    public String toString(){
        return String.format("%s\n%s %s", email, firstName, lastName);
    }
}