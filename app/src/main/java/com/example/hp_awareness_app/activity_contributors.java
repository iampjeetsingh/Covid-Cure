package com.example.hp_awareness_app;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class activity_contributors extends AppCompatActivity {
    private String[] names = new String[]{"Naveen Katiyar","Arjun Anand","Vishal Pal","Janmejai Pandey","Paramjeet","Devang Sharma"};
    private String[] descriptions = new String[]{"","","","","","",""};
    private int[] images = new int[]{R.drawable.naveen,R.drawable.arjun,R.drawable.vishal,R.drawable.janmejai,R.drawable.paramjeet,R.drawable.devang};

    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contributors);
        listView = findViewById(R.id.listView);
        List<HashMap<String,String>> aList = new ArrayList<>();
        for (int x = 0; x < names.length; x++){
            HashMap<String, String> hm = new HashMap<>();
            hm.put("Name",names[x]);
            hm.put("",descriptions[x]);
            hm.put("Image",Integer.toString(images[x]));
            aList.add(hm);
        }
        String[] from = {"Image","Name"};
        int[] to = {R.id.image,R.id.name};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,aList, R.layout.contributors_row,from,to);
        listView.setAdapter(simpleAdapter);
    }
}

