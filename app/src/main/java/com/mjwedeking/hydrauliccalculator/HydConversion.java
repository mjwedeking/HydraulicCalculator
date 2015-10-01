package com.mjwedeking.hydrauliccalculator;

/**
 * Created by matthew on 3/12/15.
 */
        import java.util.Hashtable;

public class HydConversion {
    private Hashtable<String, Double> _factors;

    public HydConversion()
    {
        _factors = new Hashtable<String, Double>();

        //	Flow conversion factors - input units converted to SI m3/s
        _factors.put("gpm", 0.000063090574947062);
        _factors.put("lps", 0.001);
        _factors.put("mgd", 0.043812899268793);
        _factors.put("cms", 1.0);
        _factors.put("cfs", 0.028317016493754);
        _factors.put("gpd", 0.000000043812899268793);

        //	Depth conversion factors - input units converted to SI meters
        _factors.put("ft", 0.3048006096012);
        _factors.put("inch", 0.0254000508001);
        _factors.put("mm", 0.001);
        _factors.put("cm", 0.01);
        _factors.put("m", 1.0);

        //	Velocity factors converted to SI velocity unit (m/s)
        _factors.put("fps", 0.30480060960127);
        _factors.put("mph", 0.447040894081863);
        _factors.put("mps", 1.0);
        _factors.put("kph", 0.277777777777777);

        //	Area factors converted to SI area unit (m2)
        _factors.put("ac", 4046.8564224);
        _factors.put("ha", 10000.0);
        _factors.put("ft2", 0.0929);
        _factors.put("m2", 1.0);

        //	Volume factors converted to SI area unit (m3)
        _factors.put("usgal", 0.0037854);
        _factors.put("mg", 3785.4);
        _factors.put("l", 0.001);
        _factors.put("ft3", 0.0283168);
        _factors.put("m3", 1.0);

        // Slope factors converted to x/x
        _factors.put("ft/ft", 100.0);
        _factors.put("m/m", 100.0);
        _factors.put("%", 1.0);

        _factors.put("NotDefined", 1.0);


    }

    public double GetFactor(String inUnits, String outUnits)
    {
        double inFactor = 1.0;
        double outFactor = 1.0;
        try
        {
            if (_factors.get(inUnits) != null)
                inFactor = (double) _factors.get(inUnits);
            if (_factors.get(outUnits) != null)
                outFactor = (double)_factors.get(outUnits);
        }
        finally{}
        return inFactor / outFactor;
    }
}
