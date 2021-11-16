package com.dictionary.Model;

public class Word {
    private String English;
    private String VietNamese;
    private String Topic;

    public Word() {
    }

    /*public Word(String english, String vietNamese, String topic) {
        English = english;
        VietNamese = vietNamese;
        Topic = topic;
    }*/

    public String getEnglish() {
        return English;
    }

    public void setEnglish(String english) {
        English = english;
    }

    public String getVietNamese() {
        return VietNamese;
    }

    public void setVietNamese(String vietNamese) {
        VietNamese = vietNamese;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }
}
