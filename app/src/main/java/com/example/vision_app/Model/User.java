package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model;

public class User {
    private String user_name,name,password,n_phone,date,address;
    private byte[] avatar;
    private int point,role;
    private  double money;

    public User() {
    }
    //getAll()
    public User(String user_name, String name, String password, String n_phone, String date, String address, byte[] avatar, double money, int point) {
        this.user_name = user_name;
        this.name = name;
        this.password = password;
        this.n_phone = n_phone;
        this.date = date;
        this.address = address;
        this.avatar = avatar;
        this.money = money;
        this.point = point;
    }
    //register
    public User(String user_name, String name, String password, String n_phone, String date, String address) {
        this.user_name = user_name;
        this.name = name;
        this.password = password;
        this.n_phone = n_phone;
        this.date = date;
        this.address = address;

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getN_phone() {
        return n_phone;
    }

    public void setN_phone(String n_phone) {
        this.n_phone = n_phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
