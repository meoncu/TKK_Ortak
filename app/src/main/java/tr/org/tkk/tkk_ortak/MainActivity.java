package tr.org.tkk.tkk_ortak;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager manager = getFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        /*fragmnet click sayfa açma özelliği*/


        FragmentTransaction ft = manager.beginTransaction();
        Fragment_Anasayfa fragment_anasayfa = new Fragment_Anasayfa();
        ft.replace(R.id.content_frame,fragment_anasayfa);
        ft.addToBackStack(null);
        ft.commit();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        TextView txt_toolbar2 = (TextView) findViewById(R.id.txt_toolbar2);
        int id = item.getItemId();

        if (id == R.id.nav_anasayfa) {

            FragmentTransaction foa = manager.beginTransaction();
            Fragment_Anasayfa fragment_anasayfa = new Fragment_Anasayfa();
            foa.replace(R.id.content_frame,fragment_anasayfa);
            foa.commit();

        } else if (id == R.id.nav_personel) {

            FragmentTransaction fop = manager.beginTransaction();
            Fragment_Personel fragment_personel = new Fragment_Personel();
            fop.replace(R.id.content_frame,fragment_personel);
            fop.commit();

            txt_toolbar2.setText("Personel Bilgileri");

        } else if (id == R.id.nav_kurum) {

            FragmentTransaction fok = manager.beginTransaction();
            Fragment_Kurum fragment_kurum = new Fragment_Kurum();
            fok.replace(R.id.content_frame,fragment_kurum);
            fok.commit();
            txt_toolbar2.setText("Kurum Bilgileri");
        } else if (id == R.id.nav_sermaye) {

             FragmentTransaction fos= manager.beginTransaction();
            Fragment_Sermaye fragment_sermaye = new Fragment_Sermaye();
            fos.replace(R.id.content_frame,fragment_sermaye);
            fos.commit();
            txt_toolbar2.setText("Sermaye Bilgileri");
        } else if (id == R.id.nav_alisveris) {

            FragmentTransaction foal= manager.beginTransaction();
             Fragment_Alisveris fragment_alisveris = new Fragment_Alisveris();
            foal.replace(R.id.content_frame,fragment_alisveris);
            foal.commit();
            txt_toolbar2.setText("Alışveriş Bilgileri");
        } else if (id == R.id.nav_kefil) {

            FragmentTransaction fok= manager.beginTransaction();
            Fragment_Kefil fragment_kefil = new Fragment_Kefil();
           fok.replace(R.id.content_frame,fragment_kefil);
           fok.commit();
            txt_toolbar2.setText("Kefil Bilgileri");
        } else if (id == R.id.nav_senet) {

            FragmentTransaction fos= manager.beginTransaction();
           Fragment_Senet fragment_senet = new Fragment_Senet();
            fos.replace(R.id.content_frame,fragment_senet);
            fos.commit();
            txt_toolbar2.setText("Senet Bilgileri");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
