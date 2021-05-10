package com.mad.oneclick.model;

public class ItemDetails {
    private String itemid;
    private String itemimg;
    private String itemname;
    private String itemprice;
    private String itemmodel;
    private String itemdetails;

    public ItemDetails() {
    }

    public ItemDetails(String itemid, String itemimg, String itemname, String itemprice, String itemmodel, String itemdetails) {
        this.itemid = itemid;
        this.itemimg = itemimg;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.itemmodel = itemmodel;
        this.itemdetails = itemdetails;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemimg() {
        return itemimg;
    }

    public void setItemimg(String itemimg) {
        this.itemimg = itemimg;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public String getItemmodel() {
        return itemmodel;
    }

    public void setItemmodel(String itemmodel) {
        this.itemmodel = itemmodel;
    }

    public String getItemdetails() {
        return itemdetails;
    }

    public void setItemdetails(String itemdetails) {
        this.itemdetails = itemdetails;
    }
}
