package com.nessynet.yaacs.configuration;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
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
		return AmazonDynamoDBClientBuilder.standard()
										  .withEndpointConfiguration(endpointConfiguration())
										  .withCredentials(awsCredentialsProvider())
										  .build();
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
		return new EndpointConfiguration(props.getEndpoint(), props.getRegion());
	}

	@PostConstruct
	public void createTables() {
		AmazonDynamoDB dynamoDB = amazonDynamoDB();
		ListTablesResult listTablesResult = dynamoDB.listTables();
		if (!listTablesResult.getTableNames()
							 .contains("Anime")) {
			createAnimeTable(dynamoDB);
		}
	}

	/* We do not need to specify non-key schema attributes */
	public void createAnimeTable(final AmazonDynamoDB dynamoDB) {
		List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("Id")
														  .withAttributeType("S"));
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("Title")
														  .withAttributeType("S"));

		List<KeySchemaElement> keySchemaElements = new ArrayList<>();
		keySchemaElements.add(new KeySchemaElement().withAttributeName("Id")
													.withKeyType(KeyType.HASH));
		keySchemaElements.add(new KeySchemaElement().withAttributeName("Title")
													.withKeyType(KeyType.RANGE));

		CreateTableRequest createTableRequest = new CreateTableRequest().withTableName("Anime")
																		.withAttributeDefinitions(attributeDefinitions)
																		.withKeySchema(keySchemaElements)
																		.withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(10L)
																															  .withWriteCapacityUnits(5L));
		System.out.println("Creating DynamoDB Anime table");
		CreateTableResult table = dynamoDB.createTable(createTableRequest);
	}
}
