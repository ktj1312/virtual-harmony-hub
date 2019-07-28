package tk.ktj1312.virtualhub.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tk.ktj1312.virtualhub.define.IR_CODE_TYPE;
import tk.ktj1312.virtualhub.entity.CommandEntity;
import tk.ktj1312.virtualhub.entity.HubEntity;
import tk.ktj1312.virtualhub.entity.IrCodeEntity;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class BlasterService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(HubEntity hub, CommandEntity command) throws JsonProcessingException , HttpClientErrorException , IOException , JSONException {

        IrCodeEntity irCodeEntity = command.getIr_code();

        String req;

        if(irCodeEntity.getType().equals("raw")){
            List<Integer> raw_values = objectMapper.readValue(irCodeEntity.getData(),new TypeReference<List<Integer>>(){});

            String jsonString = objectMapper.writeValueAsString(irCodeEntity);
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray jsonRawData = new JSONArray(raw_values);
            jsonObject.put("data",jsonRawData);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObject);
            req = jsonArray.toString();
        } else if(IR_CODE_TYPE.contains(irCodeEntity.getType())){
            req = objectMapper.writeValueAsString(Collections.singleton(irCodeEntity));
        } else {
            log.error("not supported type [ " + irCodeEntity.getType() + " ]");
            return;
        }

        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(hub.getHubIp())
                .path("/json")
                .queryParam("pass",hub.getHubPass())
                .queryParam("simple","1")
                .queryParam("plain",req)
                .build().toUri();

        try{
            restTemplate.getForObject(uri,String.class);
        }catch (RestClientException e){
            if(e instanceof HttpClientErrorException){
                HttpClientErrorException exception = (HttpClientErrorException) e;
                throw exception;
            }else{
                throw e;
            }
        }
    }
}
