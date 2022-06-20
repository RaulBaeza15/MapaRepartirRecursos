public class Entero {
    private int entero;

    public Entero(int i) {
        this.entero = i;
    }



    @Override
    public String toString() {
        return "Entero{" +
                "entero=" + entero +
                '}';
    }

    public int getEntero() {
        return entero;
    }

    public void setEntero(int entero) {
        this.entero = entero;
    }
}
