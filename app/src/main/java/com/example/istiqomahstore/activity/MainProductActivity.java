package com.example.istiqomahstore.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.istiqomahstore.R;
import com.example.istiqomahstore.helpers.CustomCompatActivity;
import com.example.istiqomahstore.helpers.RandomString;

import java.util.Random;

public class MainProductActivity extends CustomCompatActivity {

    private Toolbar toolbarMainMenu;
    MenuItem menuItem;
    TextView badgeCounter;
    int pendingNotifications = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_product);

        setVariable();
        createView();
    }

    private void createView() {
        setSupportActionBar(toolbarMainMenu);
    }

    private void setVariable() {
        toolbarMainMenu = findViewById(R.id.toolbarMainMenu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.toolbar_menu, menu);

       menuItem = menu.findItem(R.id.action_cart);
       badgeCounter = findViewById(R.id.badge_counter);

       if (pendingNotifications == 0){
           menuItem.setActionView(null);
       } else {
           menuItem.setActionView(R.layout.notification_badge);
           View view =  menuItem.getActionView();
           badgeCounter = view.findViewById(R.id.badge_counter);
           badgeCounter.setText(String.valueOf(pendingNotifications));

           view.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Toast.makeText(getApplicationContext(),"Cart", Toast.LENGTH_SHORT).show();
               }
           });
       }

       menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem menuItem) {
               Toast.makeText(getApplicationContext(),"Cart", Toast.LENGTH_SHORT).show();
               return true;
           }
       });

       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart){
            Toast.makeText(getApplicationContext(),"Cart", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_edit_profile){
            Toast.makeText(getApplicationContext(),"Edit Profile", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_logout){
            outDialogMessage();
        }
        return true;
    }
}
