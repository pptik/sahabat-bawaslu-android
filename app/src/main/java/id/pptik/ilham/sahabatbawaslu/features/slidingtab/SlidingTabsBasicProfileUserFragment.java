/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package id.pptik.ilham.sahabatbawaslu.features.slidingtab;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import id.pptik.ilham.sahabatbawaslu.R;
import id.pptik.ilham.sahabatbawaslu.features.forum.ForumFragment;
import id.pptik.ilham.sahabatbawaslu.features.learning.LearningFragment;
import id.pptik.ilham.sahabatbawaslu.features.news.NewsFragment;
import id.pptik.ilham.sahabatbawaslu.features.profile.ProfileActivitiesLogFragment;
import id.pptik.ilham.sahabatbawaslu.features.profile.ProfileUserFragment;


public class SlidingTabsBasicProfileUserFragment extends FragmentPagerAdapter {

    private Context context;
    public int[] titleIcon = {R.drawable.baseline_face_white_18dp,R.drawable.baseline_history_white_18dp};
    private int tinggiIcon;

    public SlidingTabsBasicProfileUserFragment(FragmentManager fm, Context c) {
        super(fm);

        context = c;
        double scale = c.getResources().getDisplayMetrics().density;
        tinggiIcon = (int)(24 * scale + 0.5f);

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if(position == 0){
            fragment = new ProfileUserFragment();
        }else if (position==1){
            fragment=new ProfileActivitiesLogFragment();
        }

        //Bundle b = new Bundle();
        //b.putInt("posisi",position);

        //fragment.setArguments(b);

        return fragment;
    }

    //Count the number of displayed tab
    //Based on the length of icons's array
    @Override
    public int getCount() {
        return titleIcon.length;
    }

    //Untuk menampilkan title dengan icon
    @Override
    public CharSequence getPageTitle(int position) {
        Drawable d = context.getResources().getDrawable(titleIcon[position]);
        d.setBounds(0 ,0 , tinggiIcon, tinggiIcon);

        ImageSpan is = new ImageSpan(d);

        SpannableString sp = new SpannableString(" ");
        sp.setSpan(is,0,sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return (sp);
    }


}
