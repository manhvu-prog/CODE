package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cus {

    public static String chuanhoa3(String a) {
        String[] s = a.trim().toLowerCase().split("\\s+");

        String res = s[s.length - 1].toUpperCase() + ", ";

        for (int i = 0; i < s.length - 1; i++) {
            res += Character.toUpperCase(s[i].charAt(0))
                    + s[i].substring(1) + " ";
        }

        return res.trim();
    }

    public static String chuanhoa4(String a) {
        String[] s = a.split("-");
        return s[1] + "/" + s[0] + "/" + s[2];
    }

    public static String chuanhoa5(String a) {
        String[] s = a.trim().toLowerCase().split("\\s+");

        String res = "";

        for (int i = 0; i < s.length - 1; i++) {
            res += s[i].charAt(0);
        }

        res += s[s.length - 1];

        return res;
    }

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("36.50.135.242", 2209);

        ObjectOutputStream out =
                new ObjectOutputStream(socket.getOutputStream());
        out.flush();

        ObjectInputStream in =
                new ObjectInputStream(socket.getInputStream());

        String ma = "B23DCCN535;SH0rGzTf";

        out.writeObject(ma);
        out.flush();

        Customer a = (Customer) in.readObject();
        a.userName = chuanhoa5(a.name);
        a.name = chuanhoa3(a.name);
        a.dayOfBirth = chuanhoa4(a.dayOfBirth);

        System.out.println("NAME: " + a.name);
        System.out.println("DOB: " + a.dayOfBirth);
        System.out.println("USERNAME: " + a.userName);


        out.writeObject(a);
        out.flush();

        in.close();
        out.close();
        socket.close();
    }
}