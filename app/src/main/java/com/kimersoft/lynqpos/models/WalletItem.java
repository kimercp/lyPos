package com.kimersoft.lynqpos.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;


/**
 * Created by Gadour on 9/1/17.
 */

public class WalletItem implements Serializable {


    private long id;
    private String account;
    private String asset;
    private int balance; // total quantity
    private String category;
    private String code;
    private String description;
    private String issuer;
    private LinkedHashMap<String,String> walletModifiers;
   // private ArrayList<LinkedHashMap<String, LinkedHashMap<String, String>>> modifiers;
    private String name;
    private String nameShort;
    private String price;
    private String path;
    private int actualBalance;//quantity
    private String rating;
    private String custumer;
    private String iconUrl;


    private String version;
    private String subcategory;
    private ArrayList<String> subCategoris;
    private int  bulk;
    private String actualPrice;



    public WalletItem() {

    }

    public String getCustumer() {
        return custumer;
    }

    public void setCustumer(String custumer) {
        this.custumer = custumer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getBulk() {
        return bulk;
    }

    public void setBulk(int bulk) {
        this.bulk = bulk;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public int getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(int actualBalance) {
        this.actualBalance = actualBalance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public ArrayList<String> getSubCategoris() {
        return subCategoris;
    }

    public void setSubCategoris(ArrayList<String> subCategoris) {
        this.subCategoris = subCategoris;
    }

    public LinkedHashMap<String, String> getWalletModifiers() {
        return walletModifiers;
    }

    public void setWalletModifiers(LinkedHashMap<String, String> walletModifiers) {
        this.walletModifiers = walletModifiers;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

   /* public ArrayList<LinkedHashMap<String, LinkedHashMap<String, String>>> getModifiers() {
        return modifiers;
    }

    public void setModifiers(ArrayList<LinkedHashMap<String, LinkedHashMap<String, String>>> modifiers) {
        this.modifiers = modifiers;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalletItem that = (WalletItem) o;
        return asset.equals(that.asset);
    }

    @Override
    public int hashCode() {
        return asset.hashCode();
    }

    @Override
    public String toString() {
        return "WalletItem{" +
                "client=" + name
                +"asset "+asset+
                "price "+actualPrice
                +"quantity "+actualBalance
                +"issuer "+issuer+
                '}';
    }
    public static Float getCartPrice(ArrayList<WalletItem> walletItemCarts){

        Float totalPrice=0f;
        for(WalletItem walletItemCart:walletItemCarts){
            Float itemPrice= Float.parseFloat(walletItemCart.getActualPrice());
            totalPrice+=itemPrice;
        }
        return totalPrice;
    }

    /*Comparator for sorting the list by rating*/
    public static Comparator<WalletItem> ratingComparator = new Comparator<WalletItem>() {

        public int compare(WalletItem s1, WalletItem s2) {
            try {
                Float r1 =  Float.parseFloat(s1.getRating());
                Float r2 =  Float.parseFloat(s2.getRating());
                // ascending order
                // return r1.compareTo(r2);
                // descending order
                return r2.compareTo(r1);
            }catch (NumberFormatException e){
                return  0;
            }
        }};
}
