package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Lap {
    public static String chuanhoa1(String s){
        String res="";
        String[] a=s.split("\\s+");
        res+=a[a.length-1]+" ";
        for(int i=1;i<a.length-1;++i){
            res+=a[i]+" ";
        }
        res+=a[0];
        return res.trim();
    }
    public static String  chuanhoa2(int n){
        String res="";
        String a=Integer.toString(n);
        for(int i=a.length()-1;i>=0;--i){
            res+=a.charAt(i);
        }
        return res;
    }
    public static void main(String[] args) throws Exception {
        Socket socket=new Socket("36.50.135.242",2209);
        ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());

        String ma="B23DCCN535;WtSxsH0p";

        out.writeObject(ma);
        out.flush();

        Laptop a=(Laptop) in.readObject();
        a.name=chuanhoa1(a.name);
        a.quantity= Integer.parseInt(chuanhoa2(a.quantity));

        out.writeObject(a);
        out.flush();

        out.close();
        in.close();
        socket.close();


    }
}
