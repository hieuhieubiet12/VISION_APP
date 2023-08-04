package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model;

public class Withdraw {
    private int id_withdraw, withdraw_amount,status_withdraw;
    private String user_name,date_withdraw,describe_withdraw;

    public Withdraw() {
    }

    public Withdraw(int id_withdraw, int withdraw_amount, int status_withdraw, String user_name, String date_withdraw) {
        this.id_withdraw = id_withdraw;
        this.withdraw_amount = withdraw_amount;
        this.status_withdraw = status_withdraw;
        this.user_name = user_name;
        this.date_withdraw = date_withdraw;
    }

    public String getDescribe_withdraw() {
        return describe_withdraw;
    }

    public void setDescribe_withdraw(String describe_withdraw) {
        this.describe_withdraw = describe_withdraw;
    }

    public int getId_withdraw() {
        return id_withdraw;
    }

    public void setId_withdraw(int id_withdraw) {
        this.id_withdraw = id_withdraw;
    }

    public int getWithdraw_amount() {
        return withdraw_amount;
    }

    public void setWithdraw_amount(int withdraw_amount) {
        this.withdraw_amount = withdraw_amount;
    }

    public int getStatus_withdraw() {
        return status_withdraw;
    }

    public void setStatus_withdraw(int status_withdraw) {
        this.status_withdraw = status_withdraw;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDate_withdraw() {
        return date_withdraw;
    }

    public void setDate_withdraw(String date_withdraw) {
        this.date_withdraw = date_withdraw;
    }
}
