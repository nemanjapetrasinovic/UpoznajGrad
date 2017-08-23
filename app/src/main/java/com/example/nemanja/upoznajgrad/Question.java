package com.example.nemanja.upoznajgrad;

import java.util.List;

/**
 * Created by Marija on 8/23/2017.
 */

public class Question {
    String tip;
    String tekst;
    String ponudjeniOdgovori;
    String tacniOdgovori;

    public void setTacniOdgovori(String tacniOdgovori) {
        this.tacniOdgovori = tacniOdgovori;
    }

    public void setPonudjeniOdgovori(String ponudjeniOdgovori) {

        this.ponudjeniOdgovori = ponudjeniOdgovori;
    }

    public void setTekst(String tekst) {

        this.tekst = tekst;
    }

    public void setTip(String tip) {

        this.tip = tip;
    }

    public String getTacniOdgovori() {

        return tacniOdgovori;
    }

    public String getTekst() {

        return tekst;
    }

    public String getPonudjeniOdgovori() {

        return ponudjeniOdgovori;
    }

    public String getTip() {

        return tip;
    }
}
