package excecoes;

public class ExcecaoNegocio extends RuntimeException {
    public ExcecaoNegocio(String message) {
        super(message);
    }

    public ExcecaoNegocio(String message, Throwable cause) {
        super(message, cause);
    }

    // Métodos estáticos para criar diferentes tipos de exceções de validação
    public static ExcecaoNegocio validacao(String message) {
        return new ExcecaoNegocio(message);
    }

    // Métodos estáticos para exceções de conexão
    public static ExcecaoNegocio conexao(String message) {
        return new ExcecaoNegocio(message);
    }

    // Outros métodos para tipos específicos de exceções podem ser adicionados aqui
}
