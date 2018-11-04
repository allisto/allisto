package lmniit.hackx.aesher.lnmniit_hacx.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import lmniit.hackx.aesher.lnmniit_hacx.fragments.Chatbot;
import lmniit.hackx.aesher.lnmniit_hacx.fragments.News;
import lmniit.hackx.aesher.lnmniit_hacx.fragments.Profile;

public class MainPagerAdapter extends FragmentPagerAdapter {
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0: return new News();
            case 1: return new Profile();
            case 2: return new Chatbot();
            default: return new News();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0: return "Pocket News";
            case 1: return  "Profile";
            case 2: return "Pocket Doctor";
            default: return  "Pocket News";
        }
    }
}
