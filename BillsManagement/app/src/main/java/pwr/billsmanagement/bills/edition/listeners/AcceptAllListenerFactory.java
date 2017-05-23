package pwr.billsmanagement.bills.edition.listeners;

import pwr.billsmanagement.bills.edition.listeners.params.DefineParams;
import pwr.billsmanagement.bills.edition.listeners.params.EditParams;
import pwr.billsmanagement.bills.edition.listeners.params.ListenerParams;

/**
 * Created by Squier on 22.05.2017.
 */

public class AcceptAllListenerFactory {

    public enum ListenerType {
        DEFINE, EDIT
    }

    public static AcceptAllListener getListener(ListenerType type, ListenerParams params) {
        switch (type) {
            case DEFINE: {

                AssemblyShredsListener listener = new AssemblyShredsListener((DefineParams) params);
                return listener;
            }
            case EDIT: {
                AssemblyFinalsListener listener = new AssemblyFinalsListener((EditParams) params);
                return listener;
            }
            default:
                return null;
        }
    }
}
