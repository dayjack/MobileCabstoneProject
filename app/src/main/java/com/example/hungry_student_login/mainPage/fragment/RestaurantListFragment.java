package com.example.hungry_student_login.mainPage.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.RestaurantListData;
import com.example.hungry_student_login.mainPage.restaurant.RestaurantInfoPage;
import com.example.hungry_student_login.mainPage.restaurant.RestaurantListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantListFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestaurantListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantListFragment newInstance(String param1, String param2) {
        RestaurantListFragment fragment = new RestaurantListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // Inflate the layout for this fragment
        /**
         * 식당 리스트 페이지 카테고리 관련
         * **/
        View v = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        FrameLayout categoryFrame;
        categoryFrame = v.findViewById(R.id.category_frame);
        LayoutInflater categoryInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        categoryInflater.inflate(R.layout.category_gridlayout, categoryFrame, true);


        ListView listView = v.findViewById(R.id.restaurant_list);
        RestaurantListAdapter adapter = new RestaurantListAdapter();
        adapter.addItem(new RestaurantListData("김김김",1,3.1));
        adapter.addItem(new RestaurantListData("최최최",2,3.2));
        adapter.addItem(new RestaurantListData("이이이",3,3.3));
        adapter.addItem(new RestaurantListData("박박박",4,3.4));
        adapter.addItem(new RestaurantListData("정정정",5,3.5));
        adapter.addItem(new RestaurantListData("김김김",1,3.1));
        adapter.addItem(new RestaurantListData("최최최",2,3.2));
        adapter.addItem(new RestaurantListData("이이이",3,3.3));
        adapter.addItem(new RestaurantListData("박박박",4,3.4));
        adapter.addItem(new RestaurantListData("정정정",5,3.5));
        adapter.addItem(new RestaurantListData("김김김",1,3.1));
        adapter.addItem(new RestaurantListData("최최최",2,3.2));
        adapter.addItem(new RestaurantListData("이이이",3,3.3));
        adapter.addItem(new RestaurantListData("박박박",4,3.4));
        adapter.addItem(new RestaurantListData("정정정",5,3.5));
        adapter.addItem(new RestaurantListData("김김김",1,3.1));
        adapter.addItem(new RestaurantListData("최최최",2,3.2));
        adapter.addItem(new RestaurantListData("이이이",3,3.3));
        adapter.addItem(new RestaurantListData("박박박",4,3.4));
        adapter.addItem(new RestaurantListData("정정정",5,3.5));
        adapter.addItem(new RestaurantListData("김김김",1,3.1));
        adapter.addItem(new RestaurantListData("최최최",2,3.2));
        adapter.addItem(new RestaurantListData("이이이",3,3.3));
        adapter.addItem(new RestaurantListData("박박박",4,3.4));
        adapter.addItem(new RestaurantListData("정정정",5,3.5));
        adapter.addItem(new RestaurantListData("김김김",1,3.1));
        adapter.addItem(new RestaurantListData("최최최",2,3.2));
        adapter.addItem(new RestaurantListData("이이이",3,3.3));
        adapter.addItem(new RestaurantListData("박박박",4,3.4));
        adapter.addItem(new RestaurantListData("정정정",5,3.5));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(), RestaurantInfoPage.class);
                startActivity(intent);
            }
        });

        return v;
    }
}