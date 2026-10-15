import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.Buffer;

public class Summary {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2207);

        //Input/OutputStream
        socket.getOutputStream().write("B22DCCN589".getBytes());
        byte[] buff1 = new byte[1024];
        int bytes = socket.getInputStream().read(buff1);
        String s1 = new String(buff1, 0, bytes);

        // BufferReader/Writer
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write("B22DCCN589\n");
        bw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s2 = br.readLine();

        // DataOutputStream/DataInputStream
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("B22DCCN589");
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int s3 = dis.readInt();

        //ObjectOutputStream/ObjectInputStream
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("B22DCCNt89");
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Object s4 = (Object) ois.readObject();

        //UDP 
        DatagramSocket udpSocket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("203.162.10.109");

        byte[] req1 = "NBkdjsdks".getBytes();
        udpSocket.send(new DatagramPacket(req1, req1.length, address, 2209));

        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
        udpSocket.receive(dp);
        Object s5 = (Object) new ObjectInputStream(new ByteArrayInputStream(dp.getData())).readObject();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ObjectOutputStream(baos).writeObject(s5);
        baos.toByteArray();



        socket.close();
    }
}
