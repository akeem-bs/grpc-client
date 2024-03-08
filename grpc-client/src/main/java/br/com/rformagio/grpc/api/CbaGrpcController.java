package br.com.rformagio.grpc.api;

import br.com.rformagio.grpc.service.CbaGrpcService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cba")
@RequiredArgsConstructor
public class CbaGrpcController {

    private final CbaGrpcService cbaGrpcService;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/read-request", produces = MediaType.APPLICATION_JSON_VALUE)
    public String processRead(@RequestBody String requestBody, @RequestHeader("Authorization") String authHeader, @RequestHeader("fineract-platform-tenantid") String tenantId) {
        try {
            return cbaGrpcService.makeRead(requestBody, authHeader, tenantId);
        } catch (Exception e) {
            System.out.println("##########################################################################");
            System.out.println("ERRO: " + e.getMessage());
            return "{\"error\":\"" + e.getMessage()+"\"}";
        }

    }

    @PostMapping(value = "/write-request", produces = MediaType.APPLICATION_JSON_VALUE)
    public  String processWrite(@RequestBody String requestBody, @RequestHeader("Authorization") String authHeader, @RequestHeader("fineract-platform-tenantid") String tenantId) {
        try {
            return cbaGrpcService.makeWrite(requestBody, authHeader, tenantId);
        } catch (Exception e) {
            System.out.println("##########################################################################");
            System.out.println("ERRO: " + e.getMessage());
            return "{\"error\":\"" + e.getMessage()+"\"}";
        }

    }
}
