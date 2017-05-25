package pwr.billsmanagement.webApp.models;

/**
 * Created by E6520 on 2017-05-10.
 */

public class Shop {
    public int shopID;
    public String shopName;
    public User userOwner;

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }
}
