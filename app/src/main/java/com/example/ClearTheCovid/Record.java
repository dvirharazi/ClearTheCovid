package com.example.ClearTheCovid;

public class Record {
    private String name;
    private Integer point;
    private String id;

    @Override
    public String toString() {
        return "Record{" +
                "name='" + name + '\'' +
                ", point='" + point + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public Record(String name, Integer point, String id) {
        this.name = name;
        this.point = point;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoint() {
        return point;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int compareTo(Record o1) {
        return this.getPoint().compareTo(o1.getPoint());
    }
}
