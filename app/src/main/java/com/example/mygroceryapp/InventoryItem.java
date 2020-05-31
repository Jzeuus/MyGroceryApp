package com.example.mygroceryapp;

public class InventoryItem {
    private long id;
    private String itemId;
    private String itemName;
    private String pantryLife;
    private String barcodeNum;

    public InventoryItem(long id, String itemId, String itemName, String pantryLife, String upc)
    {
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.pantryLife = pantryLife;
        this.barcodeNum = upc;
    }

    public InventoryItem( String itemName, String itemId, String pantryLife)
    {
        this.itemId = itemId;
        this.itemName = itemName;
        this.pantryLife = pantryLife;
    }


    public long getId() {
        return id;
    }

    public String getPantryLife(){ return pantryLife; }
    public String getItemName(){ return itemName; }
    public String getBarcodeNum(){ return barcodeNum; }
    public String getItemId(){ return itemId; }

    public boolean equals(Object otherDept)
    {
        return this.id == ((InventoryItem)otherDept).id;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return "row: " + id + " itemId: " + itemId + " - Name: " + itemName + " - dopLife: " + pantryLife +"- UPC: "+ barcodeNum;
    }
}