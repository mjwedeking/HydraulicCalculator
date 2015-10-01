package com.mjwedeking.hydrauliccalculator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.mjwedeking.hydrauliccalculator.Pipe;

public class Calculator extends Fragment {
    OnPipeUpdatedListener mCallback;

    public interface OnPipeUpdatedListener {
        public void onPipeUpdated(double dD, double qCAP, double Cap, double VelHead, double WetArea, double WetPerimiter);
        //void UpdatePipe(Pipe newPipe);
    }
    private static final String FLOW = "FLOW";
    private static final String DEPTH = "DEPTH";
    private static final String DIAMETER = "DIAMETER";
    private static final String VELOCITY = "VELOCITY";
    private static final String SLOPE = "SLOPE";
    private static final String NVALUE = "NVALUE";

    private static final String FLOW_UNITS = "FLOW_UNITS";
    private static final String DEPTH_UNITS = "DEPTH_UNITS";
    private static final String DIAMETER_UNITS = "DIAMETER_UNITS";
    private static final String VELOCITY_UNITS = "VELOCITY_UNITS";
    private static final String SLOPE_UNITS = "SLOPE_UNITS";

    private double flow;
    private double depth;
    private double diameter;
    private double velocity;
    private double slope;
    private double nvalue;

    private int flowunits;
    private int depthunits;
    private int diameterunits;
    private int velocityunits;
    private int slopeunits;


    private Spinner spnFlowUnit, spnDepthUnit, spnDiameterUnit, spnVelocityUnit, spnSlopeUnit, spnNValueList;
    private EditText inputFlow, inputDepth, inputDiameter, inputVelocity, inputSlope, inputNValue;
    private EditText inputdD, inputQCap, inputCap, inputVelHead, inputWP, inputWA;
    private RadioButton rdoFlow, rdoQVA, rdoVQA, rdoDepth;
    private TextView labelFlow, labelDepth, labelVelocity, labelDiameter, labelSlope, labelNValue;
    private RadioGroup calculateRadioGroup;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View calculator = inflater.inflate(R.layout.calculator_frag, container, false);
        View details = inflater.inflate(R.layout.details_frag, container, false);
        //((TextView)android.findViewById(R.id.textView)).setText("Android");
        if(savedInstanceState == null){
            //Just Started

            flow = 0.0;
            depth = 0.0;
            diameter = 0.0;
            velocity = 0.0;
            slope = 0.0;
            nvalue = 0.013;

            flowunits = 1;
            depthunits = 1;
            diameterunits = 1;
            velocityunits = 1;
            slopeunits = 1;
        } else {
            // App is being restored

            flow = savedInstanceState.getDouble(FLOW);
            depth = savedInstanceState.getDouble(DEPTH);
            diameter = savedInstanceState.getDouble(DIAMETER);
            velocity = savedInstanceState.getDouble(VELOCITY);
            slope = savedInstanceState.getDouble(SLOPE);
            nvalue = savedInstanceState.getDouble(NVALUE);

            flowunits = savedInstanceState.getInt(FLOW_UNITS);
            depthunits = savedInstanceState.getInt(DEPTH_UNITS);
            diameterunits = savedInstanceState.getInt(DIAMETER_UNITS);
            velocityunits = savedInstanceState.getInt(VELOCITY_UNITS);
            slopeunits = savedInstanceState.getInt(SLOPE_UNITS);
        }

        labelFlow = (TextView) calculator.findViewById(R.id.inputFlowLabel);
        labelDepth = (TextView) calculator.findViewById(R.id.inputDepthLabel);
        labelDiameter = (TextView) calculator.findViewById(R.id.inputDiameterLabel);
        labelVelocity = (TextView) calculator.findViewById(R.id.inputVelocityLabel);
        labelSlope = (TextView) calculator.findViewById(R.id.inputSlopeLabel);
        labelNValue = (TextView) calculator.findViewById(R.id.inputNValueLabel);

        inputFlow = (EditText) calculator.findViewById(R.id.inputFlowEditText);
        inputDepth = (EditText) calculator.findViewById(R.id.inputDepthEditText);
        inputDiameter = (EditText) calculator.findViewById(R.id.inputDiameterEditText);
        inputVelocity = (EditText) calculator.findViewById(R.id.inputVelocityEditText);
        inputSlope = (EditText) calculator.findViewById(R.id.inputSlopeEditText);
        inputNValue = (EditText) calculator.findViewById(R.id.inputNValueEditText);

        inputdD = (EditText) details.findViewById(R.id.inputdDEditText);
        inputQCap = (EditText) details.findViewById(R.id.inputQCapEditText);
        inputCap = (EditText) details.findViewById(R.id.inputCapacityEditText);
        inputVelHead = (EditText) details.findViewById(R.id.inputVelHeadEditText);
        inputWP = (EditText) details.findViewById(R.id.inputWetPerimiterEditText);
        inputWA = (EditText) details.findViewById(R.id.inputFlowAreaEditText);

        rdoFlow = (RadioButton) calculator.findViewById(R.id.radio_flow);
        rdoQVA  = (RadioButton) calculator.findViewById(R.id.radio_qva);
        rdoVQA  = (RadioButton) calculator.findViewById(R.id.radio_vqa);
        rdoDepth  = (RadioButton) calculator.findViewById(R.id.radio_depth);

        spnFlowUnit = (Spinner) calculator.findViewById(R.id.inputFlowUnitSpinner);
        spnDiameterUnit = (Spinner) calculator.findViewById(R.id.inputDiameterUnitSpinner);
        spnVelocityUnit = (Spinner) calculator.findViewById(R.id.inputVelocityUnitSpinner);
        spnDepthUnit = (Spinner) calculator.findViewById(R.id.inputDepthUnitSpinner);
        spnSlopeUnit = (Spinner) calculator.findViewById(R.id.inputSlopeUnitSpinner);
        spnNValueList = (Spinner) calculator.findViewById(R.id.inputNValueListSpinner);

        calculateRadioGroup = (RadioGroup) calculator.findViewById(R.id.calculateRadioGroup);

        calculateRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                if(rdoFlow.isChecked())
                    FlowClicked();
                else if(rdoQVA.isChecked())
                    QVAClicked();
                else if(rdoVQA.isChecked())
                    VQAClicked();
                else if(rdoDepth.isChecked())
                    DepthClicked();
            }
        });

        inputFlow.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if(!rdoFlow.isChecked() && !rdoQVA.isChecked())
                    Calculate();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        inputDepth.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if(!rdoDepth.isChecked())
                    Calculate();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        inputDiameter.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                Calculate();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        inputVelocity.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if(rdoQVA.isChecked())
                    Calculate();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        inputSlope.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if(rdoFlow.isChecked() || rdoDepth.isChecked())
                    Calculate();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        inputNValue.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                String NValue;
                NValue = inputNValue.getText().toString();
                if (NValue == "0.011")
                    spnNValueList.setSelection(1, true);
                else if (NValue == "0.013")
                    spnNValueList.setSelection(2, true);
                else if (NValue == "0.014")
                    spnNValueList.setSelection(3, true);
                else
                    spnNValueList.setSelection(0, true);

                if(rdoFlow.isChecked() || rdoDepth.isChecked())
                    Calculate();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });


        spnFlowUnit.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Calculate();
            }
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spnVelocityUnit.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Calculate();
            }
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spnDepthUnit.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Calculate();
            }
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spnDiameterUnit.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Calculate();
            }
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spnSlopeUnit.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Calculate();
            }
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spnNValueList.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int NValueID;
                NValueID = spnNValueList.getSelectedItemPosition();
                switch (NValueID){
                    case 1:
                        inputNValue.setText("0.011");
                        break;
                    case 2:
                        inputNValue.setText("0.013");
                        break;
                    case 3:
                        inputNValue.setText("0.014");
                        break;
                }
                Calculate();
            }
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        inputNValue.setText("0.013");
        inputFlow.setText("");
        inputVelocity.setText("");
        labelFlow.setEnabled(false);
        inputFlow.setEnabled(false);
        labelVelocity.setEnabled(false);
        inputVelocity.setEnabled(false);
        inputDepth.setEnabled(true);
        inputDiameter.setEnabled(true);
        inputSlope.setEnabled(true);
        inputNValue.setEnabled(true);
        rdoFlow.setChecked(true);

        return calculator;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnPipeUpdatedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPipeUpdatedListener");
        }
    }

    @Override
    public void onPause(){
        super.onPause();

    }

    @Override
    public void onResume(){
        super.onResume();

    }

    public void FlowClicked(){
        labelFlow.setEnabled(false);
        inputFlow.setText("");
        inputFlow.setEnabled(false);
        labelVelocity.setEnabled(false);
        inputVelocity.setText("");
        inputVelocity.setEnabled(false);
        labelDepth.setEnabled(true);
        inputDepth.setEnabled(true);
        labelDiameter.setEnabled(true);
        inputDiameter.setEnabled(true);
        labelSlope.setEnabled(true);
        inputSlope.setEnabled(true);
        labelNValue.setEnabled(true);
        inputNValue.setEnabled(true);
        Calculate();
    }

    public void QVAClicked(){
        inputFlow.setText("");
        inputSlope.setText("");
        labelFlow.setEnabled(false);
        inputFlow.setEnabled(false);
        labelVelocity.setEnabled(true);
        inputVelocity.setEnabled(true);
        labelDepth.setEnabled(true);
        inputDepth.setEnabled(true);
        labelDiameter.setEnabled(true);
        inputDiameter.setEnabled(true);
        labelSlope.setEnabled(false);
        inputSlope.setEnabled(false);
        labelNValue.setEnabled(false);
        inputNValue.setEnabled(false);
        Calculate();
    }

    public void VQAClicked(){
        inputSlope.setText("");
        inputVelocity.setText("");
        labelFlow.setEnabled(true);
        inputFlow.setEnabled(true);
        labelVelocity.setEnabled(false);
        inputVelocity.setEnabled(false);
        labelDepth.setEnabled(true);
        inputDepth.setEnabled(true);
        labelDiameter.setEnabled(true);
        inputDiameter.setEnabled(true);
        labelSlope.setEnabled(true);
        inputSlope.setEnabled(false);
        labelNValue.setEnabled(true);
        inputNValue.setEnabled(false);
        Calculate();
    }

    public void DepthClicked(){
        inputDepth.setText("");
        inputVelocity.setText("");
        labelFlow.setEnabled(true);
        inputFlow.setEnabled(true);
        labelVelocity.setEnabled(false);
        inputVelocity.setEnabled(false);
        labelDepth.setEnabled(false);
        inputDepth.setEnabled(false);
        labelDiameter.setEnabled(true);
        inputDiameter.setEnabled(true);
        labelSlope.setEnabled(true);
        inputSlope.setEnabled(true);
        labelNValue.setEnabled(true);
        inputNValue.setEnabled(true);
        Calculate();
    }

    public void Calculate(){
        Pipe newPipe = new Pipe();
        HydConversion conversion = new HydConversion();

        //Get data from View
        String txtFlow = inputFlow.getText().toString();
        String txtDepth = inputDepth.getText().toString();
        String txtDiameter = inputDiameter.getText().toString();
        String txtVelocity = inputVelocity.getText().toString();
        String txtSlope = inputSlope.getText().toString();
        String txtNValue = inputNValue.getText().toString();

        String flowUnit = spnFlowUnit.getSelectedItem().toString();
        String diameterUnit = spnDiameterUnit.getSelectedItem().toString();
        String velocityUnit = spnVelocityUnit.getSelectedItem().toString();
        String depthUnit = spnDepthUnit.getSelectedItem().toString();
        String slopeUnit = spnSlopeUnit.getSelectedItem().toString();

        try
        {
            //Determine which function to run and convert units as needed
            if (rdoFlow.isChecked())           //Calculate Flow
            {
                if(txtDepth.isEmpty() || txtDiameter.isEmpty() || txtSlope.isEmpty() || txtNValue.isEmpty())
                    return;

                newPipe.setDepth(Double.parseDouble(txtDepth));
                newPipe.setDiameter(Double.parseDouble(txtDiameter));
                newPipe.setSlope(Double.parseDouble(txtSlope));
                newPipe.setNValue(Double.parseDouble(txtNValue));

                //Convert units to cfs, feet, fps, and ft/ft as needed
                newPipe.setDepth(newPipe.getDepth() * conversion.GetFactor(depthUnit, "ft"));
                newPipe.setDiameter(newPipe.getDiameter() * conversion.GetFactor(diameterUnit, "ft"));
                newPipe.setSlope(newPipe.getSlope() * conversion.GetFactor(slopeUnit, "ft/ft"));

                newPipe.QManning();

                //Convert units as needed
                newPipe.setFlow(newPipe.getFlow() * conversion.GetFactor("cfs", flowUnit));
                newPipe.setVelocity(newPipe.getVelocity() * conversion.GetFactor("fps", velocityUnit));

                //Update form
                inputFlow.setText(strPre(newPipe.getFlow()));
                inputVelocity.setText(strPre(newPipe.getVelocity()));
                //Update Details
                inputdD.setText(strPre(newPipe.getdD()));
                inputQCap.setText(strPre(newPipe.getqCAP()));
                inputCap.setText(strPre(newPipe.getCap()));
                inputVelHead.setText(strPre(newPipe.getVelHead()));
                inputWA.setText(strPre(newPipe.getWetArea()));
                inputWP.setText(strPre(newPipe.getWetPerimiter()));
            }
            else if (rdoQVA.isChecked())
            {
                if(txtDepth.isEmpty() || txtDiameter.isEmpty() || txtVelocity.isEmpty())
                    return;

                newPipe.setDepth(Double.parseDouble(txtDepth));
                newPipe.setDiameter(Double.parseDouble(txtDiameter));
                newPipe.setVelocity(Double.parseDouble(txtVelocity));

                //Convert units to cfs, feet, fps, and ft/ft as needed
                newPipe.setDepth(newPipe.getDepth() * conversion.GetFactor(depthUnit, "ft"));
                newPipe.setDiameter(newPipe.getDiameter() * conversion.GetFactor(diameterUnit, "ft"));
                newPipe.setVelocity(newPipe.getVelocity() * conversion.GetFactor(velocityUnit, "fps"));

                newPipe.QVA();

                //Convert units as needed
                newPipe.setFlow(newPipe.getFlow() * conversion.GetFactor("cfs", flowUnit));

                //Update view
                inputFlow.setText(strPre(newPipe.getFlow()));
                //Update Details
                inputdD.setText(strPre(newPipe.getdD()));
                inputQCap.setText("NaN");
                inputCap.setText("NaN");
                inputVelHead.setText(strPre(newPipe.getVelHead()));
                inputWA.setText(strPre(newPipe.getWetArea()));
                inputWP.setText(strPre(newPipe.getWetPerimiter()));
            }
            else if (rdoDepth.isChecked())       //Calculate Depth
            {
                //statusBar.Text = "Calculating... This could take awhile.";
                if(txtFlow.isEmpty() || txtDiameter.isEmpty() || txtSlope.isEmpty() || txtNValue.isEmpty())
                    return;

                newPipe.setFlow(Double.parseDouble(txtFlow));
                newPipe.setDiameter(Double.parseDouble(txtDiameter));
                newPipe.setSlope(Double.parseDouble(txtSlope));
                newPipe.setNValue(Double.parseDouble(txtNValue));

                //Convert units to cfs, feet, fps, and ft/ft as needed
                newPipe.setFlow(newPipe.getFlow() * conversion.GetFactor(flowUnit, "cfs"));
                newPipe.setDiameter(newPipe.getDiameter() * conversion.GetFactor(diameterUnit, "ft"));
                newPipe.setSlope(newPipe.getSlope() * conversion.GetFactor(slopeUnit, "ft/ft"));

                newPipe.DManning();

                //Convert units as needed
                newPipe.setDepth(newPipe.getDepth() * conversion.GetFactor("ft", depthUnit));
                newPipe.setVelocity(newPipe.getVelocity() * conversion.GetFactor("fps",velocityUnit));

                //Update view
                inputDepth.setText(strPre(newPipe.getDepth()));
                inputVelocity.setText(strPre(newPipe.getVelocity()));
                //Update Details
                inputdD.setText(strPre(newPipe.getdD()));
                inputQCap.setText(strPre(newPipe.getqCAP()));
                inputCap.setText(strPre(newPipe.getCap()));
                inputVelHead.setText(strPre(newPipe.getVelHead()));
                inputWA.setText(strPre(newPipe.getWetArea()));
                inputWP.setText(strPre(newPipe.getWetPerimiter()));
            }
            else if (rdoVQA.isChecked())          //Calculate velocity
            {
                if(txtDepth.isEmpty() || txtDiameter.isEmpty() || txtFlow.isEmpty())
                    return;

                newPipe.setFlow(Double.parseDouble(txtFlow));
                newPipe.setDepth(Double.parseDouble(txtDepth));
                newPipe.setDiameter(Double.parseDouble(txtDiameter));

                //Convert units to cfs, feet, fps, and ft/ft as needed
                newPipe.setFlow(newPipe.getFlow() * conversion.GetFactor(flowUnit, "cfs"));
                newPipe.setDepth(newPipe.getDepth() * conversion.GetFactor(depthUnit, "ft"));
                newPipe.setDiameter(newPipe.getDiameter() * conversion.GetFactor(diameterUnit, "ft"));

                newPipe.VQA();

                //Convert units as needed
                newPipe.setVelocity(newPipe.getVelocity() * conversion.GetFactor("fps", velocityUnit));

                //Update view
                inputVelocity.setText(strPre(newPipe.getVelocity()));
                //Update Details
                inputdD.setText(strPre(newPipe.getdD()));
                inputQCap.setText("NaN");
                inputCap.setText("NaN");
                inputVelHead.setText(strPre(newPipe.getVelHead()));
                inputWA.setText(strPre(newPipe.getWetArea()));
                inputWP.setText(strPre(newPipe.getWetPerimiter()));
            }

            mCallback.onPipeUpdated(newPipe.getdD(), newPipe.getqCAP(), newPipe.getCap(), newPipe.getVelHead(), newPipe.getWetArea(), newPipe.getWetPerimiter());
            //this.Cursor = Cursors.Default;
            //statusBar.Text = "Completed!";
        }
        finally
        {
            //statusBar.Text = " - * -  ERROR  - * -";
            //this.Cursor = Cursors.Default;
        }
    }
    private String strPre(double inValue)
    {
        String shortString = "";
        DecimalFormat fourDec = new DecimalFormat("0.0000", new DecimalFormatSymbols(Locale.US));
        shortString = (fourDec.format(inValue));
        return shortString;
    }
}