package com.example.user.androidcsit.Activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.user.androidcsit.Database;
import com.example.user.androidcsit.Fragment.Fragment_fri;
import com.example.user.androidcsit.Fragment.Fragment_mon;
import com.example.user.androidcsit.Fragment.Fragment_sun;
import com.example.user.androidcsit.Fragment.Fragment_thur;
import com.example.user.androidcsit.Fragment.Fragment_ue;
import com.example.user.androidcsit.Fragment.Fragment_wed;
import com.example.user.androidcsit.Model.Routinedata_sqlite;
import com.example.user.androidcsit.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Razu on 11/24/2017.
 */

public class Routine extends AppCompatActivity {
    //this is routine classss
    Toolbar toolbars;
    ImageView btn_back;
    TextView text_toolbars;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String spinneritems = null ;
    String endtimes,starttimes;
    Database db;
    FloatingActionButton fgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_routine);
       toolbar =(Toolbar)findViewById(R.id.toolbarroutine);
       // text_toolbars = (TextView)findViewById(R.id.tolbar_textview);
        //btn_back = (ImageView)findViewById(R.id.back_btn);

        //text_toolbars.setText(names);
        setSupportActionBar(toolbar);
        db = new Database(this);
    setTitle("Routine");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
    viewPager = (ViewPager) findViewById(R.id.viewpagers);
        viewPager.setOffscreenPageLimit(6);
    setupViewPager(viewPager);

    tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        fgs =(FloatingActionButton)findViewById(R.id.fg);
        fgs.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                //Intent intesns = new Intent(Routine.this,Eventadds.class);
                //startActivity(intesns);
                alertdialogue_addroutine();
            }
        });


}
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_sun(), "SUN");
        adapter.addFragment(new Fragment_mon(), "MON");
        adapter.addFragment(new Fragment_ue(), "TUE");
        adapter.addFragment(new Fragment_wed(), "WED");
        adapter.addFragment(new Fragment_thur(), "THU");
        adapter.addFragment(new Fragment_fri(), "FRI");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
           // text_toolbars.setText(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    /*public void getdatas() {
        mdatabase = new Database(getContext());
        int i = 0;
        Cursor datas = mdatabase.getdata_sun("Sunday");
        Log.d("cursor", "getdatas: ----------"+datas);
        while (datas.moveToNext()) {
            Routinedata_sqlite data_sqlite = new Routinedata_sqlite();
            i++;
            //data_sqlite.setId(datas.getInt(0));
            //data_sqlite.setDay(datas.getString(1));
            data_sqlite.setStart_times(datas.getString(1));
            data_sqlite.setEnd_times(datas.getString(2));
            data_sqlite.setSubject(datas.getString(3));
            data_sqlite.setTeacher(datas.getString(4));
            Log.d("fragment", "getdatas: kkkkkkkkkkk///////"+datas.getString(2)+"dd+"+datas.getString(3)+"dd+"+datas.getString(4)+"dd+"+datas.getString(1));
            marraylists.add(data_sqlite);

        }
        if(i==0){
            //mrecyclerview.setVisibility(View.GONE);
            mlayout.setVisibility(View.VISIBLE);

        }else{
            mrecyclerview.setVisibility(View.VISIBLE);
            mrecycleradater = new Recyclerview_timelineview(marraylists,getContext());
            mrecyclerview.setAdapter(mrecycleradater);
            mrecycleradater.notifyDataSetChanged();
        }

    }*/

    public void adddatas_routine(String days,String starttime,String endtime,String subject,String teachername){
        db = new Database(this);
        boolean res = db.adddata(new Routinedata_sqlite(days,starttime,endtime,subject,teachername));
        if(res) {
            toastmessage("Data added Sucessfull");
        }else {
            toastmessage("Data added Failed");
            Log.d("ADDS", "adddatas_routine: kkkkkkkkkkk" + res);
        }
    }
    public void toastmessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    //alert dilaoure
    public void alertdialogue_addroutine(){

        AlertDialog.Builder dialoges = new AlertDialog.Builder(this);
        View viees = getLayoutInflater().inflate(R.layout.alertdialogue_addroutine,null);
        final Button addbtn = (Button)viees.findViewById(R.id.btn_adds);
        final Button cancelbtn = (Button)viees.findViewById(R.id.cancel_btns);
        final EditText subject =(EditText)viees.findViewById(R.id.eventdescritpion);
        final EditText teachersnames =(EditText)viees.findViewById(R.id.locations);
        final TextView starttimesss =(TextView) viees.findViewById(R.id.time_start);

        final TextView endtimesss =(TextView) viees.findViewById(R.id.time_end);
        final Spinner spinner_day=(Spinner)viees.findViewById(R.id.spinner_da);
        ArrayList<String> elist = new ArrayList<>();
        elist.add("Sunday");
        elist.add("Monday");
        elist.add("Tuesday");
        elist.add("Wednesday");
        elist.add("Thursday");
        elist.add("Friday");
        ArrayAdapter<String> madapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,elist);
        madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(madapter);
        spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinneritems = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //for start timepicker
        starttimesss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timepickerdialoges;
                timepickerdialoges = new TimePickerDialog(Routine.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int ihour, int i1minute) {
                        starttimesss.setText( ihour + ":" + i1minute);
                        final long dadtasss = ihour*3600000;
                        final long dat_mins = i1minute*60000;
                        final long add = dadtasss + dat_mins;
                        //starttimes = Long.toString(add);
                    }
                },hour,minute,true);
                timepickerdialoges.setTitle("SELECT TIME");
                timepickerdialoges.show();
            }
        });
        //for endtimeselect listener
        endtimesss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timepickerdialoge;
                timepickerdialoge = new TimePickerDialog(Routine.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int ihour, int i1minute) {
                        endtimesss.setText( ihour + ":" + i1minute);


                        final long dadtas = ihour*3600000;
                        final long dat_min = i1minute*60000;
                        final long add = dadtas + dat_min;
                        // endtimes = Long.toString(add);
                        Log.d("TIMES", "onTimeSet: mmmmmm"+dadtas+";;;;;;;"+dat_min);
                    }
                },hour,minute,true);
                timepickerdialoge.setTitle("SELECT TIME");
                timepickerdialoge.show();



            }
        });

        dialoges.setView(viees);
        final AlertDialog mdialoges = dialoges.create();
        mdialoges.show();
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdialoges.dismiss();
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start_tmes = starttimesss.getText().toString();
                String end_tiems = endtimesss.getText().toString();
                String days = spinneritems;
                String subj = subject.getText().toString();
                String tea = teachersnames.getText().toString();
                if(start_tmes.equals("") && end_tiems.equals("") && subj.equals("") && tea.equals("")){
                    toastmessage("You Cann't Leave the Field Empty!!");

                }else {
                    Log.d("Add", "onClick: daysss"+start_tmes+end_tiems+subj+tea);
                    adddatas_routine(days,start_tmes,end_tiems,subj,tea);
                    mdialoges.dismiss();
                }

            }
        });
    }


}
