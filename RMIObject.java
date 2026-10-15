import java.rmi.Naming;

import RMI.ObjectService;
import RMI.ProductX;

public class RMIObject {
    public static void main(String[] args) throws Exception {
        ObjectService service = (ObjectService) Naming.lookup("rmi://203.162.10.109/RMIObjectService");
        ProductX productX = (ProductX) service.requestObject("B22DCCN589", "N9vvJNQJ");

        productX.discount = 0;
        for (Character c : productX.discountCode.toCharArray()) {
            if (Character.isDigit(c)) {
                productX.discount += c - '0';
            }
        }

        service.submitObject("B22DCCN589", "N9vvJNQJ", productX);
    }
}
