package com.psl.grpc;

import com.google.protobuf.StringValue;
import com.psl.grpc.gen.proto.CustomerRecordRequest;
import com.psl.grpc.gen.proto.CustomerRecordResponse;
import com.psl.grpc.gen.proto.CustomerServiceGrpc.CustomerServiceImplBase;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * HelloServiceImpl class.
 */
public class CustomerRecordServiceImpl extends CustomerServiceImplBase {

    private List<CustomerRecordRequest> customerRecords = new ArrayList<>();

    @Override
    public void addCustomer(CustomerRecordRequest request, StreamObserver<CustomerRecordResponse> responseObserver) {
        System.out.println("Request received from client to add customer record\n" + request);

        // Add the customer records
        customerRecords.add(request);

        String custResponse = new StringBuilder().append("Added customer : ")
            .append(request.getFirstName())
            .append(" ")
            .append(request.getLastName())
            .toString();

        CustomerRecordResponse response = CustomerRecordResponse.newBuilder()
            .setCustomerDetails(custResponse)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCustomer(StringValue request,
        StreamObserver<CustomerRecordRequest> responseObserver) {
        System.out.println("Request received from client to search customer " + request.getValue());

        for (CustomerRecordRequest custRequest : customerRecords) {
            String firstName = custRequest.getFirstName();
            String lastName = custRequest.getLastName();
            if (firstName.equalsIgnoreCase(request.getValue())
                || lastName.equalsIgnoreCase(request.getValue())) {
                responseObserver.onNext(custRequest);
                break;
            }
        }
        responseObserver.onCompleted();
    }
}
