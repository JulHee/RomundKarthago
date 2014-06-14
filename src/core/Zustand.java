package core;

/**
 * Projekt: RomUndKathargo
 * Author : Julian Heeger
 * Date : 04.06.14
 * Year : 2014
 */
public class Zustand {

    private String name;
    private Integer errorcode;

    public Zustand(String name, Integer errorcode) {
        this.name = name;
        this.errorcode = errorcode;
    }

    public Zustand() {
        this.name = null;
        this.errorcode = null;
    }

    public Zustand(Integer errorcode) {
        this.name = null;
        this.errorcode = errorcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(Integer errorcode) {
        this.errorcode = errorcode;
    }
}
