import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class DATA_TCP {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("36.50.135.242", 2207);

        DataInputStream in =
                new DataInputStream(socket.getInputStream());

        DataOutputStream out =
                new DataOutputStream(socket.getOutputStream());

        String ma = "B23DCCN535;myCWIwHs";

        // Gửi mã sinh viên + mã câu hỏi
        out.writeUTF(ma);
        out.flush();

        // Nhận 2 số nguyên
        int a = in.readInt();
        int b = in.readInt();

        // Tính tổng và tích
        int tong = a + b;
        int tich = a * b;

        // Gửi tổng trước, tích sau
        out.writeInt(tong);
        out.writeInt(tich);
        out.flush();

        in.close();
        out.close();
        socket.close();
    }
}