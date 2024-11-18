package com.narvasoft.my_appi;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.narvasoft.my_appi.fragments.create;
import com.narvasoft.my_appi.fragments.deleteId;
import com.narvasoft.my_appi.fragments.searchId;


public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new searchId();
            case 1:
                return new create();
            case 2:
                return new deleteId();
            default:
                return new searchId();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
