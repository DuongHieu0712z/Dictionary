package com.dictionary.Model;

public class Word {
    private String EngKey;
    private String EngMean;
    private String VieKey;
    private String VieMean;
    private String Topic;

    public Word() {
    }

    public Word(String engKey, String engMean, String vieKey, String vieMean, String topic) {
        EngKey = engKey;
        EngMean = engMean;
        VieKey = vieKey;
        VieMean = vieMean;
        Topic = topic;
    }

    public String getEngKey() {
        return EngKey;
    }

    public void setEngKey(String engKey) {
        EngKey = engKey;
    }

    public String getEngMean() {
        return EngMean;
    }

    public void setEngMean(String engMean) {
        EngMean = engMean;
    }

    public String getVieKey() {
        return VieKey;
    }

    public void setVieKey(String vieKey) {
        VieKey = vieKey;
    }

    public String getVieMean() {
        return VieMean;
    }

    public void setVieMean(String vieMean) {
        VieMean = vieMean;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }
}
