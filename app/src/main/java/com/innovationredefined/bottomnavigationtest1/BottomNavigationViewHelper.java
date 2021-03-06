package com.innovationredefined.bottomnavigationtest1;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

@SuppressLint("RestrictedAPI")
public class BottomNavigationViewHelper {
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                item.setPadding(0, 15, 0, 0);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    public static void  removeBottomNavigationLabel(BottomNavigationView view){
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                Field mLargeLabel = item.getClass().getDeclaredField("mLargeLabel");
                Field mSmallLabel = item.getClass().getDeclaredField("mSmallLabel");
                mLargeLabel.setAccessible(true);
                mSmallLabel.setAccessible(true);
                TextView mLargeLabelTextView=null, mSmallLabelTextView=null;

                if(mLargeLabel.getType()==TextView.class)
                   mLargeLabelTextView = (TextView) mLargeLabel.get(item);
                if(mLargeLabelTextView!=null)
                    mLargeLabelTextView.setVisibility(View.GONE);

                if(mSmallLabel.getType()==TextView.class)
                    mSmallLabelTextView = (TextView) mSmallLabel.get(item);
                if(mSmallLabelTextView!=null)
                    mSmallLabelTextView.setVisibility(View.GONE);

                mLargeLabel.setAccessible(false);
                mSmallLabel.setAccessible(false);

            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get tv field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of tv", e);
        }
    }
}