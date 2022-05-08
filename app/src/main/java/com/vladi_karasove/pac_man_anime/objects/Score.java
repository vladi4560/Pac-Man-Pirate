package com.vladi_karasove.pac_man_anime.objects;

public class Score implements Comparable {
    private int score;
    private String name;
    private double latitude, longitude;

    public Score() {
    }

    public Score(int score, String name, double latitude, double longitude) {
        this.score = score;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    public int compareTo(Object o) {
        try {
            Score s1 = (Score) o;
            return s1.getScore() - score;
        } catch (Exception ex) {
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Score{" +
                "score=" + score +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
