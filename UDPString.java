import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;

public class UDPString {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("203.162.10.109");

        byte[] req = ";B22DCCN589;xytNHHdk".getBytes();
        socket.send(new DatagramPacket(req, req.length, address, 2208));

        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
        socket.receive(dp);

        String[] res = new String(dp.getData()).split(";");
        HashSet<Character> set = new HashSet<>();
        for (Character c : res[2].toCharArray()) {
            set.add(c);
        }

        String s = "";
        for (Character c : res[1].toCharArray()) {
            if (!set.contains(c)) s += c;
        }

        byte[] req2 = (res[0] + ";" + s).getBytes();
        socket.send(new DatagramPacket(req2, req2.length, address, 2208));

        socket.close();
    }
}
