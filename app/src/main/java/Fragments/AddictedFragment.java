package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coins.black.main_menu.R;

import java.util.ArrayList;
import java.util.List;

import Classes.SkillAdapter;
import Classes.SkillItem;


public class AddictedFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<SkillItem> skillItems;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addicted, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.addicted_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        skillItems = new ArrayList<>();

        List<Integer> PolicePic = new ArrayList<>();

        PolicePic.add(R.drawable.addicted_1);
        PolicePic.add(R.drawable.addicted_2);
        PolicePic.add(R.drawable.addicted_3);

        for(int i =1;i<=3; i++ ){

            SkillItem skillItem = new SkillItem(
                    "heading" + (i), PolicePic.get(i-1)

            );

            skillItems.add(skillItem);
        }

        adapter = new SkillAdapter(skillItems,getActivity());

        recyclerView.setAdapter(adapter);


        return view;
    }
}


