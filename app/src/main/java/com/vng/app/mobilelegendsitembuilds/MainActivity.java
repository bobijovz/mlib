package com.vng.app.mobilelegendsitembuilds;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.vng.app.mobilelegendsitembuilds.adapter.ImageAdapter;
import com.vng.app.mobilelegendsitembuilds.fragment.HeroListFragment;
import com.vng.app.mobilelegendsitembuilds.fragment.ItemBuilderFragment;
import com.vng.app.mobilelegendsitembuilds.fragment.ShareBuildFragment;
import com.vng.app.mobilelegendsitembuilds.fragment.WidgetFragment;
import com.vng.app.mobilelegendsitembuilds.model.Hero;
import com.vng.app.mobilelegendsitembuilds.model.Item;

import java.util.ArrayList;
import com.vng.app.mobilelegendsitembuilds.service.FloatingViewService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FirebaseAuth.AuthStateListener,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<Status>,
        ImageAdapter.ImageAdapterListener{

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private String PROVIDER_ID = "firebase";
    private TextView textName, textEmail;
    private ArrayList<Hero> heros = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private HeroListFragment heroFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        heros = getIntent().getParcelableArrayListExtra("HERO_LIST");
        items = getIntent().getParcelableArrayListExtra("ITEMS");

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Item Widget Initialized", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                stopService(new Intent(MainActivity.this, FloatingViewService.class));
                            }
                        }).show();
                startService(new Intent(MainActivity.this, FloatingViewService.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        textName = (TextView) headerView.findViewById(R.id.text_name);
        textEmail = (TextView) headerView.findViewById(R.id.text_email);
        navigationView.setNavigationItemSelectedListener(this);
        textName.setText(mAuth.getCurrentUser().getDisplayName());
        textEmail.setText(mAuth.getCurrentUser().getEmail());
        heroFrag = new HeroListFragment().newInstance(heros,items);
        switchFragment(heroFrag);
        heroFrag.setAdapterListener(this);

    }

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

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
        if (id == R.id.action_exit) {
            System.exit(0);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_item_builder) {
            switchFragment(heroFrag);
        } else if (id == R.id.nav_widget) {
            switchFragment(new WidgetFragment());
        } else if (id == R.id.nav_build_sharing) {
            switchFragment(new ShareBuildFragment());
        } else if (id == R.id.nav_share) {
            ShareDialog shareDialog = new ShareDialog(this);
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Mobile Legends : Bang bang Item Builds")
                    .setContentDescription(
                            "Check Mobile Legends Item Builds on Google Play!")
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .build();
            shareDialog.show(linkContent);

        } else if (id == R.id.nav_logout) {
            mAuth.signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            for (UserInfo data : user.getProviderData()) {
                PROVIDER_ID = data.getProviderId() != null ? data.getProviderId() : PROVIDER_ID;
            }
        } else {
            signMeOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void signMeOut() {
        if (PROVIDER_ID.contentEquals("firebase")) {
            this.finish();
        } else if (PROVIDER_ID.contentEquals("facebook.com")) {
            LoginManager.getInstance().logOut();
            this.finish();
        } else if (PROVIDER_ID.contentEquals("google.com")) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(this);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull Status status) {
        this.finish();
    }

    private void switchFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();
    }

    @Override
    public void onHeroPick(Bundle hero, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition changeTransform = TransitionInflater.from(this).
                    inflateTransition(R.transition.change_image_transform);
            Transition explodeTransform = TransitionInflater.from(this).
                    inflateTransition(android.R.transition.explode);

            // Setup exit transition on first fragment
            heroFrag.setSharedElementReturnTransition(changeTransform);
            heroFrag.setExitTransition(explodeTransform);

            ItemBuilderFragment builderFragment = new ItemBuilderFragment().newInstance(hero);
           /* ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, view, "profile");
            builderFragment.setArguments(options.toBundle());*/
            // Setup enter transition on second fragment
            builderFragment.setSharedElementEnterTransition(changeTransform);
            builderFragment.setEnterTransition(explodeTransform);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, builderFragment)
                    .addToBackStack("item_builder")
                    .addSharedElement(view, "hero_image");
            // Apply the transaction
            ft.commit();
        } else {
            //switchFragment();
        }
    }
}
