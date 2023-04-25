package org.example;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.CosmosDBInput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

public class CosmosDBTriggerFunction {

    @FunctionName("InputId")
    public HttpResponseMessage CosmosDBInputId(@HttpTrigger(name = "req", methods = {HttpMethod.GET,
            HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
                                               @CosmosDBInput(name = "item", databaseName = "DBName", containerName =
                                                       "ItemsCollection", connection = "AzureWebJobsCosmosDBConnectionString",
                                                       id = "{docId}") String item, final ExecutionContext context) {
        context.getLogger().info("Http trigger processed a request");

        if (item != null) {
            return request.createResponseBuilder(HttpStatus.OK).body("Received").build();
        } else {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Bad request").build();
        }
    }

    @FunctionName("InputIdPOJO")
    public HttpResponseMessage CosmosDBINputPojo(@HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST},
            authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request, @CosmosDBInput(name = "item",
            databaseName = "DBName", containerName = "ItemsCollection", connection = "AzureWebJobsCosmosDBConnectionString",
            id = "{docID}") String item, final ExecutionContext context) {
        context.getLogger().info("Http trigger processed request");

        if (item != null) {
            return request.createResponseBuilder(HttpStatus.OK).body("Received").build();
        }
    }
}
