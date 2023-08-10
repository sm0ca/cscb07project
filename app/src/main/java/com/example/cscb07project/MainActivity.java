package com.example.cscb07project;

import android.os.Bundle;
import android.view.View;

import com.example.cscb07project.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    public static String currentUser;
    public static boolean isOwner;
    public static String ownerStore;
    private static final Bundle bundleStoreToItem = new Bundle();
    private static final String BUNDLE_STORE_KEY = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.cscb07project.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        FirebaseAuth dbAuth = FirebaseAuth.getInstance();
        // for shopper
//        currentUser = "user2"; //dbAuth.getCurrentUser().getUid();
//        isOwner = false;
//        ownerStore = "";

//        // for owner
        currentUser = "person 1";
        isOwner = true;
        ownerStore = "st 1";

        NavigationBarView bottomNav;
        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHost != null;
        NavController navController = navHost.getNavController();
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);
        AppBarConfiguration appBarConfiguration;
        if (isOwner) {
            bottomNav = findViewById(R.id.nav_view_owner);
            findViewById(R.id.nav_view).setVisibility(View.INVISIBLE);
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_owner_shop, R.id.navigation_owner_orders, R.id.navigation_options).build();
            navGraph.setStartDestination(R.id.navigation_owner_shop);

        } else {
            bottomNav = findViewById(R.id.nav_view);
            findViewById(R.id.nav_view_owner).setVisibility(View.INVISIBLE);
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_shop, R.id.navigation_cart, R.id.navigation_orders, R.id.navigation_options).build();
        }
        navController.setGraph(navGraph);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
        Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).navigateUp();
        return true;
    }

    public static Bundle getStoreBundle() {
        return bundleStoreToItem;
    }

    public static String getStoreBundleKey() {
        return BUNDLE_STORE_KEY;
    }

}
