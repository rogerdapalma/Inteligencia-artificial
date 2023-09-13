import busca.*;
import java.util.ArrayList;
import java.util.List;

public class Travessia implements Estado {
    final int missionariosEsquerda, canibaisEsquerda, missionariosDireita, canibaisDireita;
    char barco; // 'e' para esquerda, 'd' para direita
    String op;

    public Travessia(int missionariosEsquerda, int canibaisEsquerda, int missionariosDireita, int canibaisDireita, char barco, String op) {
        this.missionariosEsquerda = missionariosEsquerda;
        this.canibaisEsquerda = canibaisEsquerda;
        this.missionariosDireita = missionariosDireita;
        this.canibaisDireita = canibaisDireita;
        this.barco = barco;
        this.op = op;
    }

    @Override
    public String getDescricao() {
        return "(" + missionariosEsquerda + "M, " + canibaisEsquerda + "C, " + missionariosDireita + "M, " + canibaisDireita + "C, " + barco + ")";
    }

    @Override
    public boolean ehMeta() {
        return missionariosEsquerda == 0 && canibaisEsquerda == 0 && missionariosDireita == 3 && canibaisDireita == 3 && barco == 'd';
    }

    @Override
    public int custo() {
        return 1;
    }

    @Override
    public List<Estado> sucessores() {
        List<Estado> visitados = new ArrayList<>();
        levar1M(visitados);
        levar1C(visitados);
        levar1M1C(visitados);
        levar2M(visitados);
        levar2C(visitados);
        return visitados;
    }

    private boolean ehValido(Travessia estado) {
        // Verifica se o número de canibais não é maior que o número de missionários em qualquer margem.
        if (estado.missionariosEsquerda > 0 && estado.missionariosEsquerda < estado.canibaisEsquerda) {
            return false;
        }
        if (estado.missionariosDireita > 0 && estado.missionariosDireita < estado.canibaisDireita) {
            return false;
        }
        return true;
    }

    private void levar1M(List<Estado> visitados) {
        if (barco == 'e' && missionariosEsquerda >= 1) {
            Travessia novo = new Travessia(missionariosEsquerda - 1, canibaisEsquerda, missionariosDireita + 1, canibaisDireita, 'd', "Levando 1 missionário para a direita");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        } else if (barco == 'd' && missionariosDireita >= 1) {
            Travessia novo = new Travessia(missionariosEsquerda + 1, canibaisEsquerda, missionariosDireita - 1, canibaisDireita, 'e', "Levando 1 missionário para a esquerda");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        }
    }

    private void levar1C(List<Estado> visitados) {
        if (barco == 'e' && canibaisEsquerda >= 1) {
            Travessia novo = new Travessia(missionariosEsquerda, canibaisEsquerda - 1, missionariosDireita, canibaisDireita + 1, 'd', "Levando 1 canibal para a direita");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        } else if (barco == 'd' && canibaisDireita >= 1) {
            Travessia novo = new Travessia(missionariosEsquerda, canibaisEsquerda + 1, missionariosDireita, canibaisDireita - 1, 'e', "Levando 1 canibal para a esquerda");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        }
    }

    private void levar1M1C(List<Estado> visitados) {
        if (barco == 'e' && missionariosEsquerda >= 1 && canibaisEsquerda >= 1) {
            Travessia novo = new Travessia(missionariosEsquerda - 1, canibaisEsquerda - 1, missionariosDireita + 1, canibaisDireita + 1, 'd', "Levando 1 missionário e 1 canibal para a direita");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        } else if (barco == 'd' && missionariosDireita >= 1 && canibaisDireita >= 1) {
            Travessia novo = new Travessia(missionariosEsquerda + 1, canibaisEsquerda + 1, missionariosDireita - 1, canibaisDireita - 1, 'e', "Levando 1 missionário e 1 canibal para a esquerda");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        }
    }

    private void levar2M(List<Estado> visitados) {
        if (barco == 'e' && missionariosEsquerda >= 2) {
            Travessia novo = new Travessia(missionariosEsquerda - 2, canibaisEsquerda, missionariosDireita + 2, canibaisDireita, 'd', "Levando 2 missionários para a direita");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        } else if (barco == 'd' && missionariosDireita >= 2) {
            Travessia novo = new Travessia(missionariosEsquerda + 2, canibaisEsquerda, missionariosDireita - 2, canibaisDireita, 'e', "Levando 2 missionários para a esquerda");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        }
    }

    private void levar2C(List<Estado> visitados) {
        if (barco == 'e' && canibaisEsquerda >= 2) {
            Travessia novo = new Travessia(missionariosEsquerda, canibaisEsquerda - 2, missionariosDireita, canibaisDireita + 2, 'd', "Levando 2 canibais para a direita");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        } else if (barco == 'd' && canibaisDireita >= 2) {
            Travessia novo = new Travessia(missionariosEsquerda, canibaisEsquerda + 2, missionariosDireita, canibaisDireita - 2, 'e', "Levando 2 canibais para a esquerda");
            if (ehValido(novo)) {
                visitados.add(novo);
            }
        }
    }

    public static void main(String[] args) {
        Travessia estadoInicial = new Travessia(3, 3, 0, 0, 'e', "estado inicial");

        // chama busca em profundidade
        System.out.println("busca em profundidade...");
        Nodo n = new BuscaProfundidade().busca(estadoInicial);
        if (n == null) {
            System.out.println("sem solução!");
        } else {
            System.out.println("solução:\n" + n.montaCaminho() + "\n\n");
        }
    }
}
