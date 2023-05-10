package entidades;

import java.util.ArrayList;
import java.util.UUID;

public class Frota {
    private String code = UUID.randomUUID().toString();
    private ArrayList<Veiculo> listaVeiculos = new ArrayList<Veiculo>();

    @Override
    public String toString() {
        return "{" +
                " code='" + getCode() + "'" +
                ", listaVeiculos='" + getListaVeiculos() + "'" +
                "}";
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<Veiculo> getListaVeiculos() {
        return this.listaVeiculos;
    }

    public void setListaVeiculos(ArrayList<Veiculo> listaVeiculos) {
        this.listaVeiculos = listaVeiculos;
    }
}
