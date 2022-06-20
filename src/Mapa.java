

public class Mapa {
    private int[][] mapa;

    public Mapa() {

        mapa = new int[][]{
                {0, 0, 1, 2, 3, 3, 3, 3, 2, 2, 1, 0, 0},
                {0, 0, 0, 0, 0, 3, 2, 1, 1, 1, 0, 0, 0},
                {1, 0, 1, 1, 1, 2, 1, 1, 1, 1, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 2, 0, 0, 0, 0, 0, 0}

        };



    }

    public Mapa(Mapa solucion) {

        mapa = solucion.getMapa();
    }

    public Mapa(int[][] isla1) {
        this.mapa = isla1;
    }

    public int seleccionOptima() {
        Entero mejor = new Entero(0);
        Entero actual = new Entero(0);
        Mapa mejorSolucion = new Mapa();
        seleccionOptimaAux(actual, mejorSolucion, mejor);
        System.out.println(mejorSolucion);
        return mejor.getEntero();
    }

    public void seleccionOptimaAux(Entero actual, Mapa mejorSolucion, Entero mejor) {

        if (actual.getEntero() > mejor.getEntero()) {
            mejorSolucion.setMapa(this.copiaMapa());
            mejor.setEntero(actual.getEntero());
        }

        int i = 0, j = 0;
        while (i < mapa.length) {

            if (aceptable(i, j)) {

                anotar_candidato(i, j);
                actual.setEntero(actual.getEntero() + 1);
                seleccionOptimaAux(actual, mejorSolucion, mejor);

                actual.setEntero(actual.getEntero() - 1);
                desanotar_candidato(i, j);
            }
            j++;
            if (j == mapa[i].length) {
                j = 0;
                i++;
            }
        }


    }

    private int[][] copiaMapa() {

        int[][] resultado = new int[mapa.length][mapa[0].length];
        for (int i = 0; i < mapa.length; i++) {
            System.arraycopy(mapa[i], 0, resultado[i], 0, mapa[0].length);
        }
        return resultado;
    }

    private void desanotar_candidato(int i, int j) {
        mapa[i][j] = 1;
        mapa[i - 1][j] = 1;
        mapa[i + 1][j] = 1;
        mapa[i][j + 1] = 1;
        mapa[i][j - 1] = 1;
    }

    private void anotar_candidato(int i, int j) {
        mapa[i][j] = -1;
        mapa[i - 1][j] = -2;
        mapa[i + 1][j] = -2;
        mapa[i][j + 1] = -2;
        mapa[i][j - 1] = -2;
    }

    private boolean aceptable(int i, int j) {
        return mapa[i][j] == 1 && (i - 1 >= 0 && mapa[i - 1][j] == 1) && (j - 1 >= 0 && mapa[i][j - 1] == 1) && (i + 1 < mapa.length && mapa[i + 1][j] == 1) && (j + 1 < mapa[i].length && mapa[i][j + 1] == 1);
    }


    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder();
        for (int[] linea : mapa) {
            StringBuilder recta = new StringBuilder();
            for (int num : linea) {
                switch (num) {
                    case 0 -> recta.append("█");
                    case 1 -> recta.append(" ");
                    case -1 -> recta.append("A");
                    case -2 -> recta.append("0");
                    case 2 -> recta.append("░");
                    case 3 -> recta.append("▓");
                    default -> System.out.println("Error");
                }

            }
            resultado.append(recta).append("\n");
        }
        return resultado.toString();
    }

    public int[][] getMapa() {
        return mapa;
    }

    public void setMapa(int[][] mapa) {
        this.mapa = mapa;
    }

    public int quitaArbolYMontana() {
        int original = this.seleccionOptima();
        int mejor = original;
        int actual = mejor;
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j] == 2) {
                    mapa[i][j] = 1;
                    actual = this.seleccionOptima();
                    for (int k = 0; k < mapa.length; k++) {
                        for (int l = 0; l < mapa[i].length; l++) {
                            if (mapa[i][j] == 3) {
                                mapa[i][j] = 1;
                                actual = this.seleccionOptima();
                                mapa[i][j] = 3;
                            }
                        }
                    }
                    mapa[i][j] = 2;
                }
                if (actual > mejor) {
                    mejor = actual;
                }
            }

        }
        return mejor - original;
    }
}
