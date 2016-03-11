package com.findkos.local.findkosapps.view;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("NewApi")
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    onDateInterFace listener;

    public onDateInterFace getListener() {
        return listener;
    }

    public void setListener(onDateInterFace listener) {
        this.listener = listener;
    }

    public interface onDateInterFace {
        public void onDateListener(String valueClicked, String toValue);
    }

    int _YEAR = 0;
    int _MOUNTH = 0;
    int _DAY = 0;

    public DatePickerFragment(int year, int mount, int day) {
        // TODO Auto-generated constructor stub
        this._YEAR = year;
        this._MOUNTH = mount;
        this._DAY = day;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, _YEAR, _MOUNTH, _DAY);
        dialog.getDatePicker().setMaxDate(new Date().getTime());

        return dialog;
    }

    public void registerForListener(onDateInterFace listener) {
        this.listener = listener;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, (month + 6));
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.YEAR, year);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(c.getTime());

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

//		listener.onDateListener(new OpenDateTime().getMonth(formattedDate), sdf1.format(c.getTime()));
        listener.onDateListener(""+sdf1.format(c.getTime()), sdf1.format(c.getTime()));

    }
}