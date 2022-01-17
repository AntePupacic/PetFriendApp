package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PetListActivity extends AppCompatActivity {

    private RecyclerView petRecycleView;
    private PetAdapter.RecycleViewClickListener listener;
    private FloatingActionButton addBtn;
    private PetAdapter petAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);


        DataStorage.fillData(getApplicationContext());
        findViews();
        addPet();


        recycleViewClickListener();
        petRecycleView.addItemDecoration(new DividerItemDecoration(petRecycleView.getContext(), DividerItemDecoration.VERTICAL));
        petRecycleView.setItemAnimator(new DefaultItemAnimator());
        petAdapter = new PetAdapter(getApplicationContext(), listener);
        petRecycleView.setAdapter(petAdapter);

        searchRecycleView();

    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.petlist_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mybutton) {
            Intent intent = new Intent(PetListActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



    private void searchRecycleView(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                petAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                petAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void findViews(){
        addBtn = (FloatingActionButton) findViewById(R.id.addBtn);
        searchView = (SearchView) findViewById(R.id.conatinerSearchView);
        petRecycleView = (RecyclerView) findViewById(R.id.dogRecycleView);
    }

    private void recycleViewClickListener(){
        listener = new PetAdapter.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(PetListActivity.this, PetInfoActivity.class);
                Integer id = DataStorage.pets.get(position).getID();
                intent.putExtra("id", id);
                startActivity(intent);
            }
        };
    }

    //New activity to add pet data into DB
    private void addPet(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetListActivity.this, AddPetActivity.class);
                startActivity(intent);
            }
        });
    }

}