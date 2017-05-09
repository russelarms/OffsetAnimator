package com.russelarms.offsetanimator;

class PositionGenerator {

    static float[] generatePositionOffsets() {
        float[] floats = new float[100];
        for (int i = 0; i < 100; i++) {
            floats[i] = (float) i / 100;
        }
        return floats;
    }
}
