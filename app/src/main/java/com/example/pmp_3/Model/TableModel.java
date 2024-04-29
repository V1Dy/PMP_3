package com.example.pmp_3.Model;

public class TableModel {

    private String table;
    private int id , status, beerCount = 0;

    public String getTable() {
        return table;
    }

    public void setTable(String table) { this.table = table; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setBeerCount(int beerCount){ this.beerCount = beerCount; }

    public int getBeerCount() { return beerCount; }
}
