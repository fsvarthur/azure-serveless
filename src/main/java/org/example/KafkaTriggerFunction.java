package org.example;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;

import java.util.Optional;

public class KafkaTriggerFunction {

    @FunctionName("HttpTriggerAndKafkaOutPut")
    public HttpResponseMessage HttpTriggerAndKafkaOutput(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request, @KafkaOutput(name = "httpTriggerAndKafkaOutput",
            topic = "ci", brokerList = "%BrokerList%", username = "%ConfluentCloud%", password = "%ConfluentCloduy%",
            authenticationMode = BrokerAuthenticationMode.PLAIN, sslCaLocation = "confluent_cloud_cacert.pem",
            protocol = BrokerProtocol.SASLSSL) OutputBinding<String> output, final ExecutionContext context) {
        String message = request.getQueryParameters().get("message");
        message = request.getBody().orElse(message);
        context.getLogger().info("Http trigger received message: " + message + " messages for kafka");
        output.setValue(message);
        return request.createResponseBuilder(HttpStatus.OK).body(message).build();
    }

    @FunctionName("KafkaTriggerAndKafkaOutput")
    public void KafkaTriggerAndKafkaOutput(@KafkaTrigger(name = "kafkaTriggerAndKafkaOutput", topic = "ci",
            brokerList = "%BrokerList%", consumerGroup = "$Default", username = "%ConfluentCloud%",
            password = "%ConfluentCloud%", authenticationMode = BrokerAuthenticationMode.PLAIN, protocol = BrokerProtocol.SASLSSL,
            sslCaLocation = "confluent_cloud_cacert.pem", dataType = "string") String message,
                                           @QueueOutput(name = "output", queueName = "test-kafka-cardinality-one-java",
                                                   connection = "AzureWebJobsStorage") OutputBinding<String> output,
                                           final ExecutionContext context) {
        context.getLogger().info("Kafka Output function processed a message: " + message);
        output.setValue(message);
    }
}
