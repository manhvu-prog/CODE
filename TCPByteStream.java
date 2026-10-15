import java.net.Socket;

public class TCPByteStream {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2206);

        socket.getOutputStream().write("B22DCCN589;PrHmkvtP".getBytes());

        byte[] buff = new byte[1024];
        int bytes = socket.getInputStream().read(buff);

        int n = Integer.parseInt(new String(buff, 0, bytes));

        int c = 0;
        String s = "";
        while (n != 1) {
            s += n + "";
            c++;
            if (n%2 == 0) {
                n /= 2;
            } else {
                n = 3*n + 1;
            }
        }
        s += "1";
        c++;

        socket.getOutputStream().write(String.format("%s; %d", s, c).getBytes());

        socket.close();
    }
}
