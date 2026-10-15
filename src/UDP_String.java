import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

public class UDP_String {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        int port = 2208;

        // a. Gửi thông điệp
        String studentCode = "B23DCCN535"; // Thay bằng mã thật của bạn
        String qCode = "EYzCkNFf";
        String reqStr = ";" + studentCode + ";" + qCode;
        byte[] reqData = reqStr.getBytes();
        socket.send(new DatagramPacket(reqData, reqData.length, address, port));

        // b. Nhận thông điệp
        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
        socket.receive(dp);

        String receivedStr = new String(dp.getData(), 0, dp.getLength()).trim();
        String[] parts = receivedStr.split(";");

        if (parts.length >= 2) {
            String requestId = parts[0];
            String data = parts[1];

            // c. Tìm ký tự xuất hiện nhiều nhất (Ưu tiên ký tự xuất hiện đầu tiên trong chuỗi nếu hòa)
            HashMap<Character, Integer> countMap = new HashMap<>();

            // Bước 1: Đếm tần suất tất cả các ký tự
            for (char c : data.toCharArray()) {
                countMap.put(c, countMap.getOrDefault(c, 0) + 1);
            }

            // Bước 2: Tìm số lần xuất hiện lớn nhất (maxCount)
            int maxCount = 0;
            for (int count : countMap.values()) {
                if (count > maxCount) {
                    maxCount = count;
                }
            }

            // Bước 3: Duyệt lại chuỗi từ đầu để chốt ký tự (maxChar) xuất hiện sớm nhất có tần suất bằng maxCount
            char maxChar = data.charAt(0);
            for (char c : data.toCharArray()) {
                if (countMap.get(c) == maxCount) {
                    maxChar = c;
                    break; // Ngừng luôn khi tìm thấy ký tự đầu tiên thỏa mãn
                }
            }

            // Bước 4: Lấy danh sách vị trí của maxChar
            StringBuilder positions = new StringBuilder();
            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == maxChar) {
                    positions.append(i + 1).append(",");
                }
            }

            // Gửi thông điệp kết quả
            String resStr = requestId + ";" + maxChar + ":" + positions.toString();
            byte[] resData = resStr.getBytes();
            socket.send(new DatagramPacket(resData, resData.length, address, port));
        }

        // d. Đóng socket
        socket.close();
    }
}