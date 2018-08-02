package com.example.acer.moviemonsters;

class JsonTdata {
    String video_url;
    String language;
    String name;
    String type;
    String country;
    String site;
    long size;
    String id;

    public JsonTdata(String video_url, String language, String name, String type, String county, String site, long size, String id) {
        this.video_url = video_url;
        this.country = county;
        this.language = language;
        this.name = name;
        this.type = type;
        this.site = site;
        this.size = size;
        this.id = id;


    }

    public String getVideo_url() {
        return video_url;
    }

    public String getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCountry() {
        return country;
    }

    public String getSite() {
        return site;
    }

    public long getSize() {
        return size;
    }

    public String getId() {
        return id;
    }
}
