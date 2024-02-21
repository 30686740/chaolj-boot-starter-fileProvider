package com.chaolj.core.fileProvider;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "myproviders.myfileprovider")
public class MyFileProviderProperties {
    private String defaultClientToken = "dev";
    private String serverHostUrl = "https://deverp.ztzs.cn/_global";
}
