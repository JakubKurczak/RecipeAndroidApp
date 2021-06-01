package com.example.recipies;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import models.BaseRecipe;
import models.User;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    MyFeed myFeed;
    MyProfile myProfile;
    BaseRecipeListView likedRecipies;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    User user;


    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String doc_path = "ignacy_jan";
        User.setDoc_id(doc_path);

        setTitle("My Feed");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        if (savedInstanceState == null) {
            myFeed = MyFeed.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment, myFeed)
                    .commit();



        }
        Context context = getApplicationContext();

        db.collection("users").document(doc_path).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(documentSnapshot.exists()){
                            user = new User(documentSnapshot.getId(),(String)documentSnapshot.get("name"),(String)documentSnapshot.get("surname"),(String)documentSnapshot.get("photo_url"),(String)documentSnapshot.get("mail"));
                            String s =User.getDoc_id();

                        }else{
                            Toast.makeText(context,"No such user",Toast.LENGTH_LONG).show();
                        }

                    }
                });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(new ComponentName(getApplicationContext(),SearchActivity.class)));
        searchView.setSubmitButtonEnabled(true);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_my_profile:
                Toast.makeText(this,"Nav my profile selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_my_feed:
                if(myFeed == null){
                    myFeed = MyFeed.newInstance();

                }
                setTitle("My Feed");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment, myFeed)
                        .commit();
                break;
            case R.id.nav_my_recipies:
                if(myProfile == null)
                    myProfile = MyProfile.newInstance(User.getDoc_id());
                setTitle("My Recipies");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment, myProfile)
                        .commit();
                break;
            case R.id.nav_saved_recipies:
                if(likedRecipies == null)
                    likedRecipies = BaseRecipeListView.newInstance();
                setTitle("Saved Recipies");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment, likedRecipies)
                        .commit();
                break;
            default:
                break;
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onSearchRequested() {
        Toast.makeText(this,"aaaaaa",Toast.LENGTH_SHORT).show();
        return super.onSearchRequested();
    }
}