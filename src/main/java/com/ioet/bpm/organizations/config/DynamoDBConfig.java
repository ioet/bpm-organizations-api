package com.ioet.bpm.organizations.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.ioet.bpm.organizations.domain.Organization;
import lombok.extern.slf4j.Slf4j;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@EnableDynamoDBRepositories(basePackages = "com.ioet.bpm.organizations.repositories")
public class DynamoDBConfig {

    @Bean
    public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider awsCredentialsProvider) {
        AmazonDynamoDB amazonDynamoDB
                = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(awsCredentialsProvider).build();
        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new EnvironmentVariableCredentialsProvider();
    }


    @Bean
    public InitializingBean initializeTables(AmazonDynamoDB amazonDynamoDB) {
        return () -> {
            DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

            List<Class> modelClasses = new ArrayList<>();
            modelClasses.add(Organization.class);

            for (Class cls : modelClasses) {
                log.info("Creating DynamoDB table for " + cls.getSimpleName());
                CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(cls);
                tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

                boolean created = TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);

                if (created) {
                    log.info("Created DynamoDB table for " + cls.getSimpleName());
                } else {
                    log.info("Table already exists for " + cls.getSimpleName());
                }
            }
        };
    }
}