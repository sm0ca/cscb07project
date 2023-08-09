package com.example.mallapp;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.cscb07project.R;
import com.example.cscb07project.databinding.ActivityMainBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public static String currentUser;
    public static boolean isOwner;
//    public static BottomNavigationView bottomNav;
    private static final Bundle bundleStoreToItem = new Bundle();
    private static final String BUNDLE_STORE_KEY = "key";

    public static String ownerStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.cscb07project.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuth dbAuth = FirebaseAuth.getInstance();
        // for shopper
        currentUser = "user2"; //dbAuth.getCurrentUser().getUid();
        isOwner = false;
        ownerStore = "";

        // for owner
//        currentUser = "person 1";
//        isOwner = true;
//        ownerStore = "st 1";

//        bottomNav = findViewById(R.id.nav_view);
//        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                item.se
//                return true;
//            }
//        });
        if (isOwner) {
            BottomNavigationView bottomNav = findViewById(R.id.nav_view);
            bottomNav.getMenu().findItem(R.id.navigation_cart).setVisible(false);
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_shop, R.id.navigation_cart, R.id.navigation_orders,
                R.id.navigation_options, R.id.shop_storelist, R.id.owner_orders, R.id.orders_all_list, R.id.owner_list)
                .build();

        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHost != null;
        NavController navController = navHost.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

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