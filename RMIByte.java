import java.rmi.Naming;

import RMI.ByteService;

public class RMIByte {
    public static void main(String[] args) throws Exception {
        ByteService service = (ByteService) Naming.lookup("rmi://203.162.10.109/RMIByteService");

        byte[] data = service.requestData("B22DCCN589", "5i6Jsbwl");
        for (int i = 0; i < data.length; i++) {
            data[i] += data.length;
        }

        service.submitData("B22DCCN589", "5i6Jsbwl", data);
    }
}
