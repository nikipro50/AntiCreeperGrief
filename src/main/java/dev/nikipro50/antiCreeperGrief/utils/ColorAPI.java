package dev.nikipro50.antiCreeperGrief.utils;

public class ColorAPI {
    public static String colorize(String message){
        return message.replaceAll("&", "§");
    }
}
