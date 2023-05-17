package menu;

import java.util.Date;
import java.util.Map;

import javax.print.attribute.IntegerSyntax;

import entidades.Seguradora;
import entidades.Veiculo;
import entidades.Cliente.Cliente;
import entidades.Cliente.Condutor;
import entidades.Seguro.Seguro;
import execeptions.CondutorNaoAssociadoException;
import execeptions.CondutorNaoEncontradoException;
import execeptions.SeguradoraNaoEncontradaException;
import execeptions.SeguroNaoEncontradoException;
import execeptions.ValorNaoEsperadoException;
import utils.InputUtils;

public enum MenuOperacoes {
    CADASTRAR(1),
    LISTAR(2),
    EXCLUIR(3),
    GERAR_SINISTRO(4),
    TRANSFERIR_SEGURO(5),
    CALCULAR_RECEITA_SEGURADORA(6),
    CALCULAR_VALOR_SEGURO(7),
    SAIR(0);

    private final int value;

    MenuOperacoes(int value) {
        this.value = value;
    }

    public static void menu(Map<String, Seguradora> seguradoras, Map<Integer, Seguro> seguros,
            Map<String, Condutor> condutores) {
        System.out.println("-------------- MENU -------------");
        System.out.println("1- Cadastros");
        System.out.println("2- Listar");
        System.out.println("3- Excluir");
        System.out.println("4- Gerar Sinistro");
        System.out.println("5- Transferir Seguro");
        System.out.println("6- Calcular Receita Seguradora");
        System.out.println("7- Calcular Seguro do Cliente");
        System.out.println("0- Sair");
        int operacao = InputUtils.lerInt();

        try {
            MenuOperacoes o = getOperacao(operacao);
            if (handle(o, seguradoras)) {
                menu(seguradoras, seguros, condutores);
            }
        } catch (ValorNaoEsperadoException e) {
            menu(seguradoras, seguros, condutores);
        }
    }

    private static boolean handle(MenuOperacoes operacao, Map<String, Seguradora> seguradoras) {
        switch (operacao) {
            case CADASTRAR:
                MenuCadastrar.cadastrar(seguradoras);
                break;
            case LISTAR:
                MenuListar.listar(seguradoras);
                break;
            case EXCLUIR:
                MenuExcluir.excluir(seguradoras);
                break;
            case GERAR_SINISTRO:
                gerarSinistro(seguradoras);
                break;
            case TRANSFERIR_SEGURO:
                transferirSeguro(seguradoras);
                break;
            case CALCULAR_RECEITA_SEGURADORA:
                calcularReceitaSeguradora(seguradoras);
                break;
            case CALCULAR_VALOR_SEGURO:
                calcularValorSeguro(seguradoras);
                break;
            case SAIR:
                return false;
        }
        return true;
    }

    public static Condutor getCondutor(Map<String, Condutor> condutores, String cpf)
            throws CondutorNaoEncontradoException {
        Condutor condutor = condutores.get(cpf);
        if (condutor != null) {
            return condutor;
        }
        throw new CondutorNaoEncontradoException("O CPF " + cpf + " não corresponde à nenhum condutor.");
    }

    public static Seguro getSeguro(Map<Integer, Seguro> seguros, Integer id)
            throws SeguroNaoEncontradoException {
        Seguro seguro = seguros.get(id);
        if (seguro != null) {
            return seguro;
        }
        throw new SeguroNaoEncontradoException("O #id " + id + " não corresponde à nenhum seguro.");
    }

    public static Seguradora getSeguradora(Map<String, Seguradora> seguradoras, String cnpj) throws SeguradoraNaoEncontradaException{
        Seguradora seguradora = seguradoras.get(cnpj);
        if (seguradora != null){
            return seguradora;
        }
        throw new SeguradoraNaoEncontradaException("O CNPJ " + cnpj + " não corresponde à nenhuma seguradora.");
    }

    private static void gerarSinistro(Map<Integer, Seguro> seguros, Map<String, Condutor> condutores) {
        try {
            Integer idSeguro = InputUtils.lerInt("ID do seguro: ");
            Seguro seguro = getSeguro(seguros, idSeguro);
            String cpfCondutor = InputUtils.lerCPF("CPF do Condutor: ");
            Condutor condutor = getCondutor(condutores, cpfCondutor);
            Date data = InputUtils.lerData("Data do sinistro (dd/mm/yyyy): ");
            String endereco = InputUtils.lerString("Endereço: ");
            seguro.gerarSinistro(data, endereco, condutor);
            System.out.println("Sinistro gerado com sucesso.");
        } catch (SeguroNaoEncontradoException e) {
            System.out.println(e.getMessage());
        } catch(CondutorNaoEncontradoException e){
            System.out.println(e.getMessage());
        } catch (CondutorNaoAssociadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void transferirSeguro(Map<String, Seguradora> seguradoras) {
        String nomeSeguradora1, nomeSeguradora2, cadastroDe, cadastroPara;
        Cliente de, para;
        nomeSeguradora1 = InputUtils.lerNome("Nome da seguradora: ");
        if (seguradoras.containsKey(nomeSeguradora1)) {
            Seguradora seguradora = seguradoras.get(nomeSeguradora1);
            cadastroDe = InputUtils.lerCadastro("Cadastro do cliente que irá transferir: ");
            de = seguradora.getClienteByCadastro(cadastroDe);
            if (de == null) {
                return;
            }
        } else {
            System.out.printf("A seguradora %s não existe\n", nomeSeguradora1);
            return;
        }
        nomeSeguradora2 = InputUtils.lerNome("Nome da seguradora: ");
        if (seguradoras.containsKey(nomeSeguradora2)) {
            Seguradora seguradora = seguradoras.get(nomeSeguradora2);
            cadastroPara = InputUtils.lerCadastro("Cadastro do cliente que irá receber: ");
            para = seguradora.getClienteByCadastro(cadastroPara);
            if (para == null) {
                return;
            }
        } else {
            System.out.printf("A seguradora %s não existe\n", nomeSeguradora1);
            return;
        }
        de.transferirSeguro(para);
        System.out.printf("Seguro do cliente %s foi transferido para o cliente %s.\n", de.getNome(), para.getNome());
    }

    private static void calcularReceitaSeguradora(Map<String, Seguradora> seguradoras) {
        String nomeSeguradora = InputUtils.lerNome("Nome da seguradora: ");
        if (seguradoras.containsKey(nomeSeguradora)) {
            Seguradora seguradora = seguradoras.get(nomeSeguradora);
            Double receita = seguradora.calcularReceita();
            System.out.printf("A receita da seguradora %s é de R$ %.2f\n", nomeSeguradora, receita);
        } else {
            System.out.printf("A seguradora %s não existe\n", nomeSeguradora);
        }
    }

    private static void calcularValorSeguro(Map<String, Seguradora> seguradoras) {
        String nomeSeguradora = InputUtils.lerNome("Nome da seguradora: ");
        if (seguradoras.containsKey(nomeSeguradora)) {
            Seguradora seguradora = seguradoras.get(nomeSeguradora);
            String cadastro = InputUtils.lerCadastro();
            Cliente cliente = seguradora.getClienteByCadastro(cadastro);
            if (cliente != null) {
                System.out.printf("O valor do seu seguro é R$ %.2f\n", seguradora.calcularPrecoSeguroCliente(cliente));
            } else {
                System.out.println("Cliente não encontrado.");
            }
        } else {
            System.out.printf("A seguradora %s não existe\n", nomeSeguradora);
        }
    }

    public int getValue() {
        return value;
    }

    public static MenuOperacoes getOperacao(int operacao) throws ValorNaoEsperadoException{
        switch (operacao) {
            case 1:
                return CADASTRAR;
            case 2:
                return LISTAR;
            case 3:
                return EXCLUIR;
            case 4:
                return GERAR_SINISTRO;
            case 5:
                return TRANSFERIR_SEGURO;
            case 6:
                return CALCULAR_RECEITA_SEGURADORA;
            case 7:
                return CALCULAR_VALOR_SEGURO;
            case 0:
                return SAIR;
            default:
            throw new ValorNaoEsperadoException("Valor não esperado")
        }
    }
}