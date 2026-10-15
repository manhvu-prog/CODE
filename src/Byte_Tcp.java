import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class Byte_Tcp {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("36.50.135.242", 2206);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        // a. Gửi mã sinh viên và mã câu hỏi
        String ma = "B23DCCN535;TUNGFRr9";

        out.write(ma.getBytes());
        out.flush();

        // b. Nhận dữ liệu
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);

        String data = new String(buffer, 0, len).trim();

        String[] s = data.split(",");

        ArrayList<Integer> ar = new ArrayList<>();

        for (String x : s) {
            ar.add(Integer.parseInt(x.trim()));
        }

        // c. Tìm khoảng cách nhỏ nhất
        Collections.sort(ar);

        long min = Long.MAX_VALUE;
        long x1 = 0;
        long x2 = 0;

        for (int i = 1; i < ar.size(); i++) {

            long distance = (long) ar.get(i) - ar.get(i - 1);

            if (distance < min) {
                min = distance;
                x1 = ar.get(i - 1);
                x2 = ar.get(i);
            }
        }

        // Gửi: khoảng cách nhỏ nhất, số thứ nhất, số thứ hai
        String result = min + "," + x1 + "," + x2;

        out.write(result.getBytes());
        out.flush();

        // d. Đóng kết nối
        in.close();
        out.close();
        socket.close();
    }
}