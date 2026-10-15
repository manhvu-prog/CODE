package UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class CUS {
    public static String chuanhoa(String name){
        String ans="";
        String[] tmp=name.trim().split("\\s+");
        ans+=tmp[tmp.length-1].toUpperCase()+", ";
        for(int i=0;i<tmp.length-1;++i){
            ans+=tmp[i].toUpperCase().charAt(0)+tmp[i].toLowerCase().substring(1)+" ";
        }
        return ans.trim();
    }

    // Đã sửa lại thuật toán chuẩn hóa ngày sinh để đảo ngày và tháng
    public static String chuanhoa2(String ngaysinh){
        String[] ngay=ngaysinh.trim().split("-");
        // ngay[0] là mm, ngay[1] là dd, ngay[2] là yyyy
        return ngay[1] + "/" + ngay[0] + "/" + ngay[2];
    }

    public static String chuanhoa3(String name){
        String[] tmp=name.trim().split("\\s+");
        String ans="";
        for(int i=0;i<tmp.length-1;++i){
            ans+=tmp[i].toLowerCase().charAt(0);
        }
        ans+=tmp[tmp.length-1].toLowerCase();
        return ans;
    }

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("36.50.135.242");
        String ma=";B23DCCN535;UMeS9pPo"; // Sửa thành mã SV của bạn

        // SỬA LỖI: Đổi cổng thành 2209 theo yêu cầu đề bài
        DatagramPacket gui = new DatagramPacket(ma.getBytes(), ma.length(), address, 2209);
        socket.send(gui);

        DatagramPacket nhan = new DatagramPacket(new byte[1024], 1024);
        socket.receive(nhan);

        byte[] request = Arrays.copyOfRange(nhan.getData(), 0, 8);
        byte[] obj = Arrays.copyOfRange(nhan.getData(), 8, nhan.getLength());

        Customer customer = (Customer) new ObjectInputStream(new ByteArrayInputStream(obj)).readObject();
        customer.userName = chuanhoa3(customer.name);
        customer.name = chuanhoa(customer.name);
        customer.dayOfBirth = chuanhoa2(customer.dayOfBirth);


        System.out.println("Tên sau khi chuẩn hóa: " + customer.name);
        System.out.println("Ngày sinh sau khi chuẩn hóa: " + customer.dayOfBirth);
        System.out.println("Username: " + customer.userName);

        // ================= ĐOẠN ĐÓNG GÓI VÀ GỬI LÊN SERVER =================

        // Bước 1: Ghi Object Customer đã sửa đổi ra mảng byte
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ObjectOutputStream(baos).writeObject(customer);
        byte[] objDataModified = baos.toByteArray();

        // Bước 2: Ghép mảng 8 byte request và mảng byte đối tượng lại bằng ByteBuffer[cite: 3]
        byte[] resData = ByteBuffer.allocate(8 + objDataModified.length)
                .put(request)
                .put(objDataModified)
                .array();

        // Bước 3: Đóng gói Packet và gửi lên Server (Nhớ là port 2209)
        DatagramPacket gui2 = new DatagramPacket(resData, resData.length, address, 2209);
        socket.send(gui2);
        System.out.println("Đã gửi thành công lên server!");

        // Bước 4: Đóng socket kết thúc chương trình
        socket.close();
    }
}