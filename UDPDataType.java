import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

public class UDPDataType {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("203.162.10.109");

        byte[] req = ";B22DCCN589;msRgQz9i".getBytes();
        socket.send(new DatagramPacket(req, req.length, address, 2207));

        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
        socket.receive(dp);

        String[] s = new String(dp.getData()).split(";");
        int n = Integer.parseInt(s[1]);
        int k = Integer.parseInt(s[2]);
        int[] a = Arrays.stream(s[3].trim().split(","))
            .mapToInt(Integer::parseInt).toArray();
        
        ArrayList<CharSequence> res = new ArrayList<>();
        for (int i = 0; i < n-k+1; i++) {
            int max = a[i];
            for (int j = 1; j < k; j++) {
                max = Math.max(max, a[i+j]);
            }
            res.add(max + "");
        }

        byte[] req2 = String.format("%s;%s", s[0], String.join(",", res)).getBytes();
        socket.send(new DatagramPacket(req2, req2.length, address, 2207));
    }
}
