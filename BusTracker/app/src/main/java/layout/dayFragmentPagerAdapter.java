package layout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by yuetinggg on 2/12/16.
 */
public class dayFragmentPagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = { "M", "T", "W", "Th", "F", "S", "S"};

    public dayFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int index) {
        return dayFragment.newInstance(index);
    }

    @Override
    public int getCount() {
        //get number of tabs, one for every day (5)
        return TITLES.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

}
