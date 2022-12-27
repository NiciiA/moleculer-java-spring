package com.example.demo.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddMathDto {

    @NotNull
    @Min(5)
    @Max(12)
    @Positive
    private int a;

    @NotNull
    private int b;

    public AddMathDto() {

    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }
}
