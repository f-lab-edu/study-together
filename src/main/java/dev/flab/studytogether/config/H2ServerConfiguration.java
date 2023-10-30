package dev.flab.studytogether.config;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.SQLException;

@Profile("default")
@Configuration
public class H2ServerConfiguration {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() throws SQLException {

        Server server = run();
        if(server.isRunning(true)){
            log.info("server run success");
        }
        log.info("h2 server url = {}", server.getURL());

        return new HikariDataSource();
    }

    private Server run() throws SQLException {
        return Server.createTcpServer(
                "-tcp",
                "-tcpAllowOthers",
                "-ifNotExists",
                "-tcpPort", 9093 +"").start();
    }
}
