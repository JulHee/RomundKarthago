package core.datacontainers;

/**
 * Projekt: RomUndKathargo
 * Author : Julian Heeger
 * Date : 04.06.14
 * Year : 2014
 */


/**
 * Diese Klasse beinhaltet Informationen ueber den jeweils aktuellen Zustand des laufenden Spiels.
 */

public class Zustand {

    private String name;
    private Integer errorcode;

	public Zustand() {
		this.name = null;
		this.errorcode = null;
	}

    public Zustand(String name, Integer errorcode) {
        this.name = name;
        this.errorcode = errorcode;
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
