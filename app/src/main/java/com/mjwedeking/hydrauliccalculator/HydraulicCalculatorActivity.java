package com.mjwedeking.hydrauliccalculator;

/**
 * Created by matthew on 3/12/15.
 */
        import java.text.DecimalFormat;
        import java.text.DecimalFormatSymbols;
        import java.util.Locale;

        import android.app.Activity;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemSelectedListener;
        import android.widget.EditText;
        import android.widget.RadioButton;
        import android.widget.Spinner;
        import android.widget.TextView;

public class HydraulicCalculatorActivity extends Activity {
    /** Called when the activity is first created. */
    private Spinner spnFlowUnit, spnDepthUnit, spnDiameterUnit, spnVelocityUnit, spnSlopeUnit, spnNValueList;
    private EditText inputFlow, inputDepth, inputDiameter, inputVelocity, inputSlope, inputNValue;
    private RadioButton rdoFlow, rdoQVA, rdoVQA, rdoDepth;
    private TextView labelFlow, labelDepth, labelVelocity, labelDiameter, labelSlope, labelNValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        labelFlow = (TextView) findViewById(R.id.inputFlowLabel);
        labelDepth = (TextView) findViewById(R.id.inputDepthLabel);
        labelDiameter = (TextView) findViewById(R.id.inputDiameterLabel);
        labelVelocity = (TextView) findViewById(R.id.inputVelocityLabel);
        labelSlope = (TextView) findViewById(R.id.inputSlopeLabel);
        labelNValue = (TextView) findViewById(R.id.inputNValueLabel);

        inputFlow = (EditText) findViewById(R.id.inputFlowEditText);
        inputDepth = (EditText) findViewById(R.id.inputDepthEditText);
        inputDiameter = (EditText) findViewById(R.id.inputDiameterEditText);
        inputVelocity = (EditText) findViewById(R.id.inputVelocityEditText);
        inputSlope = (EditText) findViewById(R.id.inputSlopeEditText);
        inputNValue = (EditText) findViewById(R.id.inputNValueEditText);

        rdoFlow = (RadioButton) findViewById(R.id.radio_flow);
        rdoQVA  = (RadioButton) findViewById(R.id.radio_qva);
        rdoVQA  = (RadioButton) findViewById(R.id.radio_vqa);
        rdoDepth  = (RadioButton) findViewById(R.id.radio_depth);

        spnFlowUnit = (Spinner) findViewById(R.id.inputFlowUnitSpinner);
        spnDiameterUnit = (Spinner) findViewById(R.id.inputDiameterUnitSpinner);
        spnVelocityUnit = (Spinner) findViewById(R.id.inputVelocityUnitSpinner);
        spnDepthUnit = (Spinner) findViewById(R.id.inputDepthUnitSpinner);
        spnSlopeUnit = (Spinner) findViewById(R.id.inputSlopeUnitSpinner);
        spnNValueList = (Spinner) findViewById(R.id.inputNValueListSpinner);

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
    }

    @Override
    public void onPause(){
        super.onPause();

    }

    @Override
    public void onResume(){
        super.onResume();

    }

    public void rdoFlow_Clicked(View view){
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
    public void rdoQVA_Clicked(View view){
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
    public void rdoVQA_Clicked(View view){
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
    public void rdoDepth_Clicked(View view){
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
            }
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
        DecimalFormat fourDec = new DecimalFormat("0.0000", new
                DecimalFormatSymbols(Locale.US));
        shortString = (fourDec.format(inValue));
        return shortString;
    }
}