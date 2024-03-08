package br.com.rformagio.grpc.service;
import br.com.rformagio.grpc.AuthMetadata;
import grpc.ReadRequest;
import grpc.ReadResponse;
import grpc.RpcServiceGrpc;
import grpc.WriteRequest;
import grpc.WriteResponse;
import io.grpc.CallCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CbaGrpcService {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9092)
            .usePlaintext()
            .build();
    public String makeRead(String requestBody, String authHeader, String tenantId) {
        RpcServiceGrpc.RpcServiceBlockingStub stub = RpcServiceGrpc.newBlockingStub(channel).withCallCredentials(new AuthMetadata(authHeader, tenantId));
        ReadResponse re = stub.makeReadRequest(ReadRequest.newBuilder().setReadRequest(requestBody)
                .build());
        return  re.getResult();
    }

    public String makeWrite(String requestBody, String authHeader, String tenantId) {
        RpcServiceGrpc.RpcServiceBlockingStub stub = RpcServiceGrpc.newBlockingStub(channel).withCallCredentials(new AuthMetadata(authHeader, tenantId));
        WriteResponse re = stub.makeWriteRequest(WriteRequest.newBuilder().setGrpcPostRequest(requestBody)
                .build());
        return "{" + re.getCommandProcessingResult() + "}";
    }
}
