import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIP_TCP1 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2210;
        String studentCode = "B23DCCN535";
        String qCode = "UJ7kN3va";

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(5000);

            GZIPOutputStream out = new GZIPOutputStream(socket.getOutputStream(), true);
            out.write((studentCode + ";" + qCode + "\n").getBytes(StandardCharsets.UTF_8));
            out.flush();
            System.out.println("Đã gửi mã SV");

            // Đọc từng byte tới '\n', chịu được EOF giữa chừng
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                GZIPInputStream in = new GZIPInputStream(socket.getInputStream());
                int b;
                while ((b = in.read()) != -1 && b != '\n') bos.write(b);
            } catch (IOException e) {
                System.out.println("Cảnh báo khi đọc: " + e);
            }
            String received = new String(bos.toByteArray(), StandardCharsets.UTF_8);
            System.out.println("Nhận (" + bos.size() + " byte): [" + received + "]");
            if (received.isEmpty()) return;

            String reversed = new StringBuilder(received).reverse().toString();
            String base64 = Base64.getEncoder()
                    .encodeToString(reversed.getBytes(StandardCharsets.UTF_8));
            String result = reversed + "|" + base64;

            out.write((result + "\n").getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.finish();
            System.out.println("Đã gửi: [" + result + "]");

            Thread.sleep(500); // cho server kịp đọc trước khi đóng socket
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}