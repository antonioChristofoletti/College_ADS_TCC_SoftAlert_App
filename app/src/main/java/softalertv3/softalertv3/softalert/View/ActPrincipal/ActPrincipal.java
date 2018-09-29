package softalertv3.softalertv3.softalert.View.ActPrincipal;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import softalertv3.softalertv3.R;

public class ActPrincipal extends AppCompatActivity {

    Toolbar toolbar;
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
        toolbar = (Toolbar) findViewById(R.id.toolbarActPrincipal);
        setSupportActionBar(toolbar);

        configuraComponentes();
    }

    public void configuraComponentes() {


        tabLayout = (TabLayout) findViewById(R.id.tabLayoutActPrincipal);
        tabNoticias = (TabItem) findViewById(R.id.tabLayoutActPrincipalItemNoticias);
        tabDicas = (TabItem) findViewById(R.id.tabLayoutActPrincipalItemDicas);
        tabPerfil = (TabItem) findViewById(R.id.tabLayoutActPrincipalItemPerfil);

        viewPager = (ViewPager) findViewById(R.id.viewPagerActPrincipal);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}