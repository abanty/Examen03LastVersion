package examenreyna03.android.examen03lastversion.ActivitiesViews;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import examenreyna03.android.examen03lastversion.R;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton add;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        add = (FloatingActionButton) findViewById(R.id.btnflotante);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),InsertarDatosActivity.class);
                startActivity(i);
            }
        });


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tabphone);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tabgroup);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_tabfav);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TabTelefono tab1 = new TabTelefono();
                    return tab1;
                case 1:
                    TabContactos tab2 = new TabContactos();
                    return tab2;
                case 2:
                    TabFavoritos tab3 = new TabFavoritos();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "TELEFONO";
                case 1:
                    return "CONTACTOS";
                case 2:
                    return "FAVORITOS";
            }
            return null;
        }
    }
}
