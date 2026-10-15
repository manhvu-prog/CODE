import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCPDataStream {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2207);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("B22DCCN589;OGEVDKfN");

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int n = dis.readInt();

        String b = Integer.toBinaryString(n);
        String h = Integer.toHexString(n).toUpperCase();

        dos.writeUTF(b + ";" + h);

        socket.close();
    }
}
