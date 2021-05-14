package com.example.pocketnews_277.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketnews_277.R;
import com.example.pocketnews_277.adapter.NewsListAdapter;
import com.example.pocketnews_277.model.NewsDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HomepageActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    final String TAG = "HomepageActivity";
    private SearchView simpleSearchView;
    private FirebaseAuth mAuth;
	FirebaseFirestore db;
    private NewsDataModel newsDataModel;
    private NewsListAdapter adapter;
    private NewsDataViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_homepage);

        RecyclerView recyclerView = findViewById(R.id.newsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        newsDataModel = new NewsDataModel();
        adapter = new NewsListAdapter(this, newsDataModel.getArticles());
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(NewsDataViewModel.class);
        viewModel.getNewsDataObserver().observe(this, new Observer<NewsDataModel>() {
            @Override
            public void onChanged(NewsDataModel newsDataModel) {
                if(newsDataModel != null){
                    HomepageActivity.this.newsDataModel = HomepageActivity.this.newsDataModel;
                    adapter.setNewsList(newsDataModel.getArticles());
                } else{
                    Toast.makeText(HomepageActivity.this, "Could not retrieve data!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.makeApiCall();

        simpleSearchView = (SearchView) findViewById(R.id.searchInput);
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "Search input: " + query);
                viewModel.makeApiCall(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (simpleSearchView.getQuery().length() == 0) {
                    Log.i(TAG, "Search got cleared");
                    viewModel.makeApiCall();
                }
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadHomePage();
    }

    public void showProfile(View v) {
        PopupMenu profileMenu = new PopupMenu(this,v);
        profileMenu.setOnMenuItemClickListener(this);
        profileMenu.inflate(R.menu.profile_menu);
        profileMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.signOutOption:
                mAuth.signOut();
                // redirect user to login activity
                Intent goToLoginIntent = new Intent(HomepageActivity.this, Login.class);
                startActivity(goToLoginIntent);
                Toast.makeText(this, "Signed out successfully!", Toast.LENGTH_SHORT).show();
            default: return false;
        }
    }

    private void loadHomePage(){

        FirebaseUser user = mAuth.getCurrentUser();

		db.collection(user.getUid())
				.get()
				.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
					@Override
					public void onComplete(@NonNull Task<QuerySnapshot> task) {
						if (task.isSuccessful()) {
							for (QueryDocumentSnapshot document : task.getResult()) {
								if (document.getId().equals("users")) {
									Log.i(TAG, document.getId() + " => " + document.getData());
									String username = "Hey " + document.getData().get("user_name") + "!";
									TextView greeting = findViewById(R.id.userGreeting);
									greeting.setText(username);
								}
							}
						} else {
							Log.w(TAG, "Error getting documents from Firestore.", task.getException());
						}
					}
				});

    }
}