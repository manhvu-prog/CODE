import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NIO_TCP1 {
    static final String HOST = "36.50.135.242";
    static final int PORT = 2211;
    static final String STUDENT_CODE = "B23DCCN535"; // TODO: sửa thành mã sinh viên của bạn
    static final String Q_CODE = "kJUYtWvi";

    // Đọc đủ dữ liệu vào buffer (vòng lặp readFully)
    static void readFully(SocketChannel ch, ByteBuffer buf) throws IOException {
        while (buf.hasRemaining()) {
            if (ch.read(buf) < 0) throw new EOFException("Server closed connection");
        }
    }

    static void sendFrame(SocketChannel ch, String msg) throws IOException {
        byte[] data = msg.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buf = ByteBuffer.allocate(4 + data.length); // big-endian mặc định
        buf.putInt(data.length);
        buf.put(data);
        buf.flip();
        while (buf.hasRemaining()) ch.write(buf);
    }

    static String recvFrame(SocketChannel ch) throws IOException {
        ByteBuffer lenBuf = ByteBuffer.allocate(4);
        readFully(ch, lenBuf);
        lenBuf.flip();
        int len = lenBuf.getInt();
        ByteBuffer payload = ByteBuffer.allocate(len);
        readFully(ch, payload);
        return new String(payload.array(), StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws IOException {
        try (SocketChannel ch = SocketChannel.open()) {
            ch.connect(new InetSocketAddress(HOST, PORT));

            // a. Gửi studentCode;qCode
            sendFrame(ch, STUDENT_CODE + ";" + Q_CODE);

            // b. Nhận đúng 3 frame và nối lại theo thứ tự
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; i++) sb.append(recvFrame(ch));
            String request = sb.toString();
            System.out.println("HTTP request:\n" + request);

            // c. Trích xuất METHOD, PATH, HOST
            String[] lines = request.split("\r\n");
            String[] first = lines[0].trim().split("\\s+");
            String method = first[0];
            String path = first[1]; // đã gồm query-string
            String host = "";
            for (String line : lines) {
                if (line.toLowerCase().startsWith("host:")) {
                    host = line.substring(5).trim();
                    break;
                }
            }

            String result = method + ";" + path + ";" + host;
            System.out.println("Send: " + result);
            sendFrame(ch, result);
        } // d. đóng kết nối (try-with-resources)
    }
}