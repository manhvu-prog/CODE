import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.SQLOutput;

public class UDP_String1 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket=new DatagramSocket();
        InetAddress address=InetAddress.getByName("36.50.135.242");
        int port=2208;

        String ma=";B23DCCN535;0KCrJrO5";
        DatagramPacket Gui=new DatagramPacket(ma.getBytes(),ma.length(),address,port);
        socket.send(Gui);

        DatagramPacket Nhan=new DatagramPacket(new byte[1024],1024);
        socket.receive(Nhan);

        String manhan=new String(Nhan.getData());
        System.out.println(manhan);
        String[] chuoi=manhan.split(";");
        String request=chuoi[0];

        String[] ten=chuoi[1].split("\\s+");
        String ans="";
        for(String x:ten){
            ans+=x.toUpperCase().charAt(0)+x.toLowerCase().substring(1)+" ";

        }
        ans=ans.trim();

        String gui=request+";"+ans;
        DatagramPacket Gui1=new DatagramPacket(gui.getBytes(),gui.length(),address,port);
        socket.send(Gui1);
        socket.close();
    }
}
