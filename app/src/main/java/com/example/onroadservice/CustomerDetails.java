package com.example.onroadservice;




public class CustomerDetails
{
    String cphone;
    String cname;
    String cvehicle;
    String cfuel;
    String creq;
    String cvehicleno;
    String ctime;
    String clocation;
    private boolean isRejected; // New field to track the status
    private String customerId;

    public CustomerDetails()
    {
        //constructor
    }
    public CustomerDetails(String cname,String cphone, String cvehicle, String cfuel, String creq, String cvehicleno, String ctime, String clocation,boolean isRejected) {
        this.cphone = cphone;
        this.cvehicle = cvehicle;
        this.cfuel = cfuel;
        this.creq = creq;
        this.cvehicleno = cvehicleno;
        this.ctime = ctime;
        this.clocation = clocation;
        this.cname=cname;
        this.isRejected=false;

    }

    public String getCphone() {
        return cphone;
    }

    public String getCname() {
        return cname;
    }

    public String getCvehicle() {
        return cvehicle;
    }

    public String getCfuel() {
        return cfuel;
    }

    public String getCreq() {
        return creq;
    }

    public String getCvehicleno() {
        return cvehicleno;
    }

    public String getCtime() {
        return ctime;
    }

    public String getClocation() {
        return clocation;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public void setRejected(boolean rejected) {
        isRejected = rejected;
    }


}