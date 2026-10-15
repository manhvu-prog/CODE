import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPTest {
    public static void main(String[] args) throws Exception {
        byte[] origin = {101, 110, 100, 96};

        // Nén
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(baos);
        gzipOutputStream.write(origin);
        gzipOutputStream.close();
        byte[] zipped = baos.toByteArray();
        print(zipped);

        // Giải nén
        GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(zipped));
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytes;
        while ((bytes = gzipInputStream.read(buffer)) != -1) {
            baos2.write(buffer, 0, bytes);
        }
        gzipInputStream.close();
        print(baos2.toByteArray());
    }

    public static void print(byte[] a) {
        for (byte b : a) {
            System.out.print(b + " ");
        }
        System.out.println();
    }
}
