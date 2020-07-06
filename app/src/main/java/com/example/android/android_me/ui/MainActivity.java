package com.example.android.android_me.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {
    private int headIndex;
    private int chestIndex;
    private int legsIndex;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.android_me_linear_layout) != null) {
            mTwoPane = true;

            Button btnNext = findViewById(R.id.next_button);
            GridView gridView = findViewById(R.id.images_grid_view);

            btnNext.setVisibility(View.GONE);
            gridView.setNumColumns(2);

            if(savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                BodyPartFragment headFragment = new BodyPartFragment();
                BodyPartFragment chestFragment = new BodyPartFragment();
                BodyPartFragment legsFragment = new BodyPartFragment();

                headFragment.setmImageIds(AndroidImageAssets.getHeads());
                chestFragment.setmImageIds(AndroidImageAssets.getBodies());
                legsFragment.setmImageIds(AndroidImageAssets.getLegs());

                fragmentManager.beginTransaction().add(R.id.head_container, headFragment).commit();
                fragmentManager.beginTransaction().add(R.id.chest_container, chestFragment).commit();
                fragmentManager.beginTransaction().add(R.id.leg_container, legsFragment).commit();
            }

        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onImageSelected(int position) {
        int bodyPartNumber = position/12;
        int listIndex = position - 12*bodyPartNumber;
        Bundle bundle = new Bundle();
        final Intent intent = new Intent(this, AndroidMeActivity.class);
        Button nextBtn = findViewById(R.id.next_button);

        if(mTwoPane) {
            BodyPartFragment bodyPartFragment = new BodyPartFragment();

            bodyPartFragment.setmListIndex(listIndex);

            switch (bodyPartNumber) {
                case 0:
                    bodyPartFragment.setmImageIds(AndroidImageAssets.getHeads());
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, bodyPartFragment)
                            .commit();
                    break;

                case 1:
                    bodyPartFragment.setmImageIds(AndroidImageAssets.getBodies());
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.chest_container, bodyPartFragment)
                            .commit();
                    break;

                case 2:
                    bodyPartFragment.setmImageIds(AndroidImageAssets.getLegs());
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, bodyPartFragment)
                            .commit();
                    break;
            }
        } else {
            switch (bodyPartNumber) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    chestIndex = listIndex;
                    break;
                case 2:
                    legsIndex = listIndex;
                    break;
                default:
                    break;
            }

            bundle.putInt(IndexBodyPartPositionContract.HEAD_INDEX, headIndex);
            bundle.putInt(IndexBodyPartPositionContract.CHEST_INDEX, chestIndex);
            bundle.putInt(IndexBodyPartPositionContract.LEGS_INDEX, legsIndex);
            intent.putExtras(bundle);

            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
        }
    }

}
