package com.example.hungry_student_login.mainPage.restaurant.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hungry_student_login.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantmenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantmenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "menuFrag";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestaurantmenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantmenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantmenuFragment newInstance(String param1, String param2) {
        RestaurantmenuFragment fragment = new RestaurantmenuFragment();
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

        View v = inflater.inflate(R.layout.fragment_restaurantmenu, container, false);
        TextView restaurantMenu = v.findViewById(R.id.restaurant_menu);
        restaurantMenu.setBackgroundResource(R.drawable.menu);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String menu = bundle.getString("menu");
            Log.d(TAG, "onCreateView: " + menu);
            restaurantMenu.setText(menu);
        } else {
            Log.d(TAG, "onCreateView: ");
        }

        return v;
    }
}