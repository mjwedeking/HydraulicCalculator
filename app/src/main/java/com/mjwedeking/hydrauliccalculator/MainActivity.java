package com.mjwedeking.hydrauliccalculator;

/**
 * Created by matthew on 3/12/15.
 */
        import android.os.Bundle;
        import android.app.ActionBar;
        import android.app.FragmentTransaction;
        import android.support.v4.app.FragmentActivity;
        import android.support.v4.view.ViewPager;
        import android.util.Log;

        import com.mjwedeking.hydrauliccalculator.Pipe;

public class MainActivity extends FragmentActivity
    implements Calculator.OnPipeUpdatedListener{

        ViewPager Tab;
        TabPagerAdapter TabAdapter;
        ActionBar actionBar;
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabAdapter = new TabPagerAdapter(getSupportFragmentManager());
        Tab = (ViewPager) findViewById(R.id.pager);
        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar = getActionBar();
                        actionBar.setSelectedNavigationItem(position);
                    }
                });
        Tab.setAdapter(TabAdapter);
        actionBar = getActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabReselected(android.app.ActionBar.Tab tab,
                                        FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }

            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                Tab.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(android.app.ActionBar.Tab tab,
                                        FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }
        };
        //Add New Tab
        actionBar.addTab(actionBar.newTab().setText("Calculator").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Details").setTabListener(tabListener));
    }

    public void onPipeUpdated(double dD, double qCAP, double Cap, double VelHead, double WetArea, double WetPerimiter) {
        // The user selected the headline of an article from the HeadlinesFragment
        Log.i("Main", "onPipeUpdated");
        // Capture the article fragment from the activity layout
        Details detailsFrag = (Details)
                getSupportFragmentManager().findFragmentById(R.layout.details_frag);

        if (detailsFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            detailsFrag.updatePipe(dD, qCAP, Cap, VelHead, WetArea, WetPerimiter);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            Details newFragment = new Details();
            Bundle args = new Bundle();
            args.putDouble("d/D", dD);
            newFragment.setArguments(args);
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            //transaction.replace(R.id.fragment_container, newFragment);
            //transaction.addToBackStack(null);

            // Commit the transaction
            //transaction.commit();

            //detailsFrag.updatePipe(thePipe);
        }
    }
}