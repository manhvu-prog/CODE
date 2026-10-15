package TCP;

import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 20170711L;
    public String code,name,dayOfBirth,userName;
    public int id;
    public Customer(int id,String code,String name,String dayOfBirth){
        this.id=id;this.code=code;this.name=name;this.dayOfBirth=dayOfBirth;
    }

    @Override
    public String toString() {
        return id+" "+code+" "+name+" "+dayOfBirth+" "+userName;
    }
}
