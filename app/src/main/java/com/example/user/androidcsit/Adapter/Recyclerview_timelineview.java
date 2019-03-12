package com.example.user.androidcsit.Adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.user.androidcsit.Database;
import com.example.user.androidcsit.Model.Routinedata_sqlite;
import com.example.user.androidcsit.R;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Razu on 12/8/2017.
 */

public class Recyclerview_timelineview extends RecyclerView.Adapter<Recyclerview_timelineview.MyViewHolder> {

    private List<Routinedata_sqlite> mlistview ;
    private Context mcontet;
String spinneritems;
    public Recyclerview_timelineview(List<Routinedata_sqlite> mlistview, Context mcontet) {
        this.mlistview = mlistview;
        this.mcontet = mcontet;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_timelineview,parent,false);


        return new MyViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    Routinedata_sqlite mdata = mlistview.get(position);
    holder.date_start.setText(mdata.getStart_times());
    holder.date_end.setText(mdata.getEnd_times());
    holder.subject.setText(mdata.getSubject());
    holder.teacher.setText(mdata.getTeacher());
    }
    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public int getItemCount() {
        return mlistview.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TimelineView mtimelineview;
        ImageView popupimage;
         TextView date_start,date_end,subject,teacher;
        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            mtimelineview = (TimelineView)itemView.findViewById(R.id.time_marker);
            date_start = (TextView)itemView.findViewById(R.id.text_timeline_startdate);
            date_end = (TextView)itemView.findViewById(R.id.text_timeline_enddate);
            subject =(TextView)itemView.findViewById(R.id.text_timeline_title);
            teacher =(TextView)itemView.findViewById(R.id.text_timeline_teacher);
            popupimage=(ImageView)itemView.findViewById(R.id.popupimage);
            popupimage.setOnClickListener(this);
            mtimelineview.initLine(viewType);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.popupimage:
                    showpopupmenu(view,getAdapterPosition());
            }
        }
    }
    public void showpopupmenu(View view,int id){
        PopupMenu pop = new PopupMenu(mcontet,view);
        MenuInflater mmenu = pop.getMenuInflater();
        mmenu.inflate(R.menu.menu_routinemenu,pop.getMenu());
        pop.setOnMenuItemClickListener(new MyMenuitemclicklistener(id));
        pop.show();
    }
    private class MyMenuitemclicklistener implements PopupMenu.OnMenuItemClickListener {
       int itemid;

        public MyMenuitemclicklistener(int id) {
            this.itemid = id;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            //Database mdatabase = new Database(mcontet);
             int ids = mlistview.get(itemid).getId();
            switch (menuItem.getItemId()){
                case R.id.update:
               alertdialogue_addroutine(itemid);
                    Log.d("aa", "onMenuItemClick: nnnnnnnnnnn"+menuItem.getItemId());
                    break;
                case R.id.delete:
                    //final AlertDialog.Builder builder = new AlertDialog.Builder(mcontet);
                     //Database handler = new Database(mcontet);
                    delete_alertdialogue(mcontet,itemid);
                    //builder.setTitle("Delete");
                    //builder.setMessage("Are You sure you want to delete?");
                    //builder.setIcon(R.drawable.ic_delete);
                    //builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        //@Override
                        //public void onClick(DialogInterface dialogInterface, int i) {
                            //boolean delete = handler.delete_routine(new Routinedata_sqlite(ids));
                            //if(delete){
                                //Toast.makeText(mcontet, "Successfully Deleted Item", Toast.LENGTH_SHORT).show();
                                //mlistview.remove(itemid);
                                //notifyDataSetChanged();
                                //totald.refreshDrawableState();
                            //}else{
                                //Toast.makeText(mcontet, "Failed to Delete Item", Toast.LENGTH_SHORT).show();
                            //}
                    //    //}
                    //}).setNegativeButton("No", new DialogInterface.OnClickListener() {
                      //  @Override
                        //public void onClick(DialogInterface dialogInterface, int i) {
                            //dialogInterface.cancel();
                        //}
                    //});
                  //  final AlertDialog dialoges = builder.create();
                    //dialoges.show();
                    break;
                    default:
            }

            //Log.d("e", "onMenuItemClick: ,,,,,,,"+itemid+"'"+fnmes+"["+fdescription);
            return false;
        }
}
    public void delete_alertdialogue(Context context,int position){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final Database handler = new Database(context);

        final int id = mlistview.get(position).getId();
        final int pos = position;
        builder.setTitle("Delete");
        builder.setMessage("Are You sure you want to delete?");
        builder.setIcon(R.drawable.ic_delete);
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean delete = handler.delete_routine(new Routinedata_sqlite(id));
                if(delete){
                    Toast.makeText(mcontet, "Successfully Deleted Item", Toast.LENGTH_SHORT).show();
                    mlistview.remove(pos);
                    notifyDataSetChanged();
                    //totald.refreshDrawableState();
                }else{
                    Toast.makeText(mcontet, "Failed to Delete Item", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog dialoges = builder.create();
        dialoges.show();


    }
    public void alertdialogue_addroutine(int ids){
         final Database mdatabases = new Database(mcontet);
        final int id=mlistview.get(ids).getId();

        AlertDialog.Builder dialoges = new AlertDialog.Builder(mcontet);
        View viees = LayoutInflater.from(mcontet).inflate(R.layout.alertdialogue_addroutine,null);
        final Button addbtn = (Button)viees.findViewById(R.id.btn_adds);
        final Button cancelbtn = (Button)viees.findViewById(R.id.cancel_btns);
        final EditText subject =(EditText)viees.findViewById(R.id.eventdescritpion);
        final EditText teachersnames =(EditText)viees.findViewById(R.id.locations);
        final TextView starttimesss =(TextView) viees.findViewById(R.id.time_start);
        final TextView endtimesss =(TextView) viees.findViewById(R.id.time_end);
        final Spinner spinner_day=(Spinner)viees.findViewById(R.id.spinner_da);
        subject.setText(mlistview.get(ids).getSubject());
        teachersnames.setText(mlistview.get(ids).getTeacher());
        starttimesss.setText(mlistview.get(ids).getStart_times());
        endtimesss.setText(mlistview.get(ids).getEnd_times());
        final String spintems = mlistview.get(ids).getDay();
        ArrayList<String> elist = new ArrayList<>();
        elist.add("Sunday");
        elist.add("Monday");
        elist.add("Tuesday");
        elist.add("Wednesday");
        elist.add("Thursday");
        elist.add("Friday");
        ArrayAdapter<String> madapter = new ArrayAdapter<String>(mcontet,android.R.layout.simple_spinner_item,elist);
        madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(madapter);
        spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinneritems = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                 //  adapterView.setTooltipText(spintems);
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
                timepickerdialoges = new TimePickerDialog(mcontet, new TimePickerDialog.OnTimeSetListener() {
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
                timepickerdialoge = new TimePickerDialog(mcontet, new TimePickerDialog.OnTimeSetListener() {
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
                    //toastmessage("You Cann't Leave the Field Empty!!");
                    Toast.makeText(mcontet, "You Cann't Leave the Field Empty!!", Toast.LENGTH_SHORT).show();

                }else {
                    Log.d("Add", "onClick: daysss"+start_tmes+end_tiems+subj+tea);
                    //adddatas_routine(days,start_tmes,end_tiems,subj,tea);
                    boolean updates = mdatabases.update_routine(new Routinedata_sqlite(id,days,start_tmes,end_tiems,subj,tea));
                    if(updates){
                        Toast.makeText(mcontet, "Sucessfully Updated", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        mdialoges.dismiss();
                    }else {
                        Toast.makeText(mcontet, "Updated Failed.", Toast.LENGTH_SHORT).show();
                    }
                    mdialoges.dismiss();
                }

            }
        });
    }


}
