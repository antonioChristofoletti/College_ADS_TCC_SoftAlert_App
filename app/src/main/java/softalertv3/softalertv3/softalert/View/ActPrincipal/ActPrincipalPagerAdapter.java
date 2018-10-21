package softalertv3.softalertv3.softalert.View.ActPrincipal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import softalertv3.softalertv3.softalert.View.ActPrincipal.FragNoticias.FragActPrincipalNoticias;

public class ActPrincipalPagerAdapter extends FragmentPagerAdapter {

    private int numeroDeTabs;

    public ActPrincipalPagerAdapter(FragmentManager fm, int numeroDeTabs) {
        super(fm);

        this.numeroDeTabs = numeroDeTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: {
                return new FragActPrincipalNoticias();
            }
            case 1: {
                return new FragActPrincipalAlertar();
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
