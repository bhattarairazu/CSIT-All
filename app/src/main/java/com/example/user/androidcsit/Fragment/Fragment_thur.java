package com.example.user.androidcsit.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.androidcsit.Adapter.Recyclerview_timelineview;
import com.example.user.androidcsit.Database;
import com.example.user.androidcsit.Model.Routinedata_sqlite;
import com.example.user.androidcsit.R;

import java.util.ArrayList;

/**
 * Created by Razu on 12/2/2017.
 */

public class Fragment_thur extends Fragment {
    RecyclerView mrecyclerview;
    RecyclerView.LayoutManager mlayoutmanager;
    Database mdatabase ;
    Recyclerview_timelineview mrecycleradater;
    FloatingActionButton fgs;
    private ArrayList<Routinedata_sqlite> marraylists = new ArrayList<>();
    String day = "SUN";

    public Fragment_thur() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View views = inflater.inflate(R.layout.fragment_thur, container, false);

        mrecyclerview = (RecyclerView)views.findViewById(R.id.recyclers_fragmentsun);
        mlayoutmanager = new LinearLayoutManager(views.getContext(), LinearLayoutManager.VERTICAL,false);
        mrecyclerview.setLayoutManager(mlayoutmanager);
        getdatas();
       /* fgs =(FloatingActionButton)views.findViewById(R.id.fg);
        fgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intesns = new Intent(Routine.this,Eventadds.class);
                // startActivity(intesns);
                alertdialogue_addroutine();
            }
        });*/



        return views;
    }
    public void getdatas() {


        mdatabase = new Database(getContext());
        Cursor datas = mdatabase.getdata_sun("Thursday");
        Log.d("cursor", "getdatas: ----------"+datas);
        while (datas.moveToNext()) {
           Routinedata_sqlite data_sqlite = new Routinedata_sqlite();
            data_sqlite.setId(datas.getInt(0));
            //data_sqlite.setDay(datas.getString(1));
            data_sqlite.setStart_times(datas.getString(1));
            data_sqlite.setEnd_times(datas.getString(2));
            data_sqlite.setSubject(datas.getString(3));
            data_sqlite.setTeacher(datas.getString(4));
            Log.d("fragment", "getdatas: kkkkkkkkkkk///////"+datas.getString(1)+"dd+"+datas.getString(2)+"dd+"+datas.getString(3));
            marraylists.add(data_sqlite);

        }
        mrecycleradater = new Recyclerview_timelineview(marraylists,getContext());
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrecyclerview.getContext(), mlayoutmanager.setOrientation());
        //recyclerView.addItemDecoration(dividerItemDecoration);  //for divider
        mrecyclerview.setAdapter(mrecycleradater);
        mrecycleradater.notifyDataSetChanged();
    }
    //adddubg data to database
    /*
    public void adddatas_routine(String starttime,String endtime,String subject,String teachername){
        mdatabase = new Database(getContext());
        boolean res = mdatabase.adddata(new Routinedata_sqlite("Thursday",starttime,endtime,subject,teachername));
        if(res) {
            toastmessage("Data added Sucessfull");
        }else {
            toastmessage("Data added Failed");
            Log.d("ADDS", "adddatas_routine: kkkkkkkkkkk" + res);
        }
    }
    public void toastmessage(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }
    //alert dilaoure
    public void alertdialogue_addroutine(){

        AlertDialog.Builder dialoges = new AlertDialog.Builder(getContext());
        View viees = getLayoutInflater().inflate(R.layout.alertdialogue_addroutine,null);
        final Button addbtn = (Button)viees.findViewById(R.id.btn_adds);
        final Button cancelbtn = (Button)viees.findViewById(R.id.cancel_btns);
        final EditText subject =(EditText)viees.findViewById(R.id.eventdescritpion);
        final EditText teachersnames =(EditText)viees.findViewById(R.id.locations);
        final TextView starttimesss =(TextView) viees.findViewById(R.id.time_start);

        final TextView endtimesss =(TextView) viees.findViewById(R.id.time_end);
        /*final Spinner spinner_day=(Spinner)viees.findViewById(R.id.spinner_da);
        ArrayList<String> elist = new ArrayList<>();
        elist.add("SUN");
        elist.add("MON");
        elist.add("TUE");
        elist.add("WED");
        elist.add("THU");
        elist.add("FRI");
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
        });*/
        //for start timepicker
    /*
        starttimesss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog timepickerdialoges;
                timepickerdialoges = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
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
                timepickerdialoge = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
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
                //String days = spinneritems;
                String subj = subject.getText().toString();
                String tea = teachersnames.getText().toString();
                if(start_tmes.equals("") && end_tiems.equals("") && subj.equals("") && tea.equals("")){
                    toastmessage("You Cann't Leave the Field Empty!!");

                }else {
                    Log.d("Add", "onClick: daysss"+start_tmes+end_tiems+subj+tea);
                    adddatas_routine(start_tmes,end_tiems,subj,tea);
                    mdialoges.dismiss();
                }

            }
        });
    }*/

}
