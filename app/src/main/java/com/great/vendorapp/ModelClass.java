package com.great.vendorapp;

public class ModelClass {

    String Category;
    String DeliveryCharges;
    String GST;
    String Offer;
    String PriceAmount;
    String ProductName;
    String ImgLink0;
    String ImgLink1;
    String ImgLink2;

    public ModelClass ()
    {

    }

    public ModelClass(String category, String deliveryCharges, String GST, String offer, String priceAmount, String productName, String imgLink0, String imgLink1, String imgLink2) {
        Category = category;
        DeliveryCharges = deliveryCharges;
        this.GST = GST;
        Offer = offer;
        PriceAmount = priceAmount;
        ProductName = productName;
        ImgLink0 = imgLink0;
        ImgLink1 = imgLink1;
        ImgLink2 = imgLink2;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDeliveryCharges() {
        return DeliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        DeliveryCharges = deliveryCharges;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public String getOffer() {
        return Offer;
    }

    public void setOffer(String offer) {
        Offer = offer;
    }

    public String getPriceAmount() {
        return PriceAmount;
    }

    public void setPriceAmount(String priceAmount) {
        PriceAmount = priceAmount;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getImgLink0() {
        return ImgLink0;
    }

    public void setImgLink0(String imgLink0) {
        ImgLink0 = imgLink0;
    }

    public String getImgLink1() {
        return ImgLink1;
    }

    public void setImgLink1(String imgLink1) {
        ImgLink1 = imgLink1;
    }

    public String getImgLink2() {
        return ImgLink2;
    }

    public void setImgLink2(String imgLink2) {
        ImgLink2 = imgLink2;
    }
}
