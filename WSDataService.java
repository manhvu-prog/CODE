import java.util.ArrayList;
import java.util.List;

// Đây là file main mẫu, muốn chạy được cần tạo project như trong netbean

import vn.medianews.DataService;
import vn.medianews.DataService_Service;

public class WSDataService {
    public static void main(String[] args) throws Exception {
        DataService_Service ds = new DataService_Service();
        DataService s = ds.getDataServicePort();
        List<Integer> nums = s.getData("B22DCCN589", "8LNJOlYb");
        
        ArrayList<String> res = new ArrayList<>();
        for (Integer num : nums) {
            String b = "";
            while (num > 0) {                
                b = num%2 + b;
                num /= 2;
            }
            res.add(b);
        }
        
        s.submitDataStringArray("B22DCCN589", "8LNJOlYb", res);
    }
}