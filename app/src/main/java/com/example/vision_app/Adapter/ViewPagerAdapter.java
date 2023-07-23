package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Fragment.Admin.ManageOrder;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Fragment.Admin.ManageProduct;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Fragment.Admin.ManageWithdraw_Recharge;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Fragment.Admin.MyAdmin;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Fragment.User.HomeFragment;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Fragment.User.NewProduct;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Fragment.User.Person;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Fragment.User.Share;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int role=0;
    int quatity = 4;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                if (role == 0){
                    return new ManageProduct();
                }
                if (role == 1){
                    return new HomeFragment();
                }
            case 1:
                if (role==0){
                    return new ManageOrder();
                }
                if (role==1) {
                    return new NewProduct();
                }
            case 2:
                if(role==0){
                    return new ManageWithdraw_Recharge();
                }
                if(role == 1){
                    return new Share();
                }
            case 3:
                if(role == 0){
                    return new MyAdmin();
                }
                if(role == 1){
                    return new Person();
                }
            default:
                if(role==0)
                    return new ManageProduct();
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return quatity;
    }
    public int getRole(int role){
        return  this.role = role;
    }
}
