package com.shane.me.shanedemo.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by luckyshane on 2017/12/19.
 */

public class Server {

    private String id;
    private String showName;
    private String imageName;
    private List<Server> subServerList;
    private int level;

    public static Server newServer(String showName) {
        Server server = new Server();
        server.setShowName(showName);
        return server;
    }

    public static Server newContinentServer(String showName) {
        Server server = newServer(showName);
        server.setLevel(ServerNodeHelper.TYPE_CONTINENT);
        return server;
    }

    public static Server newCountryServer(String showName) {
        Server server = newServer(showName);
        server.setLevel(ServerNodeHelper.TYPE_COUNTRY);
        return server;
    }

    public static Server newCityServer(String showName) {
        Server server = newServer(showName);
        server.setLevel(ServerNodeHelper.TYPE_CITY);
        return server;
    }

    public static Server newStateServer(String showName) {
        Server server = newServer(showName);
        server.setLevel(ServerNodeHelper.TYPE_STATE);
        return server;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<Server> getSubServerList() {
        return subServerList;
    }

    public void addSubServer(Server server) {
        if (server != null) {
            if (subServerList == null) {
                subServerList = new LinkedList<>();
            }
            subServerList.add(server);
        }
    }

    public void removeSubServer(Server server) {
        if (server != null && subServerList != null) {
            subServerList.remove(server);
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
