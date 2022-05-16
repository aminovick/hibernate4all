package com.hibernate4all.tutorial.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import com.hibernate4all.tutorial.conf.PersitenceConfig;
/**
 * Created by Lenovo on 24/03/2022.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PersitenceConfig.class})
@Sql({"/data/data-test.sql"})
@SqlConfig(dataSource = "dataSource",transactionManager = "transactionManager")
public class ServiveMoviesTest {
    @Autowired
    ServiveMovies service;
    @Test
    public void updateMovie() throws Exception {
        service.updateMovie(-1L,"hello");
    }

}