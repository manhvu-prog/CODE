import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import TCP.Laptop;

public class TCPObjectStream {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2209);

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("B22DCCN589;UKGkXCVJ");

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Laptop laptop = (Laptop) ois.readObject();

        String[] names = laptop.name.trim().split("\\s+");
        String t = names[0];
        names[0] = names[names.length - 1];
        names[names.length - 1] = t;
        laptop.name = String.join(" ", names);

        laptop.quantity = Integer.parseInt(new StringBuilder(laptop.quantity + "").reverse().toString());

        oos.writeObject(laptop);

        socket.close();
    }
}
