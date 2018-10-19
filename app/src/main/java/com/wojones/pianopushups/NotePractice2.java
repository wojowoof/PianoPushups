package com.wojones.pianopushups;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class NotePractice2 extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private static final String LOGTAG = "NotePractice";

    private static String[] notes = new String[]{
            "notes_A", "notes_A_flat", "notes_A_sharp",
            "notes_B", "notes_B_flat", "notes_B_sharp",
            "notes_C", "notes_C_flat", "notes_C_sharp",
            "notes_D", "notes_D_flat", "notes_D_sharp",
            "notes_E", "notes_E_flat", "notes_E_sharp",
            "notes_F", "notes_F_flat", "notes_F_sharp",
            "notes_G", "notes_G_flat", "notes_G_sharp",
    };

    Integer[] measures_values = new Integer[] {2, 3, 4, 5};
    Integer measures = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_practice2);

        Spinner spnr = findViewById(R.id.measureSpinner);
        spnr.setOnItemSelectedListener(this);

        ArrayAdapter<Integer> adptr =
                new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item,
                        measures_values);
        adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnr.setAdapter(adptr);

        spnr.setSelection(Arrays.asList(measures_values).indexOf(measures));
    }

    public void scrambleNotes() {
        Log.i(LOGTAG, "Scrambling!");
        TextView tv1 = findViewById(R.id.noteOneText);
        //tv1.setText(getString(R.string.notes_D_sharp));
        int resid_one = getResources().getIdentifier(notes[2], "string", getPackageName());
        tv1.setText(resid_one);
        ImageView iv1 = findViewById(R.id.noteOneImg);
        iv1.setImageResource(R.drawable.ic_launcher_background);
    }

    public void notesButtonPressed(View vw) {
        scrambleNotes();
    }

    public void setRowCount(Integer rows) {
        Log.i(LOGTAG, "Set rows to " + rows);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        setRowCount(measures_values[position]);
        //Toast.makeText(getApplicationContext(), measures_values[position].toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO: nothing selected stub

    }
}
