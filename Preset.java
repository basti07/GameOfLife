public enum Preset {
    INFINITE(new boolean[][]{{true,true,true,true,true,true,true,true,false,true,true,true,true,true,false,false,false,true,true,true,false,false,false,false,false,false,true,true,true,true,true,true,true,false,true,true,true,true,true,}}),

    U(new boolean[][]{
            {true, true, true},
            {true, false, true},
            {true, false, true},
            {false, false, false},
            {true, false, true},
            {true, false, true},
            {true, true, true}
    }),

    FPENTO(new boolean[][]{
            {false, true, true},
            {true, true, false},
            {false, true, false}
    }),

    GLIDE(new boolean[][] {
            {false, true,  false},
            {false, false, true },
            {true,  true,  true }
    }),

    PULSATOR(new boolean[][]{
            {false, false, true, false, false, false, false, true, false, false},
            {true, true, false,true,true,true,true,false,true,true},
            {false, false, true, false, false, false, false, true, false, false}
    }),

    BLINKER(new boolean[][]{
            {false, false, false},
            {true, true, true},
            {false, false, false}
    });

    public final boolean[][] pattern;
    Preset(boolean[][] pattern){
        this.pattern = pattern;
    }
}
