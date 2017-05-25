package pwr.billsmanagement.localDatabase.dao;

import java.util.ArrayList;
import java.util.List;

import pwr.billsmanagement.localDatabase.dataObjects.BillEntries;

/**
 * Created by E6520 on 2017-05-20.
 */

public class BillEntriesDAOImpl {

    public static List<BillEntries> billEntries = new ArrayList<>();


    public List<BillEntries> list(){
        return billEntries;
    }
}
