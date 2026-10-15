import java.util.ArrayList;
import java.util.List;

// Đây là file main mẫu, muốn chạy được cần tạo project như trong netbean

import vn.medianews.CharacterService;
import vn.medianews.CharacterService_Service;

public class WSCharacterService {
    public static void main(String[] args) {
        CharacterService_Service cs = new CharacterService_Service();
        CharacterService s = cs.getCharacterServicePort();
        
        String[] data = s.requestString("B22DCCN589", "MYlpDqoc").trim().split("\\s+");
        
        String s1 = data[0].substring(0, 1).toUpperCase() + data[0].substring(1).toLowerCase();
        String s2 = data[0].toLowerCase();
        String s3 = data[0].toLowerCase();
        
        for (int i = 1; i < data.length; i++) {
            s1 += data[i].substring(0, 1).toUpperCase() + data[i].substring(1).toLowerCase();
            s2 += data[i].substring(0, 1).toUpperCase() + data[i].substring(1).toLowerCase();
            s3 += "_" + data[i].toLowerCase();
        }
        
        List<String> res = new ArrayList<>();
        res.add(s1);
        res.add(s2);
        res.add(s3);
        
        s.submitCharacterStringArray("B22DCCN589", "MYlpDqoc", res);
    }
}
