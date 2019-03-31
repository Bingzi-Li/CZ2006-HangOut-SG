package com.example.hangout_v0.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//LUO
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import com.youth.banner.Banner;
import java.util.ArrayList;

import com.example.hangout_v0.R;

public class HomeFragment extends Fragment {

    private SearchView mSearchView = null;
    private ListView mListView = null;
    private String[] mDatas = {};

    private ArrayList<Integer> images;

    ViewPager viewPager;
    private ViewPager mViewPager;
    private com.example.hangout_v0.Home.CardPagerAdapter mCardAdapter;
    private com.example.hangout_v0.Home.ShadowTransformer mCardShadowTransformer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.homepage_layout, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);
        ///////////////////////////////////////////////////////////////////////////////////

        initImageData();
        Banner banner = (Banner) view.findViewById(R.id.home_banner);
        banner.setImageLoader(new com.example.hangout_v0.Home.GlideImageLoader());
        banner.setImages(images);
        banner.start();

        ///////////////////////////////////////////////////////////////////////////////////
//
//        mSearchView = (SearchView) view.findViewById(R.id.home_searchView);
//        mListView = (ListView) view.findViewById(R.id.home_listView);
//        mListView.setAdapter(new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, mDatas));
//        mListView.setTextFilterEnabled(true);
//
//        // listen to the search view
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            // if the search view text changes
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (!TextUtils.isEmpty(newText)){
//                    mListView.setFilterText(newText);
//                }else{
//                    mListView.clearTextFilter();
//                }
//                return false;
//            }
//        });

        ///////////////////////////////////////////////////////////////////////////////////

        mViewPager = (ViewPager) view.findViewById(R.id.home_cardViewPager);

        mCardAdapter = new com.example.hangout_v0.Home.CardPagerAdapter();
        mCardAdapter.addCardItem(new com.example.hangout_v0.Home.CardItem(R.string.title_1, R.string.text_1));
        mCardAdapter.addCardItem(new com.example.hangout_v0.Home.CardItem(R.string.title_2, R.string.text_2));
        mCardAdapter.addCardItem(new com.example.hangout_v0.Home.CardItem(R.string.title_3, R.string.text_3));
        mCardAdapter.addCardItem(new com.example.hangout_v0.Home.CardItem(R.string.title_4, R.string.text_4));

        mCardShadowTransformer = new com.example.hangout_v0.Home.ShadowTransformer(mViewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        ///////////////////////////////////////////////////////////////////////////////////

        return view;
    }

    private void initImageData() {
        images = new ArrayList<>();
        images.add(R.drawable.home_banner_shop_esora);
        images.add(R.drawable.home_banner_shop_jaan);
        images.add(R.drawable.home_banner_shop_amo);
        images.add(R.drawable.home_banner_shop_folklore);
        images.add(R.drawable.home_banner_shop_humpback);
    }
}
