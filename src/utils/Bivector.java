package utils;

import static java.lang.Math.sqrt;

public class Bivector {
    public float X, Y;

    public Bivector(float X, float Y) {
        this.X = X;
        this.Y = Y;
    }

    public float getNorm() {
        return (float) sqrt(X*X + Y*Y);
    }

    public Bivector normalize(float maxNorm) {
        float norm = getNorm();
        return this.multiply(maxNorm / norm);
    }

    public Bivector add(Bivector o) {
        X += o.X;
        Y += o.Y;
        return this;
    }

    public Bivector multiply(float v) {
        X *= v;
        Y *= v;
        return this;
    }
}
