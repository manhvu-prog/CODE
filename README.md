## 06/01/2026 - Code giải các bài tập LTM - PTIT
## Đề thi mẫu các dạng
### 1. TCP - Byte Stream
	[Mã câu hỏi (qCode): PrHmkvtP].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2206 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s).
	Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng các luồng byte (InputStream/OutputStream) để trao đổi thông tin theo thứ tự: 
	a.	Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;2B3A6510"
	b.	Nhận dữ liệu từ server là một số nguyên n nhỏ hơn 400. Ví dụ: 7
	c.	Thực hiện các bước sau đây để sinh ra chuỗi từ số nguyên n ban đầu và gửi lên server.
			Bắt đầu với số nguyên nn:
				Nếu n là số chẵn, chia nn cho 2 để tạo ra số tiếp theo trong dãy.
				Nếu n là số lẻ và khác 1, thực hiện phép toán n=3*n+1 để tạo ra số tiếp theo.
			Lặp lại quá trình trên cho đến khi n=1, tại đó dừng thuật toán.
	Kết quả là một dãy số liên tiếp, bắt đầu từ n ban đầu, kết thúc tại 1 và độ dài của chuỗi theo format "chuỗi kết quả; độ dài"  Ví dụ: kết quả với n = 7 thì dãy: 7 22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1; 17;  
	d.	Đóng kết nối và kết thúc chương trình.

### 2. TCP - Character Stream
	[Mã câu hỏi (qCode): 3Ymeh4c6].  [Loại bỏ ký tự đặc biệt, trùng và giữ nguyên thứ tự xuất hiện] Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản dưới đây:
	a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;7D6265E3"
	b.	Nhận một chuỗi ngẫu nhiên từ server
	c.	Loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của ký tự. Gửi chuỗi đã được xử lý lên server.
	d.	Đóng kết nối và kết thúc chương trình

### 3. TCP - Data Stream
	[Mã câu hỏi (qCode): OGEVDKfN].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu sinh viên xây dựng chương trình client để tương tác với server, sử dụng các luồng data (DataInputStream và DataOutputStream) để trao đổi thông tin theo thứ tự sau:
	a. Gửi mã sinh viên và mã câu hỏi: Chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;D68C93F7".
	b. Nhận một số nguyên hệ thập phân từ server. Ví dụ:: 15226.
	c. Chuyển đổi số nguyên nhận được sang hệ nhị phân và thập lục phân, ghép thành chuỗi và gửi lên server. Ví dụ: 15226 sẽ thành "11101101111010;3B7A"
	d. Đóng kết nối: Kết thúc chương trình sau khi gửi kết quả chuyển đổi.

### 4. TCP - Object Stream
	[Mã câu hỏi (qCode): UKGkXCVJ].  Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
	a) Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: “lenovo thinkpad T520” bị chuyển thành “T520 thinkpad lenovo”
	b) Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899

	Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) để gửi/nhận và sửa các thông tin bị sai của sản phẩm. Chi tiết dưới đây:
	a) Đối tượng trao đổi là thể hiện của lớp Laptop được mô tả như sau
		  •	Tên đầy đủ của lớp: TCP.Laptop
		  •	Các thuộc tính: id int, code String, name String, quantity int
		  •	Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
		  •	Trường dữ liệu: private static final long serialVersionUID = 20150711L; 
	b)	Tương tác với server theo kịch bản
	1)	Gửi đối tượng là chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;5AD2B818"
	2)	Nhận một đối tượng là thể hiện của lớp Laptop từ server
	3)	Sửa các thông tin sai của sản phẩm về tên và số lượng.  Gửi đối tượng vừa được sửa sai lên server
	4)	Đóng socket và kết thúc chương trình.

### 5. UDP - Data Type
	[Mã câu hỏi (qCode): msRgQz9i].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
	a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B21DCCN795;ylrhZ6UM".
	b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;n;k;z1,z2,...,zn", trong đó:
		requestId là chuỗi ngẫu nhiên duy nhất.
		n là số phần tử của mảng.
		k là kích thước cửa sổ trượt (k < n).
		z1 đến zn là n phần tử là số nguyên của mảng.

	c. Thực hiện tìm giá trị lớn nhất trong mỗi cửa sổ trượt với kích thước k trên mảng số nguyên nhận được, và gửi thông điệp lên server theo định dạng "requestId;max1,max2,...,maxm", trong đó max1 đến maxm là các giá trị lớn nhất tương ứng trong mỗi cửa sổ.
	Ví dụ: "requestId;5;3;1,5,2,3,4"
	Kết quả: "requestId;5,5,4"
	d. Đóng socket và kết thúc chương trình.

### 6. UDP - String
	[Mã câu hỏi (qCode): xytNHHdk].  [Loại bỏ ký tự đặc biệt và ký tự trùng giữ nguyên thứ tự xuất hiện]
	Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208 . Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
	a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode”. Ví dụ: ";B15DCCN001;B34D51E0"
	b.	Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;str1;str2".
	•	requestId là chuỗi ngẫu nhiên duy nhất
	•	str1,str2 lần lượt là chuỗi thứ nhất và chuỗi thứ hai
	c.	Loại bỏ các ký tự trong chuỗi thứ nhất mà xuất hiện trong chuỗi thứ hai, giữ nguyên thứ tự xuất hiện. Gửi thông điệp là một chuỗi lên server theo định dạng "requestId;strOutput", trong đó chuỗi strOutput là chuỗi đã được xử lý ở trên.
	d.	Đóng socket và kết thúc chương trình.

### 7. UDP - Object
	[Mã câu hỏi (qCode): dRArdZY8].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:

	Đối tượng trao đổi là thể hiện của lớp UDP.Book được mô tả:

		Tên đầy đủ lớp: UDP.Book
		Các thuộc tính: id (String), title (String), author (String), isbn (String), publishDate (String)
		Hàm khởi tạo:
			public Book(String id, String title, String author, String isbn, String publishDate)
		Trường dữ liệu: private static final long serialVersionUID = 20251107L

	Thực hiện:

	a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B23DCCN005;eQkvAeId"

	b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Book từ server. Trong đó, các thuộc tính id, title, author, isbn, và publishDate đã được thiết lập sẵn.

	c. Thực hiện:
			Chuẩn hóa title: viết hoa chữ cái đầu của mỗi từ.
			Chuẩn hóa author theo định dạng "HỌ, Tên".
			Chuẩn hóa mã ISBN theo định dạng "978-3-16-148410-0"
			Chuyển đổi publishDate từ yyyy-mm-dd sang mm/yyyy.
	d. Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Book đã được sửa đổi.
	Đóng socket và kết thúc chương trình.

### 8. RMI - Byte
	[Mã câu hỏi (qCode): 5i6Jsbwl].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu nhị phân.
	Giao diện từ xa:
	public interface ByteService extends Remote {
	public byte[] requestData(String studentCode, String qCode) throws RemoteException;
	public void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;
	}
	Trong đó:
	•	Interface ByteService được viết trong package RMI.
	•	Đối tượng cài đặt giao diện từ xa ByteService được đăng ký với RegistryServer với tên là: RMIByteService.
	Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhị phân nhận được từ RMI Server:
	a. Triệu gọi phương thức requestData để nhận một mảng dữ liệu nhị phân (byte[]) từ server, đại diện cho một chuỗi văn bản ASCII.
	b. Thực hiện mã hóa Caesar cho mảng dữ liệu nhị phân bằng cách dịch chuyển mỗi byte trong mảng đi một số bước cố định trong bảng mã ASCII. Số bước dịch chuyển là số ký tự ASCII trong mảng dữ liệu.
		Ví dụ: Nếu dữ liệu nhị phân nhận được là [72, 101, 108, 108, 111] (tương ứng với chuỗi "Hello"), chương trình sẽ thực hiện mã hóa Caesar với độ dịch là 5. Kết quả mã hóa là mảng [77, 108, 113, 113, 116], tương ứng với chuỗi "Mlqqt".
	c. Triệu gọi phương thức submitData để gửi mảng dữ liệu đã được mã hóa bằng Caesar trở lại server.
	d. Kết thúc chương trình client.

### 9. RMI - Character
	[Mã câu hỏi (qCode): lZwNnzTz].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý chuỗi.
	Giao diện từ xa:
	public interface CharacterService extends Remote {
	public String requestCharacter(String studentCode, String qCode) throws RemoteException;
	public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
	}
	Trong đó:
	• Interface CharacterService được viết trong package RMI.
	• Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên là: RMICharacterService.
	Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi được nhận từ RMI Server:
	a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server với định dạng: "Chuỗi văn bản đầu vào".
	b. Thực hiện thao tác đếm tần số xuất hiện của từng ký tự trong chuỗi đầu vào. Kết quả trả về là danh sách các ký tự kèm theo số lần xuất hiện của mỗi ký tự.
	Ví dụ: Chuỗi ban đầu "Hello world" -> Kết quả đếm tần số ký tự: {"H": 1, "e": 1, "l": 3, "o": 2, " ": 1, "w": 1, "r": 1, "d": 1}.
	c. Triệu gọi phương thức submitCharacter để gửi kết quả đếm tần số ký tự trở lại server dưới dạng chuỗi kết quả đã được định dạng.
	d. Kết thúc chương trình client.

### 10. RMI - Object
	[Mã câu hỏi (qCode): N9vvJNQJ].  Một chương trình (tạm gọi là RMI Server) cung cấp các mã khuyến mãi sản phẩm ngẫu nhiên cho sinh viên, được mô tả như sau:
	•	Giao diện từ xa
		public interface ObjectService extends Remote {
			public Serializable requestObject(String studentCode, String qAlias) throws RemoteException;

			public void submitObject(String studentCode, String qAlias, Serializable object) throws RemoteException;
		}
	•	Lớp ProductX gồm các thuộc tính id String, code String, discountCode String, discount int.
	o	Một hàm khởi dựng với đầy đủ các thuộc tính liệt kê ở trên
	o	Trường dữ liệu: private static final long serialVersionUID = 20171107; 
	•	Đối tượng triệu gọi từ xa được đăng ký RegistryServer với tên: RMIObjectService
	•	Tất cả các lớp được viết trong package RMI

	Yêu cầu là xây dựng một chương trình client thực hiện các tương tác với hệ thống phần mềm ở trên theo kịch bản dưới đây:
	1.	  Triệu gọi phương thức từ xa requestObject từ RMI Server với tham số đầu vào là mã sinh viên, mã câu để nhận về đối tượng ProductX
	2.    Nhận về đối tượng ProductX từ RMI Server với giá trị ban đầu đã được thiết lập. Tính tổng các chữ số nằm trong chuỗi mã giảm giá (discountCode) để ra giá trị được khuyến mãi của sản phẩm và cập nhật giá trị của khuyến mãi (discount)
	3.	Triệu gọi phương thức từ xa submitObject với tham số đầu vào là đối tượng Product đã được cập nhật đầy đủ thông tin giá trị khuyến mãi
	4.	Kết thúc chương trình

### 11. WS - Character Service
	[Mã câu hỏi (qCode): MYlpDqoc].  Một dịch vụ web (hỗ trợ SOAP version 1.1) được định nghĩa và mô tả trong tệp CharacterService.wsdl, được triển khai trên server tại URL http://<Exam_IP>:8080/JNPWS/CharacterService?wsdl để xử lý các bài toán về chuỗi và ký tự.
	Yêu cầu: Viết chương trình tại máy trạm (WS client) để giao tiếp với CharacterService thực hiện các công việc sau:
	a. Triệu gọi phương thức requestString với tham số đầu vào là mã sinh viên (studentCode) và mã câu hỏi (qCode) để nhận về một chuỗi (String) từ server. Chuỗi có thể chứa các từ được phân tách bằng dấu cách hoặc dấu gạch dưới.
	b. Chuyển đổi chuỗi đã nhận được sang ba định dạng khác nhau:
	•	PascalCase: Mỗi từ bắt đầu bằng chữ in hoa, không có khoảng cách giữa các từ.
	•	camelCase: Từ đầu tiên viết thường, các từ tiếp theo viết hoa chữ cái đầu và viết liền nhau.
	•	snake_case: Các từ được viết thường và nối với nhau bằng dấu gạch dưới.
	c. Triệu gọi phương thức submitCharacterStringArray(String studentCode, String qCode, List<String> data) để gửi mảng kết quả chứa ba chuỗi đã định dạng trở lại server, theo thứ tự: PascalCase, camelCase, snake_case.
	Ví dụ: Nếu chuỗi nhận được từ phương thức requestCharacter là "hello world example", các chuỗi kết quả sẽ là:
	•	PascalCase: "HelloWorldExample"
	•	camelCase: "helloWorldExample"
	•	snake_case: "hello_world_example"
	Mảng kết quả sẽ là ["HelloWorldExample", "helloWorldExample", "hello_world_example"], và sẽ được gửi lại server qua phương thức submitCharacter.
	d. Kết thúc chương trình client.

### 12. WS - Data Service
	[Mã câu hỏi (qCode): 8LNJOlYb].  Một dịch vụ web (hỗ trợ SOAP version 1.1) được định nghĩa và mô tả trong tệp DataService?wsdl, được triển khai trên server tại URL http://<Exam_IP>:8080/JNPWS/DataService?wsdl để xử lý các bài toán với dữ liệu nguyên thủy.
	Yêu cầu: Viết chương trình tại máy trạm (WS client) để giao tiếp với DataService thực hiện các công việc sau:
	a. Triệu gọi phương thức getData với tham số đầu vào là mã sinh viên (studentCode) và mã câu hỏi (qCode) để nhận về một danh sách số nguyên (List<Integer>) từ server.
	b. Chuyển đổi số nguyên nhận được từ hệ thập phân sang hệ nhị phân và biểu diễn kết quả dưới dạng chuỗi nhị phân.
	c. Triệu gọi phương thức submitDataStringArray(String studentCode, String qCode, List<String> data) để gửi chuỗi nhị phân đã chuyển đổi trở lại server.
	Ví dụ: Nếu mỗi số nguyên nhận được từ phương thức getData, chương trình client sẽ chuyển đổi sang chuỗi nhị phân là "1010", và gửi mảng chuỗi này trở lại server qua phương thức submitData.
	d. Kết thúc chương trình client.

### 13. WS - Object Service
	[Mã câu hỏi (qCode): GXplRaiG].  Một dịch vụ web (hỗ trợ SOAP version 1.1) được định nghĩa và mô tả trong tệp ObjectService.wsdl, được triển khai trên server tại URL http://<Exam_IP>:8080/JNPWS/ObjectService?wsdl để xử lý các bài toán với đối tượng.
	Yêu cầu: Viết chương trình tại máy trạm (WS client) để giao tiếp với ObjectService thực hiện các công việc sau:
	a. Triệu gọi phương thức requestListEmployeeY với tham số đầu vào là mã sinh viên (studentCode) và mã câu hỏi (qCode) để nhận về một danh sách đối tượng EmployeeY từ server. Mỗi đối tượng EmployeeY có các thuộc tính:
	•	name: kiểu String, đại diện cho tên của nhân viên.
	•	startDate: kiểu Date, đại diện cho ngày bắt đầu làm việc của nhân viên.
	b. Sắp xếp danh sách EmployeeY theo thứ tự thâm niên từ cao đến thấp (người có ngày bắt đầu làm việc sớm nhất sẽ đứng đầu danh sách).
		Nếu hai nhân viên có cùng ngày bắt đầu làm việc, giữ nguyên thứ tự ban đầu của họ trong danh sách.
	c. Triệu gọi phương thức submitListEmployeeY(String studentCode, String qCode, List<EmployeeY> data) để gửi danh sách nhân viên đã sắp xếp trở lại server.
	d. Kết thúc chương trình client.