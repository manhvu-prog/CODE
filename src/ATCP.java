import java.io.*;
import java.net.Socket;

public class ATCP {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("36.50.135.242", 2208);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );

        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
        );

        String ma = "B23DCCN535;89IHUF9E";

        out.write(ma);
        out.newLine();
        out.flush();

        String s = in.readLine().trim();

        int[] cnt = new int[1024];

        for (char x : s.toCharArray()) {
            if (x != ' ') {
                cnt[x]++;
            }
        }

        String res = "";
        boolean[] used = new boolean[1024];

        for (char x : s.toCharArray()) {
            if (x != ' ' && cnt[x] > 1 && !used[x]) {
                res += x + ":" + cnt[x] + ",";
                used[x] = true;
            }
        }

        out.write(res);
        out.newLine();
        out.flush();

        in.close();
        out.close();
        socket.close();
    }
}