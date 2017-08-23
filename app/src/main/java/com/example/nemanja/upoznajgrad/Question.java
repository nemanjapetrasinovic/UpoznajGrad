package com.example.nemanja.upoznajgrad;

import java.util.List;

/**
 * Created by Marija on 8/23/2017.
 */

public class Question {
    String tip;
    String tekst;
    List<String> ponudjeniOdgovori;
    List<String> tacniOdgovori;

    public void setTacniOdgovori(List<String> tacniOdgovori) {
        this.tacniOdgovori = tacniOdgovori;
    }

    public void setPonudjeniOdgovori(List<String> ponudjeniOdgovori) {

        this.ponudjeniOdgovori = ponudjeniOdgovori;
    }

    public void setTekst(String tekst) {

        this.tekst = tekst;
    }

    public void setTip(String tip) {

        this.tip = tip;
    }

    public List<String> getTacniOdgovori() {

        return tacniOdgovori;
    }

    public String getTekst() {

        return tekst;
    }

    public List<String> getPonudjeniOdgovori() {

        return ponudjeniOdgovori;
    }

    public String getTip() {

        return tip;
    }
}
