public class ThuatToan {
    // public static void main(String[] args) {
    //     int n = 5;
    //     int k = 3;

    //     int[] a = new int[k];
    //     for (int i = 0; i < k; i++) {
    //         a[i] = i;
    //     }

    //     do {
    //         for (int i = 0; i < k; i++) {
    //             System.out.print((a[i] + 1) + " ");
    //         }
    //         System.out.println();
    //     } while (next(a, k, n));
    // }

    // public static boolean next(int[] a, int k, int n) {
    //     int i = k - 1;

    //     while (i >= 0 && a[i] == n - k + i) {
    //         i--;
    //     }

    //     if (i < 0) return false;

    //     a[i]++;
    //     for (int j = i + 1; j < k; j++) {
    //         a[j] = a[j - 1] + 1;
    //     }
    //     return true;
    // }

    public static void main(String[] args) {
        int[] a = { 5, 4, 3, 2, 1 };
        int n = 5, k = 3;

        for (int i = 0; i < a.length; i++) {
            a[i]--;
        }

        if (nextPermu(a, n)) {
            for (int i = 0; i < n; i++) {
                System.out.print((a[i] + 1) + " ");
            }
        }
    }

    public static boolean nextPermu(int[] a, int n) {
        // tìm điểm đầu tiên a[i] < a[i+1]
        int i = n-2;
        while (i >= 0 && a[i] >= a[i+1]) {
            i--;
        }

        if (i < 0) return false;

        // tìm điểm j > i t/m a[j] > a[i]
        int j = n - 1;
        while (j > i && a[i] >= a[j]) {
            j--;
        }

        // swap a[i] và a[j]
        int t = a[i];
        a[i] = a[j];
        a[j] = t;

        // reverse từ a[i+1] đến a[n-1]
        int l = i+1, r = n-1;
        while (l < r) {
            t = a[l];
            a[l] = a[r];
            a[r] = t;
            l++;
            r--;
        }

        return true;
    }

    public static boolean next(int[] a, int k, int n) {
        int i = k-1;
        while (i >= 0 && a[i] == n - k + i) {
            i--;
        }

        if (i < 0) return false;

        a[i]++;
        for (int j = i+1; j < k; j++) {
            a[j] = a[j-1] + 1;
        }

        return true;
    }
}
