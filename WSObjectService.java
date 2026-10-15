import java.util.Comparator;
import java.util.List;

// Đây là file main mẫu, muốn chạy được cần tạo project như trong netbean

import vn.medianews.ObjectService;
import vn.medianews.ObjectService_Service;
import vn.medianews.EmployeeY;

public class WSObjectService {
    public static void main(String[] args) throws Exception { 
        ObjectService_Service os = new ObjectService_Service(); 
        ObjectService s = os.getObjectServicePort(); 
        List<EmployeeY> ems = s.requestListEmployeeY("B22DCCN589", "GXplRaiG"); 
        ems.sort(Comparator.comparing(e -> e.startDate.toGregorianCalendar().toInstant())); 
        s.submitListEmployeeY("B22DCCN589", "GXplRaiG", ems); 
    }
}