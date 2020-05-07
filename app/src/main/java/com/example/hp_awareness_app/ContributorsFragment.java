package com.example.hp_awareness_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContributorsFragment extends Fragment {

    private String[] names = new String[]{"Arjun Anand","Vishal Pal","Janmejai Pandey","Paramjeet","Devang Sharma"};
    private String[] descriptions = new String[]{"","","","",""};
    private int[] images = new int[]{R.drawable.arjun,R.drawable.vishal,0,R.drawable.paramjeet,R.drawable.devang};

    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contributors,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().findViewById(R.id.fragment_container).setVisibility(View.GONE);
        getActivity().findViewById(R.id.bottomNavigation).setVisibility(View.GONE);
        listView = view.findViewById(R.id.listView);
        List<HashMap<String,String>> aList = new ArrayList<>();
        for (int x = 0; x < names.length; x++){
            HashMap<String, String> hm = new HashMap<>();
            hm.put("Name",names[x]);
            hm.put("Description", descriptions[x]);
            hm.put("Image",Integer.toString(images[x]));
            aList.add(hm);
        }
        String[] from = {"Image","Name","Description"};
        int[] to = {R.id.imageView,R.id.name,R.id.description};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),aList, R.layout.contributors_row,from,to);
        listView.setAdapter(simpleAdapter);
    }
}
