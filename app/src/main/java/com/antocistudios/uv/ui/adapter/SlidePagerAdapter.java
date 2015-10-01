package com.antocistudios.uv.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.antocistudios.business.Const;
import com.antocistudios.moviecatalog.R;
import com.antocistudios.uv.ui.fragment.GameClassifierFragment;

/**
 * Created by Adrian Antoci.
 *
 */
public class SlidePagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public SlidePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return GameClassifierFragment.newInstance(Const.PLATFORM_PC);
            case 1:
                return GameClassifierFragment.newInstance(Const.PLATFORM_PS);
            case 2:
                return GameClassifierFragment.newInstance(Const.PLATFORM_XBOX_ONE);
            default:
                throw new RuntimeException("unhandled position");
        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getStringArray(R.array.platforms)[position];
    }

}
