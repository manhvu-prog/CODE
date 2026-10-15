import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NIO_TCP2 {
    static final String HOST = "36.50.135.242";
    static final int PORT = 2211;
    static final String STUDENT_CODE = "B23DCCN535"; // TODO: sửa thành mã sinh viên của bạn
    static final String Q_CODE = "92H6AGIg";

    static void readFully(SocketChannel ch, ByteBuffer buf) throws IOException {
        while (buf.hasRemaining()) {
            if (ch.read(buf) < 0) throw new EOFException("Server closed connection");
        }
    }

    static void sendFrame(SocketChannel ch, String msg) throws IOException {
        byte[] data = msg.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buf = ByteBuffer.allocate(4 + data.length);
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

    // Lấy giá trị chuỗi: "key" : "value"
    static String getString(String json, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"").matcher(json);
        return m.find() ? m.group(1) : "";
    }

    // Lấy giá trị boolean: "key" : true/false
    static int getBool(String json, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*(true|false)").matcher(json);
        return (m.find() && m.group(1).equals("true")) ? 1 : 0;
    }

    public static void main(String[] args) throws IOException {
        try (SocketChannel ch = SocketChannel.open()) {
            ch.connect(new InetSocketAddress(HOST, PORT));

            // a. Gửi studentCode;qCode
            sendFrame(ch, STUDENT_CODE + ";" + Q_CODE);

            // b. Nhận đúng 2 frame, nối lại theo thứ tự
            String json = recvFrame(ch) + recvFrame(ch);
            System.out.println("JSON: " + json);

            // c. Trích xuất event, user, ok
            String event = getString(json, "event");
            String user = getString(json, "user");
            int ok = getBool(json, "ok");

            String result = "event=" + event + ";user=" + user + ";ok=" + ok;
            System.out.println("Send: " + result);
            sendFrame(ch, result);
        } // d. đóng kết nối
    }
}