package com.info.worldweather.model;

import java.util.List;

public class CityWeather {
    private Coord CoordObject;
    private List<Weather> WeatherObject;
    private String base;
    private Main MainObject;
    private float visibility;
    private Wind WindObject;
    private Clouds CloudsObject;
    private float dt;
    private Sys SysObject;
    private float id;
    private String name;
    private float cod;

    public Coord getCoord() { return CoordObject; }
    public List<Weather> getWeather() { return WeatherObject; }
    public String getBase() { return base; }
    public Main getMain() { return MainObject; }
    public float getVisibility() { return visibility; }
    public Wind getWind() { return WindObject; }
    public Clouds getClouds() { return CloudsObject; }
    public float getDt() { return dt; }
    public Sys getSys() { return SysObject; }
    public float getId() { return id; }
    public String getName() { return name; }
    public float getCod() { return cod; }
    public void setCoord(Coord coord) { this.CoordObject = coord; }
    public void setWeather(List<Weather> weather) { this.WeatherObject = weather; }
    public void setBase(String base) { this.base = base; }
    public void setMain(Main main) { this.MainObject = main; }
    public void setVisibility(float visibility) { this.visibility = visibility; }
    public void setWind(Wind wind) { this.WindObject = wind; }
    public void setClouds(Clouds clouds) { this.CloudsObject = clouds; }
    public void setDt(float dt) { this.dt = dt; }
    public void setSys(Sys sys) { this.SysObject = sys; }
    public void setId(float id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCod(float cod) { this.cod = cod; }
}
