package pwr.billsmanagement.webApp.models;
import java.util.Date;
import java.util.List;

/**
 * Created by E6520 on 2017-05-10.
 */

public class Bill {
    public int billID;
    public Date purchaseDate;
    public List<BillEntry> entries;
    public Shop shop;

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<BillEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<BillEntry> entries) {
        this.entries = entries;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
