package com.example.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

public class Record {
    private long id;
    private String projectName;
    private double deposit;
    private double balance;
    private String paymentDate;
    private String type;
    private String status;

    public Record(long id, String projectName, double deposit, double balance,
                  String paymentDate, String type, String status) {
        this.id = id;
        this.projectName = projectName;
        this.deposit = deposit;
        this.balance = balance;
        this.paymentDate = paymentDate;
        this.type = type;
        this.status = status;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public double getDeposit() { return deposit; }
    public void setDeposit(double deposit) { this.deposit = deposit; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", id);
        object.put("projectName", projectName);
        object.put("deposit", deposit);
        object.put("balance", balance);
        object.put("paymentDate", paymentDate);
        object.put("type", type);
        object.put("status", status);
        return object;
    }

    public static Record fromJson(JSONObject object) throws JSONException {
        return new Record(object.getLong("id"), object.getString("projectName"),
                object.getDouble("deposit"), object.getDouble("balance"),
                object.getString("paymentDate"), object.getString("type"),
                object.getString("status"));
    }
}
