import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_DataType1 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        int port = 2207;

        // a. Gửi thông điệp lần 1 (Nhớ thay bằng mã sinh viên thực tế của bạn)
        String ma = ";B23DCCN535;M35RWcKp";
        DatagramPacket gui = new DatagramPacket(ma.getBytes(), ma.length(), address, port);
        socket.send(gui);

        // b. Nhận thông điệp từ server
        DatagramPacket nhan = new DatagramPacket(new byte[1024], 1024);
        socket.receive(nhan);

        String nhan1 = new String(nhan.getData(), 0, nhan.getLength());
        String[] tmp = nhan1.split(";");
        String requestId = tmp[0];
        String[] numbers = tmp[1].split(",");

        // c. Tìm giá trị lớn nhất (max) và nhỏ nhất (min)
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (String x : numbers) {
            int value = Integer.parseInt(x.trim());
            if (value > max) max = value;
            if (value < min) min = value;
        }

        // Tạo thông điệp gửi đi định dạng: requestId;max,min
        String gui1 = requestId + ";" + max + "," + min;
        DatagramPacket guiLan2 = new DatagramPacket(gui1.getBytes(), gui1.length(), address, port);
        socket.send(guiLan2);

        // d. Đóng socket và kết thúc
        socket.close();
    }
}