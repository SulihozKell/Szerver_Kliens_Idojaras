package idojaras;

public class Elorejelzes {
    private String szovegesElorejelzes;
    private int min;
    private int max;

    public Elorejelzes(String elo, String minmax) {
        this.szovegesElorejelzes = elo;
        String[] st = minmax.split("/");
        this.min = Integer.parseInt(st[0]);
        this.max = Integer.parseInt(st[1]);
    }

    public String getSzovegesElorejelzes() {
        return szovegesElorejelzes;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public String toString() {
        return szovegesElorejelzes + ", min=" + min + ", max=" + max;
    }
}
