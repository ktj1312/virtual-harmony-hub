package tk.ktj1312.virtualhub.define;

public enum IR_CODE_TYPE {
    nec,
    sony,
    rc5,
    rc6,
    dish,
    sharp,
    jvc,
    sanyo,
    sanyo_lc7461,
    mitsubishi,
    samsung,
    lg,
    whynter,
    aiwa_rc_t501,
    panasonic,
    denon,
    coolix,
    gree,
    lutron;

    public static boolean contains(String s)
    {
        for(IR_CODE_TYPE code_type:values())
            if (code_type.name().equals(s))
                return true;
        return false;
    }
}
