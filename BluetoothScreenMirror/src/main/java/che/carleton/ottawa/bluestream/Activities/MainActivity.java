package che.carleton.ottawa.bluestream.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import che.carleton.ottawa.activity.ActivityResultBus;
import che.carleton.ottawa.activity.ActivityResultEvent;
import che.carleton.ottawa.bluestream.Fragments.BluetoothCaptureFragment;
import che.carleton.ottawa.bluestream.R;


public class MainActivity extends AppCompatActivity {

   // private CharSequence drawerTitle;
   // private CharSequence appTitle;

    // Bad to have this here, but we need to reuse the same fragment
    private BluetoothCaptureFragment mBTCaptureFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Set up tool bar for sliding drawer
     /*   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(appTitle);
        toolbar.setLogo(R.drawable.ic_home);
        appTitle = drawerTitle = getTitle();

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/

        // Set up floating button on bottom right
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext()
                                .getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.floating_action, null);
                    final PopupWindow mypopupWindow = new PopupWindow(
                            popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

                mypopupWindow.setOutsideTouchable(true);
                mypopupWindow.setTouchable(true);
                mypopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                mypopupWindow.showAsDropDown(fab, Gravity.END, 0, 0);

                    final Button btnExit = (Button) popupView.findViewById(R.id.button_exit);
                    btnExit.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mypopupWindow.dismiss();
                            int totalViewsInFragmentStack = getSupportFragmentManager().getBackStackEntryCount();
                            while (totalViewsInFragmentStack != 0) {
                                getSupportFragmentManager().popBackStack();
                                totalViewsInFragmentStack--;
                            }

                            findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                        }
                    });

                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        int orgX, orgY;
                        int offsetX, offsetY;

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    orgX = (int) event.getX();
                                    orgY = (int) event.getY();
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    offsetX = (int) event.getRawX() - orgX;
                                    offsetY = (int) event.getRawY() - orgY;
                                    mypopupWindow.update(offsetX, offsetY, -1, -1, true);
                                    break;
                            }
                            return true;
                        }
                    });
                }
        });

        fab.setVisibility(View.INVISIBLE);

        findViewById(R.id.enter_app).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBTCaptureFragment = new BluetoothCaptureFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, mBTCaptureFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityResultBus.getInstance().postQueue(
                new ActivityResultEvent(requestCode, resultCode, data));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }}

 /*   public void setTitle(CharSequence title) {
        drawerTitle = title;
        getSupportActionBar().setTitle(drawerTitle);
        //getActionBar().setTitle(drawerTitle);
    }
}*/
