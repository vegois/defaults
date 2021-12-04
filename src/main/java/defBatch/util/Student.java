package defBatch.util;

import java.math.BigDecimal;

public class Student {

    private String name;
    private Integer age;
    private Boolean male;
    private BigDecimal money;
    private double rate;

    public Student() {
    }

    public Student(String name, Integer age, Boolean male, BigDecimal money, double rate) {
        this.name = name;
        this.age = age;
        this.male = male;
        this.money = money;
        this.rate = rate;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
