package com.sakaci.couriertracking.strategy;

import org.springframework.stereotype.Component;

@Component
public class VincentyStrategy implements DistanceCalculationStrategy {
    private static final double EARTH_RADIUS = 6371;
    private static final double FLATTENING = 1 / 298.257223563;

    @Override
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double φ1 = Math.toRadians(lat1);
        double φ2 = Math.toRadians(lat2);
        double λ1 = Math.toRadians(lon1);
        double λ2 = Math.toRadians(lon2);

        double a = EARTH_RADIUS * 1000;
        double b = a * (1 - FLATTENING);
        double f = FLATTENING;

        double L = λ2 - λ1;
        double U1 = Math.atan((1 - f) * Math.tan(φ1));
        double U2 = Math.atan((1 - f) * Math.tan(φ2));

        double sinU1 = Math.sin(U1), cosU1 = Math.cos(U1);
        double sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);

        double λ = L, λʹ, sinλ, cosλ, sinSqσ, sinσ, cosσ, σ, sinα, cosSqα, cos2σM;
        int iterations = 0;
        do {
            sinλ = Math.sin(λ);
            cosλ = Math.cos(λ);
            sinSqσ = (cosU2 * sinλ) * (cosU2 * sinλ) + 
                     (cosU1 * sinU2 - sinU1 * cosU2 * cosλ) * 
                     (cosU1 * sinU2 - sinU1 * cosU2 * cosλ);
            sinσ = Math.sqrt(sinSqσ);
            if (sinσ == 0) return 0; // co-incident points
            cosσ = sinU1 * sinU2 + cosU1 * cosU2 * cosλ;
            σ = Math.atan2(sinσ, cosσ);
            sinα = cosU1 * cosU2 * sinλ / sinσ;
            cosSqα = 1 - sinα * sinα;
            cos2σM = cosσ - 2 * sinU1 * sinU2 / cosSqα;
            if (Double.isNaN(cos2σM)) cos2σM = 0; // equatorial line
            double C = f / 16 * cosSqα * (4 + f * (4 - 3 * cosSqα));
            λʹ = λ;
            λ = L + (1 - C) * f * sinα * 
                (σ + C * sinσ * (cos2σM + C * cosσ * (-1 + 2 * cos2σM * cos2σM)));
        } while (Math.abs(λ - λʹ) > 1e-12 && ++iterations < 200);

        double uSq = cosSqα * (a * a - b * b) / (b * b);
        double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
        double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
        double Δσ = B * sinσ * (cos2σM + B / 4 * 
                  (cosσ * (-1 + 2 * cos2σM * cos2σM) - 
                   B / 6 * cos2σM * (-3 + 4 * sinσ * sinσ) * 
                   (-3 + 4 * cos2σM * cos2σM)));

        return b * A * (σ - Δσ);
    }
}
