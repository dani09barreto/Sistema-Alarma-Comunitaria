package com.edu.alarmsystem.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiSelectionSpinner extends AppCompatSpinner implements
        DialogInterface.OnMultiChoiceClickListener {

    private List<String> items;
    private boolean[] selected;
    private String defaultText;
    private MultiSpinnerListener listener;

    public MultiSelectionSpinner(Context context) {
        super(context);
    }

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiSelectionSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (selected != null && which < selected.length) {
            selected[which] = isChecked;
            listener.onItemsSelected(selected);
        } else {
            throw new IllegalArgumentException("Argument 'which' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(items.toArray(new CharSequence[items.size()]), selected, this);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss());
        builder.show();
        return true;
    }

    public void setItems(List<String> items, String allText, MultiSpinnerListener listener) {
        this.items = items;
        this.defaultText = allText;
        this.listener = listener;

        selected = new boolean[items.size()];
        Arrays.fill(selected, false);

        setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{defaultText}));
    }

    public interface MultiSpinnerListener {
        void onItemsSelected(boolean[] selected);
    }
}

