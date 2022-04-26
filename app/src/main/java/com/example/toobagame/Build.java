package com.example.toobagame;
//Класс Здания
public class Build {
    private String name;// Отображаемое название здания на карте
    private String id_Build;
    private int price;//цена покупки
    private int income;//ежедневный доход
    private int level;// текущий уровень здания
    private String point_x;//ширина
    private String point_y;//долгота
    private String owner;// id_владельца

    public Build(){}

    public Build(String name, String id_Build, int price, int income, int level, String owner, String point_x, String point_y) {
        this.name = name;
        this.id_Build = id_Build;
        this.price = price;
        this.income = income;
        this.level = level;
        this.owner = owner;
        this.point_x = point_x;
        this.point_y = point_y;
    }


    public String getPoint_x() {
        return point_x;
    }

    public void setPoint_x(String point_x) {
        this.point_x = point_x;
    }

    public String getPoint_y() {
        return point_y;
    }

    public void setPoint_y(String point_y) {
        this.point_y = point_y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id_Build;
    }

    public void setId(String id) {
        this.id_Build = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
