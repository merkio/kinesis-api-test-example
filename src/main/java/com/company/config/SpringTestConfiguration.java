package com.company.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "com.company")
@PropertySources({
    @PropertySource(value = "classpath:/tests.properties", ignoreResourceNotFound = true)
})
public class SpringTestConfiguration {

    @Bean
    public AmazonKinesis buildAmazonKinesis(@Value("${kinesisEndpoint}") String kinesisEndpoint) {
        return AmazonKinesisClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(kinesisEndpoint, Regions.EU_CENTRAL_1.getName()))
            .build();
    }

}
