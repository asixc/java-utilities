package dev.jotxee.model;

public class Item {
    private String itemId;
    private String ean;

    public Item(String itemId, String ean) {
        this.itemId = itemId;
        this.ean = ean;
    }

    // getters y setters
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId='" + itemId + '\'' +
                ", ean='" + ean + '\'' +
                '}';
    }
}