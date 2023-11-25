package com.github.lltal.testtask1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PersonItem {
    @JsonProperty("data")
    private Person person;
}
