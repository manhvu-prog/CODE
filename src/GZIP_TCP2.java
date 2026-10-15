import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIP_TCP2 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2210;
        String studentCode = "B23DCCN535";   // mã SV thật, chữ D thường
        String qCode = "IoAqV6vi";

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(5000);

            // Tạo output trước, syncFlush = true để flush đẩy dữ liệu nén đi ngay
            GZIPOutputStream out = new GZIPOutputStream(socket.getOutputStream(), true);

            // a. Gửi studentCode;qCode
            out.write((studentCode + ";" + qCode + "\n").getBytes(StandardCharsets.UTF_8));
            out.flush();
            System.out.println("Đã gửi mã SV");

            // b. Nhận chuỗi (đọc từng byte tới '\n', không trim)
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                GZIPInputStream in = new GZIPInputStream(socket.getInputStream());
                int b;
                while ((b = in.read()) != -1 && b != '\n') bos.write(b);
            } catch (IOException e) {
                System.out.println("Cảnh báo khi đọc: " + e);
            }
            String received = new String(bos.toByteArray(), StandardCharsets.UTF_8);
            System.out.println("Nhận: [" + received + "]");
            if (received.isEmpty()) return;

            // c. Sắp xếp ký tự tăng dần theo mã ASCII
            char[] chars = received.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);

            out.write((sorted + "\n").getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.finish();
            System.out.println("Đã gửi: [" + sorted + "]");

            Thread.sleep(500);   // để server kịp đọc trước khi đóng socket
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}