package com.greek;

import java.text.*;
import java.math.*;

public class Omega {
    public static void main(String[] args) {
        System.out.println("Omega");
    }

    public String nthRoot(double n, double e) {

	// java.text.DecimalFormat df = new java.text.DecimalFormat("#.#");
	// df.setRoundingMode(java.math.RoundingMode.UP);

	DecimalFormat df = new DecimalFormat("#.#");
	df.setRoundingMode(RoundingMode.UP);

	double d = Math.pow(n, 1D/e);

        return df.format(d);
    }
}