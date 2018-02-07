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


public class BullyFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<SkillItem> skillItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bully, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.bully_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        skillItems = new ArrayList<>();

        List<Integer> BullyPic = new ArrayList<>();

        BullyPic.add(R.drawable.police_1);
        BullyPic.add(R.drawable.police_2);
        BullyPic.add(R.drawable.police_3);


        SkillItem skillItem1 = new SkillItem(
                "نام 1" , BullyPic.get(0)

        );
        skillItems.add(skillItem1);

        SkillItem skillItem2 = new SkillItem(
                "نام 2" , BullyPic.get(1)

        );
        skillItems.add(skillItem2);

        SkillItem skillItem3 = new SkillItem(
                "نام 3" , BullyPic.get(2)

        );
        skillItems.add(skillItem3);


        adapter = new SkillAdapter(skillItems,getActivity());

        recyclerView.setAdapter(adapter);

        return view;
    }


}
