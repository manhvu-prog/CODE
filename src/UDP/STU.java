package UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

import UDP.Student; // Import class Student từ package UDP

public class STU {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        // a. Gửi thông điệp ban đầu
        String studentCode = "B23DCCN535"; // TODO: Đổi thành mã sinh viên của bạn
        String qCode = "nWaNR0ta";
        String reqStr = ";" + studentCode + ";" + qCode;
        byte[] reqData = reqStr.getBytes();
        socket.send(new DatagramPacket(reqData, reqData.length, address, port));

        // b. Nhận thông điệp từ server
        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
        socket.receive(dp);

        // Tách 08 byte đầu chứa requestId[cite: 3]
        byte[] requestId = Arrays.copyOfRange(dp.getData(), 0, 8);

        // Các byte còn lại chứa đối tượng (Dùng dp.getLength() theo lưu ý checklist)[cite: 3]
        byte[] objData = Arrays.copyOfRange(dp.getData(), 8, dp.getLength());

        // Giải mã mảng byte thành đối tượng Student
        Student student = (Student) new ObjectInputStream(new ByteArrayInputStream(objData)).readObject();

        // c. Xử lý chuẩn hóa tên và tạo email
        String[] words = student.name.trim().split("\\s+");
        StringBuilder normalizedName = new StringBuilder();

        // Email: bắt đầu bằng từ cuối cùng (tên) in thường
        String emailPrefix = words[words.length - 1].toLowerCase();

        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            if (w.length() > 0) {
                // Chuẩn hóa tên: Chữ cái đầu in hoa, còn lại in thường
                String normalWord = w.substring(0, 1).toUpperCase() + w.substring(1).toLowerCase();
                normalizedName.append(normalWord).append(" ");

                // Lấy chữ cái đầu tiên của các từ (trừ từ cuối cùng - tên) ghép vào email
                if (i < words.length - 1) {
                    emailPrefix += w.substring(0, 1).toLowerCase();
                }
            }
        }

        // Cập nhật lại thuộc tính cho đối tượng
        student.name = normalizedName.toString().trim();
        student.email = emailPrefix + "@ptit.edu.vn";

        System.out.println("Tên sau chuẩn hóa: " + student.name);
        System.out.println("Email tạo ra: " + student.email);

        // Đóng gói đối tượng đã xử lý trở lại mảng byte
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ObjectOutputStream(baos).writeObject(student);
        byte[] objDataModified = baos.toByteArray();

        // Gộp 08 byte requestId và mảng byte đối tượng để gửi lên server bằng ByteBuffer[cite: 3]
       /* byte[] resData = ByteBuffer.allocate(8 + objDataModified.length)
                .put(requestId)
                .put(objDataModified)
                .array();*/
        // 1. Tạo mảng kết quả có tổng độ dài của 2 mảng
        byte[] resData = new byte[8 + objDataModified.length];

        // 2. Copy 8 byte của requestId vào từ vị trí số 0
        System.arraycopy(requestId, 0, resData, 0, 8);

        // 3. Copy toàn bộ objDataModified vào tiếp nối từ vị trí số 8
        System.arraycopy(objDataModified, 0, resData, 8, objDataModified.length);

        socket.send(new DatagramPacket(resData, resData.length, address, port));

        // d. Đóng socket
        socket.close();
    }
}
