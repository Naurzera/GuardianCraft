package com.guardiancraft;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StringManager {

    static DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static String censurar(String mensagem){
        String msg = mensagem;
        List<String> censurarpalavras = Main.censurarpalavras;
        for (String texto : censurarpalavras){
            if (mensagem.contains(texto)){
                msg = msg.replaceAll(texto, Main.censurar);
                System.out.println(msg);
            }
        }
        return msg;
    }

    public static boolean temDV(String msg, String sender){
        String mensagem = msg;
        mensagem = mensagem.trim();
        mensagem = mensagem.toLowerCase();
        for (String m : Main.ignore){
            if (m.equals(".")) m = "\\.";
            mensagem = mensagem.replaceAll(m,"");
        }
        for (String dv : Main.dv){
            String d = dv;
            d = d.toLowerCase();
            if (mensagem.contains(d)){
                List<String> lista = Main.getInstance().log.getStringList("Eventos");
                LocalDateTime momento = LocalDateTime.now();
                String log = "["+momento.format(formato)+"] "
                                + sender + " enviou uma mensagem que foi impedida de ser enviada."
                        + "\nMensagem: "+msg+"\nPalavra: "+dv;
                lista.add(log);
                Main.getInstance().log.set("Eventos",lista);
                Main.getInstance().log.saveConfig();
                return true;
            }
        }
        return false;
    }

    public static String qualDV(String msg){
        String mensagem = msg;
        mensagem = mensagem.trim();
        mensagem = mensagem.toLowerCase();
        for (String m : Main.ignore){
            if (m.equals(".")) m = "\\.";
            mensagem = mensagem.replaceAll(m,"");
        }
        for (String dv : Main.dv){
            String d = dv;
            d = d.toLowerCase();
            if (mensagem.contains(d)) return dv;
        }
        return null;
    }

    public static boolean capsLock(String msg, double percentage){
        String mensagem = msg;
        mensagem = mensagem.trim();
        char[] minusculas = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        char[] maiusculas = {'A','B','C','D','E','F','G','H','I','J','K','J','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        int quantidade_de_letras = 0;
        int quantidade_maiusculas = 0;
        for (int i = 0;i<mensagem.length();i++){
            for (Character c : minusculas) {
                if (c.equals(mensagem.charAt(i))){
                    quantidade_de_letras++;
                }
            }
            for (Character c : maiusculas) {
                if (c.equals(mensagem.charAt(i))){
                    quantidade_de_letras++;
                    quantidade_maiusculas++;
                }
            }
        }
        double por_cento = quantidade_de_letras/100.0;
        return quantidade_maiusculas>por_cento*percentage;
    }

}
