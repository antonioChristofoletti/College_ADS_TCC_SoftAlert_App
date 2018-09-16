package softalertv3.softalertv3.softalert.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numeroDeTabs;

    public PagerAdapter(FragmentManager fm, int numeroDeTabs) {
        super(fm);

        this.numeroDeTabs = numeroDeTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: {
                return null;
            }
            case 1: {
                return null;
            }
            case 2: {
                return null;
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public int getCount() {
        return numeroDeTabs;
    }
}
