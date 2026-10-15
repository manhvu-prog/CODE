import java.rmi.Naming;
import java.util.ArrayList;

import RMI.DataService;

public class RMIData {
    public static void main(String[] args) throws Exception {
        DataService service = (DataService) Naming.lookup("rmi://203.162.10.109/RMIDataService");

        int num = (int) service.requestData("B22DCCN589", "LdQZ17Pq");
        ArrayList<CharSequence> a = new ArrayList<>();
        while (num > 0) {
            if (num >= 10) {
                num -= 10;
                a.add("10");
            }
            else if (num >= 5) {
                num -= 5;
                a.add("5");
            }
            else if (num >= 2) {
                num -= 2;
                a.add("2");
            }
            else {
                num -= 1;
                a.add("1");
            }
        }

        service.submitData("B22DCCN589", "LdQZ17Pq", a.size() + "; " + String.join(",", a));
    }
}
