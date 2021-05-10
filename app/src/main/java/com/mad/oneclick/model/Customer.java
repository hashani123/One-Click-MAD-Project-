package com.mad.oneclick.model;

public class Customer {
    private String userid;
    private String fname;
    private String lname;
    private String phoneno;
    private String email;
    private String nic;

    public Customer(String userid, String fname, String lname, String phoneno, String email, String nic) {
        this.userid = userid;
        this.fname = fname;
        this.lname = lname;
        this.phoneno = phoneno;
        this.email = email;
        this.nic = nic;
    }

    public Customer() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
}
