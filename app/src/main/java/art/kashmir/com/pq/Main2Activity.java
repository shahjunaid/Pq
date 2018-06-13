package art.kashmir.com.pq;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    android.support.v4.app.FragmentManager fragment;
    FragmentTransaction trans;
    int no;
    int fragmentno;
    Toolbar toolbar;
    FragmentManager fr=getFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
      toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(5);
        }
        fragment = getSupportFragmentManager();
        if(savedInstanceState != null)
        {
            int a=savedInstanceState.getInt("fragno");
            if(a == 1)
            {
                fr.beginTransaction().replace(R.id.frame,new quotes()).commit();
                no=1;
                toolbar.setSubtitle("quotes");
            }
            else{
                fr.beginTransaction().replace(R.id.frame,new BlankFragment()).commit();
                no=2;
                toolbar.setSubtitle("Images");
            }
        }
        //setting value initially to one
        if(savedInstanceState == null)
        {
            no=1;
            fr.beginTransaction().replace(R.id.frame,new quotes()).commit();
            toolbar.setSubtitle("quotes");
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu=navigationView.getMenu();
        menu.findItem(R.id.nav_quotes).setChecked(true);
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
        getMenuInflater().inflate(R.menu.main2, menu);
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
            if(no==1){
                fr.beginTransaction().replace(R.id.frame,new quotes()).commit();
            }else{
                Toast.makeText(this, "Refreshing", Toast.LENGTH_SHORT).show();
                fr.beginTransaction().replace(R.id.frame,new BlankFragment()).commit();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_quotes) {
            // Handle the camera action
            toolbar.setSubtitle("quotes");
            fr.beginTransaction().replace(R.id.frame,new quotes()).commit();
            no=1;
            fragmentno=1;
        } else if (id == R.id.nav_images) {
            toolbar.setSubtitle("images");
            fr.beginTransaction().replace(R.id.frame,new BlankFragment()).commit();
            //changing the value to 2
            no=2;
            fragmentno=2;
        }  else if (id == R.id.nav_share) {
            String quo="checkout this cool app that shows amazing quotes and pictures that you can download." +
                    "download here https://";
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,quo);
            intent.setType("text/plain");
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("fragno",fragmentno);
        super.onSaveInstanceState(outState);
    }
}
