package com.example.productdistributionsystem;

public class Items {
    private String itemName;
    private String itemCategory;
    private String itemPrice;
    private String itemBarCode;


    public Items() {
        //empty constructor is mandatory
    }

    public Items(String itemName, String itemCategory, String itemPrice, String itemBarCode) {

        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemPrice = itemPrice;
        this.itemBarCode = itemBarCode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemBarCode() {
        return itemBarCode;
    }
}
