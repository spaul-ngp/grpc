package com.psl.grpc;

import com.google.protobuf.StringValue;
import com.psl.grpc.gen.proto.CustomerRecordRequest;
import com.psl.grpc.gen.proto.CustomerRecordResponse;
import com.psl.grpc.gen.proto.CustomerServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ProductClientApp class.
 */
public final class ProductClientApp {
    /**
     * SERVER_PORTconstant.
     */
    private static final int SERVER_PORT = 8080;

    private ProductClientApp() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", SERVER_PORT)
            .usePlaintext()
            .build();

        CustomerServiceGrpc.CustomerServiceBlockingStub stub
            = CustomerServiceGrpc.newBlockingStub(channel);

        ProductClientApp clientApp = new ProductClientApp();
        boolean continueLoop = true;
        while (continueLoop) {
            CustomerRecordResponse custResponse = stub.addCustomer(clientApp.readCustomer());
            System.out.println("Response received from server:\n" + custResponse);
            if (clientApp.getCustomerRecords().trim().toLowerCase().equals("n")) {
                break;
            }
        }

        // Search from list of customers
        String searchUser = clientApp.getCustomerRecordToSearch();
        CustomerRecordRequest userRecord = stub.getCustomer(StringValue.of(searchUser));
        System.out.println("Response received from server:");
        System.out.println(userRecord.getFirstName() + " " + userRecord.getLastName());

        channel.shutdown();
    }

    /*
    * Get customer records.
    */
    private CustomerRecordRequest readCustomer() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Add first name: ");
        String firstName = reader.readLine();

        System.out.println("Add last name: ");
        String lastName = reader.readLine();

        CustomerRecordRequest custRequest = CustomerRecordRequest.newBuilder()
            .setFirstName(firstName)
            .setLastName(lastName)
            .build();

        return custRequest;
    }

    /*
    * Continue get customer records.
    */
    private String getCustomerRecords() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Add more records (Y|N):");
        String userInput = reader.readLine();

        return userInput;
    }

    /*
    * Get customer record search filter.
    */
    private String getCustomerRecordToSearch() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter first name / last name of customer to search:\n");
        String userInput = reader.readLine();

        return userInput;
    }
}
