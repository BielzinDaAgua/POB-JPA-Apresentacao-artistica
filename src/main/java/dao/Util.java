package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class Util {
    private static EntityManagerFactory factory;

    // Substituir java.util.logging por log4j
    private static final Logger logger = LogManager.getLogger(Util.class);

    public static void iniciarFactory() {
        if (factory == null) {
            try {
                logger.info("----conectar banco - configurando manualmente");

                // Definir manualmente as configurações do banco
                String sgbd = "postgresql";
                String banco = "meu_projeto";
                String ip = "localhost";

                logger.info("sgbd => " + sgbd);
                logger.info("banco => " + banco);
                logger.info("ip => " + ip);

                // alterar propriedades do persistence.xml
                Properties configuracoes = new Properties();
                if (sgbd.equals("postgresql")) {
                    logger.info("----configurando postgresql");
                    configuracoes.setProperty("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
                    configuracoes.setProperty("jakarta.persistence.jdbc.url", "jdbc:postgresql://" + ip + ":5432/" + banco);
                    configuracoes.setProperty("jakarta.persistence.jdbc.user", "postgres");
                    configuracoes.setProperty("jakarta.persistence.jdbc.password", "admin");
                }

                String unit_name = "hibernate-postgresql";
                factory = Persistence.createEntityManagerFactory(unit_name, configuracoes);
            } catch (Exception e) {
                logger.error("Problema de configuração: " + e.getMessage(), e);
                System.exit(0);
            }
        }
    }

    public static EntityManager getEntityManager() {
        if (factory == null) {
            iniciarFactory();
        }
        return factory.createEntityManager();
    }

    public static void fecharFactory() {
        if (factory != null && factory.isOpen()) {
            factory.close();
            logger.info("Factory fechada com sucesso.");
        }
    }
}
