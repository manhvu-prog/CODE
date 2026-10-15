import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class DATA_TCP1 {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("36.50.135.242", 2207);

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        String ma = "B23DCCN535;4hcPwbnf";

        out.writeUTF(ma);
        out.flush();

        String s = in.readUTF();
        int d = in.readInt();

        System.out.println(s + " " + d);

        String res = "";

        for (char x : s.toCharArray()) {

            if (Character.isLetter(x)) {
                char base = Character.isUpperCase(x) ? 'A' : 'a';
                char tam = (char) ((x - base - d + 26) % 26 + base);
                res += tam;
            } else {
                res += x;
            }
        }

        System.out.println(res);
        out.writeUTF(res);
        out.flush();

        in.close();
        out.close();
        socket.close();
    }
}