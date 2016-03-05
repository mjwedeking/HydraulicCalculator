package com.mjwedeking.hydrauliccalculator;

/**
 * Created by matthew on 3/12/15.
 */

  import android.os.Bundle;
  import android.support.v4.app.Fragment;
  import android.view.LayoutInflater;
  import android.view.View;
  import android.view.ViewGroup;
  import android.widget.EditText;
  import android.util.Log;

  import java.text.DecimalFormat;
  import java.text.DecimalFormatSymbols;
  import java.util.Locale;

public class Details extends Fragment {
    double mdD, mqCAP, mCap, mVelHead, mWetArea, mWetPerimeter;

    private EditText inputdD, inputQCap, inputCap, inputVelHead, inputWP, inputWA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View details = inflater.inflate(R.layout.details_frag, container, false);

        inputdD = (EditText) details.findViewById(R.id.inputdDEditText);
        inputQCap = (EditText) details.findViewById(R.id.inputQCapEditText);
        inputCap = (EditText) details.findViewById(R.id.inputCapacityEditText);
        inputVelHead = (EditText) details.findViewById(R.id.inputVelHeadEditText);
        inputWP = (EditText) details.findViewById(R.id.inputWetPerimeterEditText);
        inputWA = (EditText) details.findViewById(R.id.inputFlowAreaEditText);

        return details;
    }
    @Override
    public void onStart() {
        super.onStart();

        updatePipe(mdD, mqCAP, mCap, mVelHead, mWetArea, mWetPerimeter);
    }

    public void updatePipe(double dD, double qCAP, double Cap, double VelHead, double WetArea, double WetPerimeter) {
        //Update Details
        Log.i("Details", "Update Details");
        inputdD.setText(strPre(dD));
        inputQCap.setText(strPre(qCAP));
        inputCap.setText(strPre(Cap));
        inputVelHead.setText(strPre(VelHead));
        inputWA.setText(strPre(WetArea));
        inputWP.setText(strPre(WetPerimeter));
    }

    private String strPre(double inValue)
    {
        String shortString = "";
        DecimalFormat fourDec = new DecimalFormat("0.0000", new DecimalFormatSymbols(Locale.US));
        shortString = (fourDec.format(inValue));
        return shortString;
    }
}