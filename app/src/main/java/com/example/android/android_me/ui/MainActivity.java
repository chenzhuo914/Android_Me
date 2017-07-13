package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;


public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {
    // Variables to store values for the list index of the selected iamges
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    // Single-pane refers to phone screen, two-pane display refers to tablet.
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Detect if it's two-pane display
        mTwoPane = (findViewById(R.id.android_me_linear_layout) != null);

        if (mTwoPane) {
            // Get rid of the "Next" button
            findViewById(R.id.next_button).setVisibility(View.GONE);

            // Change the GridView to space out hte images more on tablet
            GridView gridView = (GridView) findViewById(R.id.images_grid_view);
            gridView.setNumColumns(2);

            if (savedInstanceState == null) {
                // Create a new head BodyPartFragment
                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setImageIds(AndroidImageAssets.getHeads());


                // Create body and leg BodyPartFragments
                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImageIds(AndroidImageAssets.getBodies());

                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setImageIds(AndroidImageAssets.getLegs());

                // Use a FragmentManager and transaction to add the fragment to the screen
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .add(R.id.body_container, bodyFragment)
                        .add(R.id.leg_container, legFragment)
                        .commit();
            }
        } else {
            final Intent intent = new Intent(this, AndroidMeActivity.class);
            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
        }
    }

    public void onImageSelected(int position) {
        // 12 images for each type (head / body / leg)
        int bodyPartType = position / 12;
        int listIndex = position - 12 * bodyPartType;

        if (mTwoPane) {
            // Handles two-pane mode
            BodyPartFragment newFragment = new BodyPartFragment();

            // Set the currently displayed item for the corret body part fragment
            switch (bodyPartType) {
                case 0:
                    newFragment.setImageIds(AndroidImageAssets.getHeads());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction().add(R.id.head_container, newFragment).commit();
                    break;
                case 1:
                    newFragment.setImageIds(AndroidImageAssets.getBodies());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction().add(R.id.body_container, newFragment).commit();
                    break;
                case 2:
                    newFragment.setImageIds(AndroidImageAssets.getLegs());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction().add(R.id.leg_container, newFragment).commit();
                    break;
                default:
                    break;
            }
        } else {
            switch (bodyPartType) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    bodyIndex = listIndex;
                    break;
                case 2:
                    legIndex = listIndex;
                    break;
                default:
                    break;
            }

            Bundle bundle = new Bundle();
            bundle.putInt("headIndex", headIndex);
            bundle.putInt("bodyIndex", bodyIndex);
            bundle.putInt("legIndex", legIndex);

            final Intent intent = new Intent(this, AndroidMeActivity.class);
            intent.putExtras(bundle);

            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
        }
    }
}