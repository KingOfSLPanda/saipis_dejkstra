package com.portfl.model;

import javax.persistence.*;
import java.util.Set;

public enum Gender {
    Male("Мужской"),
    Female("Женский");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
