package com.example.cscb07project;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.cscb07project.databinding.ActivityMainBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cscb07project.ui.cart.CartFragment;
import com.example.cscb07project.ui.options.OptionsFragment;
import com.example.cscb07project.ui.orders.OrderList.AllList.OrderListFragment;
import com.example.cscb07project.ui.orders.OrdersFragment;
import com.example.cscb07project.ui.orders.OwnerOrder.OwnerOrderFragment;
import com.example.cscb07project.ui.shop.OwnerList.OwnerListFragment;
import com.example.cscb07project.ui.shop.ShopFragment;
import com.example.cscb07project.ui.shop.storelist.StoreListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
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
        NavigationBarView bottomNav = findViewById(R.id.nav_view);
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
        bottomNav.setOnItemSelectedListener(this);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.navigation_shop && !isOwner) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new StoreListFragment()).commit();
            return true;
        }
        if(item.getItemId() == R.id.navigation_shop && isOwner) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new OwnerListFragment()).commit();
            return true;
        }
        if(item.getItemId() == R.id.navigation_cart) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new CartFragment()).commit();
            return true;
        }
        if(item.getItemId() == R.id.navigation_orders && !isOwner) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new OrderListFragment()).commit();
            return true;
        }
        if(item.getItemId() == R.id.navigation_orders &&!isOwner) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new OwnerOrderFragment()).commit();
            return true;
        }
        if(item.getItemId() == R.id.navigation_options) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_activity_main, new OptionsFragment()).commit();
            return true;
        }
        return false;
    }
}