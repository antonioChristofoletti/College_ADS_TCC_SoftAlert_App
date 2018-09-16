package softalertv3.softalertv3.softalert.View;

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import softalertv3.softalertv3.R;

public class ActPrincipal extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    TabItem tabNoticias;
    TabItem tabDicas;
    TabItem tabPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void configuraComponentes() {
       /* tabLayout = (TabLayout) findViewById(R.id.tabPrincipal);
        tabNoticias = (TabItem) findViewById(R.id.tabPrincipalItemNoticia);
        tabDicas = (TabItem) findViewById(R.id.tabPrincipalItemDicas);
        tabPerfil = (TabItem) findViewById(R.id.tabPrincipalItemPerfil);*/

        //viewPager = (TabLayout) findViewById(R.id.viewPager);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
    }
}