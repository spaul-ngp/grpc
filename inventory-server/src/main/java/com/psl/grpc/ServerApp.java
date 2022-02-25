package com.psl.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

/**
 * Hello world!
 */
public final class ServerApp {
    /**
     * SERVER_PORT constant.
     */
    private static final int SERVER_PORT = 8080;

    private ServerApp() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("... Starting...");

        Server server = ServerBuilder.forPort(SERVER_PORT)
            .addService(new CustomerRecordServiceImpl()).build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }
}
