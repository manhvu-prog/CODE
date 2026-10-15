# Lập Trình Mạng - Tổng Hợp Code + Đề Thi PTIT

> Repo: `LapTrinhMang_Code-main` - Code giải các bài tập LTM - PTIT (06/01/2026)
> Server thi: `203.162.10.109` | Mã SV mẫu: `B22DCCN589`

## Mục lục
- [1. Cấu trúc thư mục](#1-cấu-trúc-thư-mục)
- [2. Cấu hình chung](#2-cấu-hình-chung)
- [3. TCP](#3-tcp)
  - [3.1 TCP Byte Stream](#31-tcp---byte-stream)
  - [3.2 TCP Character Stream](#32-tcp---character-stream)
  - [3.3 TCP Data Stream](#33-tcp---data-stream)
  - [3.4 TCP Object Stream + Laptop](#34-tcp---object-stream)
- [4. UDP](#4-udp)
  - [4.1 UDP Data Type](#41-udp---data-type)
  - [4.2 UDP String](#42-udp---string)
  - [4.3 UDP Object + Book](#43-udp---object)
- [5. RMI](#5-rmi)
  - [5.1 RMI Byte](#51-rmi---byte)
  - [5.2 RMI Data (đổi tiền)](#52-rmi---data-đổi-tiền)
  - [5.3 RMI Object (ProductX)](#53-rmi---object)
- [6. Web Service (SOAP)](#6-web-service-soap)
  - [6.1 WS Character Service](#61-ws---character-service)
  - [6.2 WS Data Service](#62-ws---data-service)
  - [6.3 WS Object Service](#63-ws---object-service)
- [7. Tiện ích / Ôn tập](#7-tiện-ích--ôn-tập)
  - [7.1 Summary (mẫu kết nối nhanh)](#71-summary-mẫu-kết-nối-nhanh)
  - [7.2 Base64Test](#72-base64test)
  - [7.3 GZIPTest](#73-gziptest)
  - [7.4 ThuatToan (sinh tổ hợp / hoán vị)](#74-thuattoan-sinh-tổ-hợp--hoán-vị)

---

## 1. Cấu trúc thư mục

```
LapTrinhMang_Code-main/
├── TCPByteStream.java
├── TCPCharacterStream.java
├── TCPDataStream.java
├── TCPObjectStream.java
├── UDPDataType.java
├── UDPString.java
├── UDPObject.java
├── RMIByte.java
├── RMIData.java
├── RMIObject.java
├── WSCharacterService.java
├── WSDataService.java
├── WSObjectService.java
├── Base64Test.java
├── GZIPTest.java
├── Summary.java
├── ThuatToan.java
├── TCP/
│   └── Laptop.java
├── UDP/
│   └── Book.java
└── RMI/
    ├── ByteService.java
    ├── ObjectService.java
    └── ProductX.java
```

## 2. Cấu hình chung

- **IP Server:** `203.162.10.109`
- **TCP ports:** 2206 (Byte), 2207 (Data), 2208 (Character), 2209 (Object)
- **UDP ports:** 2207 (DataType), 2208 (String), 2209 (Object)
- **RMI:** `rmi://203.162.10.109/RMIByteService`, `RMIDataService`, `RMIObjectService`, `RMICharacterService`
- **WS (SOAP 1.1):** `http://<Exam_IP>:8080/JNPWS/{CharacterService,DataService,ObjectService}?wsdl`
- **Đổi mã SV + qCode** trước khi nộp bài. Trong code mẫu đang dùng `B22DCCN589`.
- **WS chạy trên NetBeans:** phải tạo Web Service Client từ file `.wsdl` để sinh package `vn.medianews.*`, sau đó copy code trong `WS*.java` vào hàm main.

---

## 3. TCP

### 3.1 TCP - Byte Stream

**Đề:** `[qCode: PrHmkvtP]` - Port **2206**, dùng `InputStream/OutputStream`, timeout 5s.
1. Gửi `"studentCode;qCode"`. VD: `"B16DCCN999;2B3A6510"`
2. Nhận số nguyên `n < 400`. VD: `7`
3. Sinh dãy Collatz từ `n`:
   - `n` chẵn → `n/2`
   - `n` lẻ, khác 1 → `3*n+1`
   - Dừng khi `n==1`. Gửi lên `"dãy; độ dài"`. VD với `n=7`: `7 22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1; 17`
4. Đóng kết nối.

**File:** `TCPByteStream.java`
```java
import java.net.Socket;

public class TCPByteStream {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2206);

        socket.getOutputStream().write("B22DCCN589;PrHmkvtP".getBytes());

        byte[] buff = new byte[1024];
        int bytes = socket.getInputStream().read(buff);

        int n = Integer.parseInt(new String(buff, 0, bytes));

        int c = 0;
        String s = "";
        while (n != 1) {
            s += n + "";
            c++;
            if (n%2 == 0) {
                n /= 2;
            } else {
                n = 3*n + 1;
            }
        }
        s += "1";
        c++;

        socket.getOutputStream().write(String.format("%s; %d", s, c).getBytes());

        socket.close();
    }
}
```

### 3.2 TCP - Character Stream

**Đề:** `[qCode: 3Ymeh4c6]` - Port **2208**, dùng `BufferedReader/BufferedWriter`.
1. Gửi `"studentCode;qCode"`. VD: `"B15DCCN999;7D6265E3"`
2. Nhận chuỗi ngẫu nhiên từ server.
3. Loại bỏ ký tự đặc biệt, số, ký tự trùng, giữ nguyên thứ tự xuất hiện. Chỉ giữ `Character.isLetter(c)` và chưa xuất hiện. Gửi chuỗi đã xử lý lên server.
4. Đóng kết nối.

**File:** `TCPCharacterStream.java`
```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashSet;

public class TCPCharacterStream {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2208);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write("B22DCCN589;3Ymeh4c6\n");
        bw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s = br.readLine();

        String res = "";
        HashSet<Character> set = new HashSet<>();
        for (Character c : s.toCharArray()) {
            if (Character.isLetter(c) && !set.contains(c)) {
                res += c;
                set.add(c);
            }
        }

        bw.write(res + "\n");
        bw.flush();

        socket.close();
    }
}
```

### 3.3 TCP - Data Stream

**Đề:** `[qCode: OGEVDKfN]` - Port **2207**, dùng `DataInputStream/DataOutputStream`, timeout 5s.
1. Gửi `"studentCode;qCode"`. VD: `"B15DCCN999;D68C93F7"`
2. Nhận số nguyên thập phân. VD: `15226`
3. Chuyển sang nhị phân + thập lục phân (in hoa), gửi `"nhị phân;thập lục phân"`. VD: `"11101101111010;3B7A"`
4. Đóng kết nối.

**File:** `TCPDataStream.java`
```java
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCPDataStream {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2207);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("B22DCCN589;OGEVDKfN");

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int n = dis.readInt();

        String b = Integer.toBinaryString(n);
        String h = Integer.toHexString(n).toUpperCase();

        dos.writeUTF(b + ";" + h);

        socket.close();
    }
}
```

### 3.4 TCP - Object Stream

**Đề:** `[qCode: UKGkXCVJ]` - Port **2209**, dùng `ObjectInputStream/ObjectOutputStream`.
- Lỗi cần sửa:
  - a) `name` bị đảo từ đầu và từ cuối. VD: `"lenovo thinkpad T520"` → `"T520 thinkpad lenovo"` (cần đảo lại).
  - b) `quantity` bị đảo ngược số. VD: `9981` → `1899` (reverse chuỗi số).
- Kịch bản:
  1. Gửi `String` object `"studentCode;qCode"`. VD: `"B15DCCN999;5AD2B818"`
  2. Nhận `TCP.Laptop`
  3. Sửa `name` + `quantity`, gửi lại.
  4. Đóng socket.

**File:** `TCP/Laptop.java`
```java
package TCP;

import java.io.Serializable;

public class Laptop implements Serializable {
    private static final long serialVersionUID = 20150711L;
    public int id, quantity;
    public String code, name;
    
    public Laptop(int id, int quantity, String code, String name) {
        this.id = id;
        this.quantity = quantity;
        this.code = code;
        this.name = name;
    }
}
```

**File:** `TCPObjectStream.java`
```java
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import TCP.Laptop;

public class TCPObjectStream {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2209);

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("B22DCCN589;UKGkXCVJ");

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Laptop laptop = (Laptop) ois.readObject();

        String[] names = laptop.name.trim().split("\\s+");
        String t = names[0];
        names[0] = names[names.length - 1];
        names[names.length - 1] = t;
        laptop.name = String.join(" ", names);

        laptop.quantity = Integer.parseInt(new StringBuilder(laptop.quantity + "").reverse().toString());

        oos.writeObject(laptop);

        socket.close();
    }
}
```

---

## 4. UDP

### 4.1 UDP - Data Type

**Đề:** `[qCode: msRgQz9i]` - Port UDP **2207**.
1. Gửi `";studentCode;qCode"`. VD: `";B21DCCN795;ylrhZ6UM"`
2. Nhận `"requestId;n;k;z1,z2,...,zn"` (n phần tử, cửa sổ trượt k, k<n).
3. Tìm max mỗi cửa sổ trượt size k, gửi `"requestId;max1,max2,...,maxm"`. VD: `"requestId;5;3;1,5,2,3,4"` → `"requestId;5,5,4"`
4. Đóng socket.

**File:** `UDPDataType.java`
```java
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

public class UDPDataType {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("203.162.10.109");

        byte[] req = ";B22DCCN589;msRgQz9i".getBytes();
        socket.send(new DatagramPacket(req, req.length, address, 2207));

        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
        socket.receive(dp);

        String[] s = new String(dp.getData()).split(";");
        int n = Integer.parseInt(s[1]);
        int k = Integer.parseInt(s[2]);
        int[] a = Arrays.stream(s[3].trim().split(","))
            .mapToInt(Integer::parseInt).toArray();
        
        ArrayList<CharSequence> res = new ArrayList<>();
        for (int i = 0; i < n-k+1; i++) {
            int max = a[i];
            for (int j = 1; j < k; j++) {
                max = Math.max(max, a[i+j]);
            }
            res.add(max + "");
        }

        byte[] req2 = String.format("%s;%s", s[0], String.join(",", res)).getBytes();
        socket.send(new DatagramPacket(req2, req2.length, address, 2207));
    }
}
```

### 4.2 UDP - String

**Đề:** `[qCode: xytNHHdk]` - Port UDP **2208**.
1. Gửi `";studentCode;qCode"`. VD: `";B15DCCN001;B34D51E0"`
2. Nhận `"requestId;str1;str2"`
3. Loại bỏ các ký tự trong `str1` mà xuất hiện trong `str2`, giữ nguyên thứ tự. Gửi `"requestId;strOutput"`.
4. Đóng socket.

**File:** `UDPString.java`
```java
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;

public class UDPString {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("203.162.10.109");

        byte[] req = ";B22DCCN589;xytNHHdk".getBytes();
        socket.send(new DatagramPacket(req, req.length, address, 2208));

        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
        socket.receive(dp);

        String[] res = new String(dp.getData()).split(";");
        HashSet<Character> set = new HashSet<>();
        for (Character c : res[2].toCharArray()) {
            set.add(c);
        }

        String s = "";
        for (Character c : res[1].toCharArray()) {
            if (!set.contains(c)) s += c;
        }

        byte[] req2 = (res[0] + ";" + s).getBytes();
        socket.send(new DatagramPacket(req2, req2.length, address, 2208));

        socket.close();
    }
}
```

### 4.3 UDP - Object

**Đề:** `[qCode: dRArdZY8]` - Port UDP **2209**.
- Class `UDP.Book`: `id, title, author, isbn, publishDate`, `serialVersionUID = 20251107L`, ctor `Book(id,title,author,isbn,publishDate)`.
- Kịch bản:
  1. Gửi `";studentCode;qCode"`. VD: `";B23DCCN005;eQkvAeId"`
  2. Nhận: 08 byte đầu `requestId` + các byte còn lại là object `Book`.
  3. Chuẩn hóa:
     - `title`: viết hoa chữ cái đầu mỗi từ (còn lại viết thường).
     - `author`: `"HỌ, Tên"` — VD code: `names[0].toUpperCase() + "," + " " + names[1..]`.
     - `isbn`: chèn `-` thành dạng `"978-3-16-148410-0"` (insert tại 3,5,8,15).
     - `publishDate`: `yyyy-mm-dd` → `mm/yyyy`.
  4. Gửi lại: 08 byte `requestId` + object `Book` đã sửa.
- Đóng socket.

**File:** `UDP/Book.java`
```java
package UDP;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 20251107L;
    public String id, title, author, isbn, publishDate;
    
    public Book(String id, String title, String author, String isbn, String publishDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }
}
```

**File:** `UDPObject.java`
```java
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import UDP.Book;

public class UDPObject {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("203.162.10.109");

        byte[] req = ";B22DCCN589;dRArdZY8".getBytes();
        socket.send(new DatagramPacket(req, req.length, address, 2209));

        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
        socket.receive(dp);

        byte[] requestId = Arrays.copyOfRange(dp.getData(), 0, 8);
        byte[] obj = Arrays.copyOfRange(dp.getData(), 8, dp.getLength());

        Book book = (Book) new ObjectInputStream(new ByteArrayInputStream(obj)).readObject();

        book.title = Arrays.stream(book.title.trim().split("\\s+"))
            .map(e -> e.substring(0, 1).toUpperCase() + e.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
        
        String[] names = book.author.trim().split("\\s+");
        book.author = names[0].toUpperCase() + ",";
        for (int i = 1; i < names.length; i++) {
            book.author += " " + names[i];
        }

        StringBuilder sb = new StringBuilder(book.isbn);
        sb.insert(3, "-");
        sb.insert(5, "-");
        sb.insert(8, "-");
        sb.insert(15, "-");
        book.isbn = sb.toString();

        String[] dates = book.publishDate.trim().split("-");
        book.publishDate = dates[1] + "/" + dates[0];

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ObjectOutputStream(baos).writeObject(book);

        byte[] req2 = ByteBuffer.allocate(8 + baos.size())
            .put(requestId).put(baos.toByteArray()).array();

        socket.send(new DatagramPacket(req2, req2.length, address, 2209));

        socket.close();
    }
}
```

---

## 5. RMI

### 5.1 RMI - Byte

**Đề:** `[qCode: 5i6Jsbwl]` - `RMIByteService`.
```java
public interface ByteService extends Remote {
  public byte[] requestData(String studentCode, String qCode) throws RemoteException;
  public void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;
}
```
- `requestData` nhận `byte[]` ASCII.
- Mã hóa Caesar: dịch mỗi byte đi `data.length` bước (số ký tự ASCII trong mảng). VD: `[72,101,108,108,111] ("Hello")` + shift 5 → `[77,108,113,113,116] ("Mlqqt")`.
- `submitData` gửi lại.

**File:** `RMI/ByteService.java`
```java
package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ByteService extends Remote {
public byte[] requestData(String studentCode, String qCode) throws RemoteException;
public void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;
}
```

**File:** `RMIByte.java`
```java
import java.rmi.Naming;

import RMI.ByteService;

public class RMIByte {
    public static void main(String[] args) throws Exception {
        ByteService service = (ByteService) Naming.lookup("rmi://203.162.10.109/RMIByteService");

        byte[] data = service.requestData("B22DCCN589", "5i6Jsbwl");
        for (int i = 0; i < data.length; i++) {
            data[i] += data.length;
        }

        service.submitData("B22DCCN589", "5i6Jsbwl", data);
    }
}
```

### 5.2 RMI - Data (đổi tiền)

**Đề trong code:** `[qCode: LdQZ17Pq]` - `RMIDataService` (tham khảo, đề gốc Character/Data khác nhau theo ca).
- `requestData` nhận `int num`.
- Đổi tiền tham lam với mệnh giá `10,5,2,1`: trừ dần, lưu list. VD code trả về `"số_tờ; 10,5,..."`.
- `submitData(studentCode,qCode, "size; list")`.

> Lưu ý: Đề Character gốc `[lZwNnzTz]` là đếm tần số ký tự `{"H":1,...}` qua `requestCharacter/submitCharacter`. Code `RMIData.java` dưới đây là biến thể đổi tiền, giữ nguyên để ôn tập.

**File:** `RMIData.java`
```java
import java.rmi.Naming;
import java.util.ArrayList;

import RMI.DataService;

public class RMIData {
    public static void main(String[] args) throws Exception {
        DataService service = (DataService) Naming.lookup("rmi://203.162.10.109/RMIDataService");

        int num = (int) service.requestData("B22DCCN589", "LdQZ17Pq");
        ArrayList<CharSequence> a = new ArrayList<>();
        while (num > 0) {
            if (num >= 10) {
                num -= 10;
                a.add("10");
            }
            else if (num >= 5) {
                num -= 5;
                a.add("5");
            }
            else if (num >= 2) {
                num -= 2;
                a.add("2");
            }
            else {
                num -= 1;
                a.add("1");
            }
        }

        service.submitData("B22DCCN589", "LdQZ17Pq", a.size() + "; " + String.join(",", a));
    }
}
```

**Đề Character gốc để tham khảo:**
```java
public interface CharacterService extends Remote {
  public String requestCharacter(String studentCode, String qCode) throws RemoteException;
  public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
}
// VD: "Hello world" -> {"H":1,"e":1,"l":3,"o":2," ":1,"w":1,"r":1,"d":1}
```

### 5.3 RMI - Object

**Đề:** `[qCode: N9vvJNQJ]` - `RMIObjectService`, class `RMI.ProductX`.
```java
public interface ObjectService extends Remote {
  public Serializable requestObject(String studentCode, String qAlias) throws RemoteException;
  public void submitObject(String studentCode, String qAlias, Serializable object) throws RemoteException;
}
// ProductX: id String, code String, discountCode String, discount int, serialVersionUID=20171107
```
- Tính tổng các chữ số trong `discountCode` → gán `discount`, `submitObject` lại.

**File:** `RMI/ProductX.java`
```java
package RMI;

import java.io.Serializable;

public class ProductX implements Serializable {
    private static final long serialVersionUID = 20171107;
    public int discount;
    public String id, code, discountCode;

    public ProductX(int discount, String id, String code, String discountCode) {
        this.discount = discount;
        this.id = id;
        this.code = code;
        this.discountCode = discountCode;
    }
}
```

**File:** `RMI/ObjectService.java`
```java
package RMI;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObjectService extends Remote {
    public Serializable requestObject(String studentCode, String qAlias) throws RemoteException;

    public void submitObject(String studentCode, String qAlias, Serializable object) throws RemoteException;
}
```

**File:** `RMIObject.java`
```java
import java.rmi.Naming;

import RMI.ObjectService;
import RMI.ProductX;

public class RMIObject {
    public static void main(String[] args) throws Exception {
        ObjectService service = (ObjectService) Naming.lookup("rmi://203.162.10.109/RMIObjectService");
        ProductX productX = (ProductX) service.requestObject("B22DCCN589", "N9vvJNQJ");

        productX.discount = 0;
        for (Character c : productX.discountCode.toCharArray()) {
            if (Character.isDigit(c)) {
                productX.discount += c - '0';
            }
        }

        service.submitObject("B22DCCN589", "N9vvJNQJ", productX);
    }
}
```

---

## 6. Web Service (SOAP)

> Cả 3 file WS chỉ là main mẫu. Muốn chạy phải tạo Project NetBeans → New Web Service Client từ `...?wsdl` để sinh `vn.medianews.*`.

### 6.1 WS - Character Service

**Đề:** `[qCode: MYlpDqoc]` - `CharacterService.wsdl` tại `http://<Exam_IP>:8080/JNPWS/CharacterService?wsdl`.
1. `requestString(studentCode,qCode)` nhận chuỗi (từ cách nhau bằng space hoặc `_`).
2. Chuyển sang 3 dạng:
   - `PascalCase`: `HelloWorldExample`
   - `camelCase`: `helloWorldExample`
   - `snake_case`: `hello_world_example`
3. `submitCharacterStringArray(studentCode,qCode, [Pascal,camel,snake])`.

**File:** `WSCharacterService.java`
```java
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
```

### 6.2 WS - Data Service

**Đề:** `[qCode: 8LNJOlYb]` - `DataService?wsdl` tại `http://<Exam_IP>:8080/JNPWS/DataService?wsdl`.
1. `getData(studentCode,qCode)` nhận `List<Integer>`.
2. Chuyển mỗi số thập phân sang nhị phân (string). VD: `10` → `"1010"`.
3. `submitDataStringArray(studentCode,qCode, List<String>)`.

**File:** `WSDataService.java`
```java
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
```

### 6.3 WS - Object Service

**Đề:** `[qCode: GXplRaiG]` - `ObjectService.wsdl` tại `http://<Exam_IP>:8080/JNPWS/ObjectService?wsdl`.
1. `requestListEmployeeY(studentCode,qCode)` nhận `List<EmployeeY>` (`name String`, `startDate Date`).
2. Sort thâm niên cao→thấp (startDate sớm nhất đứng đầu, trùng giữ nguyên thứ tự).
3. `submitListEmployeeY(studentCode,qCode, sortedList)`.

**File:** `WSObjectService.java`
```java
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
```

---

## 7. Tiện ích / Ôn tập

### 7.1 Summary (mẫu kết nối nhanh)

Tổng hợp 5 cách mở stream TCP + UDP trong 1 file để chép nhanh khi thi. **File:** `Summary.java`
```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.Buffer;

public class Summary {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2207);

        //Input/OutputStream
        socket.getOutputStream().write("B22DCCN589".getBytes());
        byte[] buff1 = new byte[1024];
        int bytes = socket.getInputStream().read(buff1);
        String s1 = new String(buff1, 0, bytes);

        // BufferReader/Writer
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write("B22DCCN589\n");
        bw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s2 = br.readLine();

        // DataOutputStream/DataInputStream
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("B22DCCN589");
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        int s3 = dis.readInt();

        //ObjectOutputStream/ObjectInputStream
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject("B22DCCNt89");
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Object s4 = (Object) ois.readObject();

        //UDP 
        DatagramSocket udpSocket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("203.162.10.109");

        byte[] req1 = "NBkdjsdks".getBytes();
        udpSocket.send(new DatagramPacket(req1, req1.length, address, 2209));

        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
        udpSocket.receive(dp);
        Object s5 = (Object) new ObjectInputStream(new ByteArrayInputStream(dp.getData())).readObject();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ObjectOutputStream(baos).writeObject(s5);
        baos.toByteArray();



        socket.close();
    }
}
```

### 7.2 Base64Test

**File:** `Base64Test.java`
```java
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
```

### 7.3 GZIPTest

**File:** `GZIPTest.java`
```java
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
```

### 7.4 ThuatToan (sinh tổ hợp / hoán vị)

Hàm `next(a,k,n)` sinh tổ hợp kế tiếp, `nextPermu(a,n)` sinh hoán vị kế tiếp. **File:** `ThuatToan.java`
```java
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
```

---

## Checklist trước khi nộp
- [ ] Đổi `B22DCCN589` → mã SV của bạn, đổi `qCode` theo đề thi.
- [ ] Đổi IP nếu đề cho IP khác `203.162.10.109`.
- [ ] TCP Character/UDP nhớ `.trim()` khi `split` nếu server gửi kèm `\0` (do `new String(dp.getData())` lấy cả buffer thừa).
- [ ] UDP Object nhớ `dp.getLength()` khi cắt mảng, không dùng `dp.getData().length`.
- [ ] WS: tạo đúng Web Service Client, không copy package `vn.medianews` thủ công.
