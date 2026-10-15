import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import UDP.Book;

public class UDPObject {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("203.162.10.109");

        byte[] req = ";B22DCCN589;dRArdZY8".getBytes();
        socket.send(new DatagramPacket(req, req.length, address, 2209));

        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
        socket.receive(dp);

        byte[] requestId = Arrays.copyOfRange(dp.getData(), 0, 8);
        byte[] obj = Arrays.copyOfRange(dp.getData(), 8, dp.getLength());

        Book book = (Book) new ObjectInputStream(new ByteArrayInputStream(obj)).readObject();

        book.title = Arrays.stream(book.title.trim().split("\\s+"))
            .map(e -> e.substring(0, 1).toUpperCase() + e.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
        
        String[] names = book.author.trim().split("\\s+");
        book.author = names[0].toUpperCase() + ",";
        for (int i = 1; i < names.length; i++) {
            book.author += " " + names[i];
        }

        StringBuilder sb = new StringBuilder(book.isbn);
        sb.insert(3, "-");
        sb.insert(5, "-");
        sb.insert(8, "-");
        sb.insert(15, "-");
        book.isbn = sb.toString();

        String[] dates = book.publishDate.trim().split("-");
        book.publishDate = dates[1] + "/" + dates[0];

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ObjectOutputStream(baos).writeObject(book);

        byte[] req2 = ByteBuffer.allocate(8 + baos.size())
            .put(requestId).put(baos.toByteArray()).array();

        socket.send(new DatagramPacket(req2, req2.length, address, 2209));

        socket.close();
    }
}
