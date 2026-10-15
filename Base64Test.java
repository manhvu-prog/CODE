import java.util.Base64;

public class Base64Test {
    public static void main(String[] args) {
        byte[] data = {101, 110, 100, 96};
        byte[] b64 = Base64.getEncoder().encode(data);
        for (byte b : b64) {
            System.out.print(b + " ");
        }
        System.out.println();

        byte[] origin = Base64.getDecoder().decode(b64);
        for (byte b : origin) {
            System.out.print(b + " ");
        }
    }
}
