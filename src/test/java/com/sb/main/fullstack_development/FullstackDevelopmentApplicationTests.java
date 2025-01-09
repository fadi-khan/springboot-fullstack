package com.sb.main.fullstack_development;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class FullstackDevelopmentApplicationTests extends AbstractTestContainers{



    @Test
    void canStartMySQL() {
        assertThat(mysqlContainer.isRunning()).isTrue();
        assertThat(mysqlContainer.isCreated()).isTrue();

    }




}

