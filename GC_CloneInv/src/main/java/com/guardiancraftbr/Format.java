package com.guardiancraftbr;

public class Format {

    public static String format(String string, String player, String comando)
    {
        return string.replaceAll("&","§").replaceAll("%player%", player).replaceAll("%comando%", comando);
    }

}
