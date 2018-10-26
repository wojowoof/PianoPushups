package com.wojones.pianopushups;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class NotePractice2 extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private static final String LOGTAG = "NotePractice";
    private static final int MAX_NOTES = 5;

    private static String[] notes = new String[] {
            "notes_A", "notes_A_flat", "notes_A_sharp",
            "notes_B", "notes_B_flat", "notes_B_sharp",
            "notes_C", "notes_C_flat", "notes_C_sharp",
            "notes_D", "notes_D_flat", "notes_D_sharp",
            "notes_E", "notes_E_flat", "notes_E_sharp",
            "notes_F", "notes_F_flat", "notes_F_sharp",
            "notes_G", "notes_G_flat", "notes_G_sharp",
    };
    private static boolean[] complexnotes = new boolean[] {
            /* A */ false, false, true,
            /* B */ false, false, true,
            /* C */ false, true, false,
            /* D */ false, false, true,
            /* E */ false, false, true,
            /* F */ false, true, false,
            /* G */ false, true, false,
    };
    private static String[] rhythms = new String[] {
            "img_quarter_notes", "img_half_notes", "img_whole_note",
    };
    private static int[] rhythm_values = new int[MAX_NOTES];

    Integer[] measures_values = new Integer[] {2, 3, 4, 5};
    Integer measures = 3;
    private int[] note_values = new int[MAX_NOTES];
    private TickTock tktk = new TickTock();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_practice2);

        Spinner spnr = findViewById(R.id.measureSpinner);
        spnr.setOnItemSelectedListener(this);

        ArrayAdapter<Integer> adptr =
                new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,
                        measures_values);
        adptr.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnr.setAdapter(adptr);

        spnr.setSelection(Arrays.asList(measures_values).indexOf(measures));

        // TODO: remember setting in user defaults or something
        CheckBox cbx = findViewById(R.id.chkNaturals);
        cbx.setChecked(true);

        CheckBox cplxbox = findViewById(R.id.chkComplex);
        cplxbox.setEnabled(false);
        cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton btn, boolean isChecked) {
                CheckBox cplxbox = findViewById(R.id.chkComplex);
                    Log.i(LOGTAG, (isChecked ? "Dis" : "En") + "abling complex");
                    cplxbox.setEnabled(!isChecked);
            }
        });

        SeekBar skbr = findViewById(R.id.metronomeBPM);
        skbr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                NotePractice2.this.onProgressChanged(seekBar, progress, fromUser);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        skbr.setProgress(90);
        tktk.setBPM(90);

        scrambleNotes();
        applyNotes();
    }

    public boolean naturalsOnly() {
        CheckBox cbx = findViewById(R.id.chkNaturals);
        return cbx.isChecked();
    }

    public boolean noComplex() {
        CheckBox cbx = findViewById(R.id.chkComplex);
        return !cbx.isChecked();
    }

    public void scrambleNotes() {
        Log.i(LOGTAG, "Scrambling!");
        for (int i = 0; i < note_values.length; i++) {
            int noteidx;
            do {
                do {
                    noteidx = new Random().nextInt(notes.length);
                    Log.i(LOGTAG, "note candidate for " + (1+i) + ": " + notes[noteidx]);
                } while (noComplex() && complexnotes[noteidx]);
                if (naturalsOnly()) {
                    Log.i(LOGTAG, "- simplify " + notes[noteidx]);
                    noteidx = 3 * (noteidx / 3);
                }
            } while (i > 0 && noteidx == note_values[i-1]);
            Log.i(LOGTAG, "Note #" + i + ": " + notes[noteidx] + " (" + noteidx + ")");
            note_values[i] = noteidx;

            do {
                rhythm_values[i] = new Random().nextInt(rhythms.length);
            } while (i > 0 && rhythm_values[i] == rhythm_values[i-1]);
            Log.i(LOGTAG, "rhythm #" + i + ": " + rhythms[rhythm_values[i]]);
        }
    }

    private void applyNotes() {
        for (int i = 0; i < note_values.length; i++) {
            TableRow tr = findViewById(getResources().getIdentifier("tablerow" + (i + 1), "id", getPackageName()));
            if (measures <= i) {
                Log.i(LOGTAG, "hide row " + i);
                tr.setVisibility(View.INVISIBLE);
            } else {
                tr.setVisibility(View.VISIBLE);
                TextView tv = findViewById(getResources().getIdentifier("noteText" + (i + 1), "id", getPackageName()));
                int resid_one = getResources().getIdentifier(notes[note_values[i]], "string", getPackageName());
                Log.i(LOGTAG, "apply" + i + ": " + notes[note_values[i]]);
                tv.setText(resid_one);
            }
            ImageView iv = findViewById(getResources().getIdentifier("noteImg" + (i+1), "id", getPackageName()));
            iv.setImageResource(getResources().getIdentifier(rhythms[rhythm_values[i]], "drawable", getPackageName()));
        }
    }

    public void notesButtonPressed(View vw) {
        scrambleNotes();
        applyNotes();
    }

    public void metronomeStartPressed(View vw) {
        Log.i(LOGTAG, "Metronome: Start!");
        tktk.start(NotePractice2.this);
    }

    public void metronomeStopPressed(View vw) {
        Log.i(LOGTAG, "Metronome: Halt!");
        tktk.stop();
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromuser) {
        Log.i(LOGTAG, "BPM: " + progress);
        TextView tv = findViewById(R.id.BPMText);
        tv.setText(Integer.toString(progress));
        tktk.setBPM(progress);
    }

    public void setRowCount(Integer rows) {
        Log.i(LOGTAG, "Set rows to " + rows);
        measures = rows;
        applyNotes();
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
