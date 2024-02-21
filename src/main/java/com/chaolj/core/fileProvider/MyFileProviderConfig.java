package com.chaolj.core.fileProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.chaolj.core.commonUtils.myServer.Interface.IFileServer;

@Configuration
@EnableConfigurationProperties(MyFileProviderProperties.class)
public class MyFileProviderConfig {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MyFileProviderProperties myFileProviderProperties;

    @Bean(name = "myFileProvider")
    public IFileServer MyFileProvider(){
        return new MyFileProvider(this.applicationContext, this.myFileProviderProperties);
    }
}
