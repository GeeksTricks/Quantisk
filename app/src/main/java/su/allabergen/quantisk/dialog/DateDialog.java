package su.allabergen.quantisk.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Rabat on 31.01.2016.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    TextView dateToPick;

    public DateDialog(View view) {
        dateToPick = (TextView) view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String month0 = (((monthOfYear < 10) ? "0" : "") + (monthOfYear + 1));
        String day0 = (((dayOfMonth < 10) ? "0" : "") + dayOfMonth);

//        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        String date2 = year + "-" + month0 + "-" + day0;
        Log.i("DATE 2", year + "-" + month0 + "-" + day0);
        dateToPick.setText(date2);
    }
}
