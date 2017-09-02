package com.nessynet.yaacs.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.nessynet.yaacs.repository")
public class DynamoDBConfiguration {

	DynamoDBConfigurationProperties props;

	@Autowired
	public DynamoDBConfiguration(final DynamoDBConfigurationProperties props) {
		this.props = props;
	}

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		return AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpointConfiguration()).withCredentials(awsCredentialsProvider()).build();
	}

	@Bean
	public AWSCredentialsProvider awsCredentialsProvider() {
		return new AWSStaticCredentialsProvider(awsCredentials());
	}

	@Bean
	public AWSCredentials awsCredentials() {
		return new BasicAWSCredentials(props.getAccesskey(), props.getSecretkey());
	}

	@Bean
	public EndpointConfiguration endpointConfiguration() {
		return new EndpointConfiguration(props.getEndpoint(),props.getRegion());
	}
}
