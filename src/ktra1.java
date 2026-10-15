import java.util.Scanner;
class SanPham{
    static int dem=1;
    String ma,ten,hangSX;
    int soLuong;
    double gia;
    SanPham(String ten, int soLuong,String hangSX, double gia){
        this.ten=ten;
        this.soLuong=soLuong;
        this.hangSX=hangSX;
        this.gia=gia;
        ma=hangSX.toUpperCase()+"-"+String.format("%03d",dem++);
    }
    String getMa(){
        return ma;
    }
    double getThanhTien(){
        if(soLuong<20)
            return soLuong*gia;
        else
            return soLuong*gia*0.9;
    }
    public String toString(){
        return getMa()+" "+ten+" "+soLuong+" "+String.format("%.1f",gia)+" "+String.format("%.1f",getThanhTien());
    }

}
public class ktra1 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int n=Integer.parseInt(sc.nextLine());
        SanPham s;
        for (int i=0;i<n;++i){
            s=new SanPham(sc.nextLine(),Integer.parseInt(sc.nextLine()),sc.nextLine(),Double.parseDouble(sc.nextLine()));
            System.out.println(s.toString());
        }

    }
}
