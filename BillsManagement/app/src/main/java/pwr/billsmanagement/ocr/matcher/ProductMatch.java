package pwr.billsmanagement.ocr.matcher;

/**
 * Created by E6520 on 2017-04-08.
 */
public class ProductMatch implements Comparable {

    private String ocrResult;
    private String name;
    private int match;

    public ProductMatch() {
    }

    public ProductMatch(String ocrResult, String name, int match) {
        this.ocrResult = ocrResult;
        this.name = name;
        this.match = match;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMatch() {
        return match;
    }

    public void setMatch(int match) {
        this.match = match;
    }

    @Override
    public String toString() {
        return "ProductMatch{" +
                "ocrResult='" + ocrResult + '\'' +
                ", name='" + name + '\'' +
                ", match=" + match +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof ProductMatch) {
            if(((ProductMatch)o).match > this.match) {
                return 1;
            } else if(((ProductMatch)o).match == this.match) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
