package com.example.hungry_student_login.mainPage.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.Post;
import com.example.hungry_student_login.data.RestaurantListData;
import com.example.hungry_student_login.mainPage.Board.PostAdapter;
import com.example.hungry_student_login.mainPage.restaurant.RestaurantInfoPage;
import com.example.hungry_student_login.mainPage.restaurant.RestaurantListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment {

    RecyclerView postRecyclerView;
    PostAdapter postAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
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

        View v = inflater.inflate(R.layout.fragment_board, container, false);
        postRecyclerView = v.findViewById(R.id.posted_writing);
        postAdapter = new PostAdapter(sample());
        postRecyclerView.setAdapter(postAdapter);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;

    }
    private List<Post> sample() {
        List<Post> postList = new ArrayList<>();
        postList.add(new Post("같이 밥 먹을사람", "나 우주공강인데 같이 밥 먹을 사람 구함~~~", "5분전", "1호",  2));
        postList.add(new Post("혼밥하기 좋은 식당 찾아줘", "나 친구 없어서..", "6분전", "1호",  0));
        postList.add(new Post("학교 주변에 파스타집 있어?", "학교 끝나고 남친이랑 파스타집 가려는 학교 근처에 ", "8분전", "1호",  1));
        postList.add(new Post("메가 커피 명지전문대점 이벤트!", "명지전문대 학생증 제시하면 10프로 할인(~11/9)", "15분전", "1호",  0));
        return postList;
    }
}