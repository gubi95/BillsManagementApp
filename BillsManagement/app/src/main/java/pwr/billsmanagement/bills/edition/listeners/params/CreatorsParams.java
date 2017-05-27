package pwr.billsmanagement.bills.edition.listeners.params;

import java.util.Map;

import pwr.billsmanagement.bills.edition.view.ViewCreator;

/**
 * Created by Squier on 23.05.2017.
 */

public class CreatorsParams {

    private ViewCreator[] creators;
    private Map<String, Integer> creatorsMap;

    public CreatorsParams(ViewCreator... creators) {
        this.creators = creators;
    }

    public ViewCreator[] getCreators() {
        return creators;
    }

    public Map<String, Integer> getCreatorsMap() {
        return creatorsMap;
    }

    public void setCreatorsMap(Map<String, Integer> creatorsMap) {
        this.creatorsMap = creatorsMap;
    }
}
