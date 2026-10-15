import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class UDP_DataType {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        int port = 2207;

        // a. Gửi thông điệp lần 1
        String ma = ";B23DCCN535;mNC4uIAN";
        DatagramPacket gui = new DatagramPacket(ma.getBytes(), ma.length(), address, port);
        socket.send(gui);

        // b. Nhận thông điệp từ server
        DatagramPacket nhan = new DatagramPacket(new byte[1024], 1024);
        socket.receive(nhan);

        String nhan1 = new String(nhan.getData(), 0, nhan.getLength());
        String[] tmp = nhan1.split(";");
        String requestId = tmp[0];
        int n = Integer.parseInt(tmp[1]);
        String[] sonhan = tmp[2].split(",");

        // Sử dụng mảng boolean kích thước n + 1 để đánh dấu từ 1 đến n
        boolean[] a = new boolean[n + 1];
        for(String x : sonhan){
            int value = Integer.parseInt(x.trim());
            if(value <= n) {
                a[value] = true;
            }
        }

        // c. Tìm kiếm các giá trị còn thiếu
        ArrayList<String> missingValues = new ArrayList<>();
        for(int i = 1; i <= n; ++i){
            if(!a[i]) {
                missingValues.add(String.valueOf(i));
            }
        }

        // Tạo chuỗi gửi đi định dạng: requestId;B1,B2,...,Bm
        String c = String.join(",", missingValues);
        String gui1 = requestId + ";" + c;

        // Gửi kết quả lên server
        DatagramPacket guiLan2 = new DatagramPacket(gui1.getBytes(), gui1.length(), address, port);
        socket.send(guiLan2);

        // d. Đóng socket và kết thúc
        socket.close();
    }
}