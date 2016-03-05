package com.mjwedeking.hydrauliccalculator;

/**
 * Created by matthew on 3/12/15.
 */
public class Pipe {


    /// flow rate
    private double _flow;
    /// depth of flow
    private double _depth;
    private double _velocity;
    private double _diameter;
    private double _slope;
    private double _nvalue;
    private double _cap;
    private double _wp;
    private double _wa;

    public Pipe()
    {
        _depth = 0.0;
        _flow = 0.0;
        _velocity = 0.0;
        _diameter = 0.0;
        _slope = 0.0;
        _nvalue = 0.013;
        _wp = 0.0;
        _wa = 0.0;
        _cap = 0.0;
    }

    public double getFlow()
    {
        return _flow;
    }
    public void setFlow(double value)
    {
        _flow = value;
    }

    public double getDepth()
    {
        return _depth;
    }
    public void setDepth(double value)
    {
        _depth = value;
    }

    public double getVelocity()
    {
        return _velocity;
    }
    public void setVelocity(double value)
    {
        _velocity = value;
    }

    public double getDiameter()
    {
        return _diameter;
    }
    public void setDiameter(double value)
    {
        _diameter = value;
    }

    public double getSlope()
    {
        return _slope;
    }
    public void setSlope(double value)
    {
        _slope = value;
    }

    public double getNValue()
    {
        return _nvalue;
    }
    public void setNValue(double value)
    {
        _nvalue = value;
    }
    public double getdD(){
        return _depth/_diameter;
    }
    public double getqCAP(){
        return _flow/_cap;
    }
    public double getWetPerimeter(){
        return _wp;
    }
    public double getWetArea(){
        return _wa;
    }
    public double getVelHead(){
        return (_velocity * _velocity)/(2 * 32.2);
    }
    public double getCap(){
        return _cap;
    }

    /// Calculate the Flow and Velocity given the Depth
    /// Calculates wetted perimeter dblWPerim
    /// calculates wetted area dblArea
    /// Uses V = 1.486/n * (A/P)^(2/3)* S^(1/2)
    /// and  Q = V * A
    public boolean QManning()
    {
        double dblTheta = 0.0;
        ///Wetted Perimeter
        double dblWPerim = 0.0;
        ///Wetted Cross Sectional Area
        double dblWArea = 0.0;
        ///Pipe Radius
        double dblRadius = _diameter / 2.0;

        _flow = 0.0;
        _velocity = 0.0;

        if (_depth == _diameter)
            dblTheta = 2.0 * Math.PI;   //full pipe theta is 2 pi
        else
            dblTheta = 2.0 * (Math.acos((dblRadius - (_depth)) / dblRadius));
        dblWPerim = dblTheta * dblRadius;
        dblWArea = dblRadius * dblRadius * (dblTheta - Math.sin(dblTheta)) / 2.0;
        _velocity = 1.486 / _nvalue * Math.pow((dblWArea / dblWPerim),(2.0/3.0)) * Math.pow(_slope,0.5);
        _flow = _velocity * dblWArea;
        _wa = dblWArea;
        _wp = dblWPerim;

        Capacity();

        return true;
    }

    /// Calculate the Flow given the Velocity and Depth
    /// calculates wetted area dblArea
    /// Uses Q = V * A
    public boolean QVA()
    {
        double dblTheta = 0.0;
        double dblWArea = 0.0;                      //wetted cross sectional area
        double dblRadius = _diameter / 2.0;        //pipe radius

        _flow = 0.0;

        if (_depth == _diameter)
            dblTheta = 2.0 * Math.PI;   //full pipe theta is 2 pi
        else
            dblTheta = 2.0 * (Math.acos((dblRadius - (_depth)) / dblRadius));
        dblWArea = dblRadius * dblRadius * (dblTheta - Math.sin(dblTheta)) / 2.0;
        _flow = _velocity * dblWArea;
        _wa = dblWArea;
        _wp = dblTheta * dblRadius;

        return true;
    }

    /// Calculate the Velocity given the Flow and Depth
    /// calculates wetted area dblArea
    /// Uses V = Q / A
    public boolean VQA()
    {
        double dblTheta = 0.0;
        double dblWArea = 0.0;                             //wetted cross sectional area
        double dblRadius = _diameter / 2.0;        //pipe radius

        _velocity = 0.0;
        if (_depth == _diameter)
            dblTheta = 2.0 * Math.PI;          //full pipe theta is 2 pi
        else
            dblTheta = 2.0 * (Math.acos((dblRadius - (_depth)) / dblRadius));

        dblWArea = dblRadius * dblRadius * (dblTheta - Math.sin(dblTheta)) / 2.0;
        _velocity = _flow / dblWArea;

        _wa = dblWArea;
        _wp = dblTheta * dblRadius;

        return true;
    }

    /// Calculate the Depth and Velocity given the Flow
    /// Increments the depth by 0.0000001 ft until
    /// the given flow is calculated and returns the
    /// resultant depth. Also calculates Velocity.
    public boolean DManning()
    {
        //double dblWPerim = 0.0;                            //wetted perimiter
        //double dblWArea = 0.0;                             //wetted cross sectional area
        //double dblCFlow = 0.0;                             //calculated flow
        //double tmpFlow = 0.0;
        //double dblTheta = 0.0;                             //theta
        //double dblRadius = _diameter / 2.0;        //radius in feet
        //int loopcount = 0;

        //tmpFlow = _flow;
        _depth = 0.0;                              //depth of flow
        _velocity = 0.0;

        if (_slope <= 0)
        {
            _depth = 0.0;
            _velocity = 0.0;
        }
        else
        {
            _depth = depthLoop(0, .94 * _diameter, _flow);
        }
        return true;
    }
    private double depthLoop(double lowDepth, double highDepth, double destFlow)
    {
        double calcDepth;

        calcDepth = (highDepth + lowDepth) / 2;
        _depth = calcDepth;
        QManning();
        if (destFlow < _flow * 0.999999)
            calcDepth = depthLoop(lowDepth, calcDepth, destFlow);
        if (destFlow > _flow * 1.000001)
            calcDepth = depthLoop(calcDepth, highDepth, destFlow);
        return calcDepth;
    }

    /// Calculate the Capacity of the pipe
    /// Calculates wetted perimeter dblWPerim
    /// calculates wetted area dblArea
    /// Uses V = 1.486/n * (A/P)^(2/3)* S^(1/2)
    /// and  Q = V * A
    public boolean Capacity()
    {
        double dblTheta = 0.0;
        ///Wetted Perimeter
        double dblWPerim = 0.0;
        ///Wetted Cross Sectional Area
        double dblWArea = 0.0;
        ///Pipe Radius
        double dblRadius = _diameter / 2.0;
        ///Velocity
        double dblVelocity = 0.0;

        _cap = 0.0;

        dblTheta = 2.0 * Math.PI;   //full pipe theta is 2 pi
        dblWPerim = dblTheta * dblRadius;
        dblWArea = dblRadius * dblRadius * Math.PI;
        dblVelocity = 1.486 / _nvalue * Math.pow((dblWArea / dblWPerim),(2.0/3.0)) * Math.pow(_slope,0.5);
        _cap = dblVelocity * dblWArea;

        return true;
    }
}