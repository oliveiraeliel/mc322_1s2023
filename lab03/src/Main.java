import static entidades.Cliente.Cliente.validarCPF;

import entidades.*;
import entidades.Cliente.Cliente;

public class Main {
	public static void main(String[] args) {
		Sinistro sinistro = new Sinistro("16/03/2023", "Rua Nanci Evangelista, 25");
		Cliente cliente = new Cliente(" Eliel Oliveira da Silva ", " 539.499.396-36 ", "20/02/2004", 19, "Rua Nanci Evangelista, 25");
		Veiculo veiculo = new Veiculo("ABC-1234", "Chevrolet", "Onix");
		Seguradora seguradora = new Seguradora("Eliel Seguradoras", "123456789", "e221437@dac.unicamp.br", "Rua Saturnino, 110");

		System.out.println(validarCPF("779.610.200-39")); // verdadeiro
		System.out.println(validarCPF("779sad.610.200-39 ")); // falso
		System.out.println(validarCPF("77961020039")); // verdadeiro
		System.out.println(validarCPF("135.039.900-03")); // verdadeiro
		System.out.println(validarCPF("135.039.900-13")); // falso
		System.out.println(validarCPF("135.039.900-09")); // falso
		System.out.println(validarCPF("135.039.900-aa")); // falso


		System.out.println("id: " + new Sinistro().getId());
		System.out.println("id: " + new Sinistro().getId());
		System.out.println("id: " + new Sinistro().getId());
		for (int i=0; i < 10000000; i++) // gerando 10 milhoes de ids unicos
			new Sinistro().getId();
		System.out.println("id: " + new Sinistro().getId());

		System.out.println(cliente);
		System.out.println(veiculo);
		System.out.println(sinistro);
		System.out.println(seguradora);
	}
}
