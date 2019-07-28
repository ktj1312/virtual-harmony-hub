package tk.ktj1312.virtualhub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.ktj1312.virtualhub.dto.HubDto;
import tk.ktj1312.virtualhub.dto.StatusDto;
import tk.ktj1312.virtualhub.entity.CommandEntity;
import tk.ktj1312.virtualhub.entity.DeviceEntity;
import tk.ktj1312.virtualhub.entity.HubEntity;
import tk.ktj1312.virtualhub.repository.HubEntityRepository;
import tk.ktj1312.virtualhub.service.BlasterService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hubs")
public class HarmonyController {

    @Autowired
    private HubEntityRepository hubEntityRepository;

    @Autowired
    private BlasterService blasterService;

    @GetMapping("/{hubName}/status")
    public ResponseEntity<?> getHubStatus(@PathVariable("hubName") String hubName){

        HubEntity hubEntity = hubEntityRepository.findByHubName(hubName).orElseThrow(()->new EntityNotFoundException("can't find "+hubName));
        //TODO hubIp로 http 암거나 때려보기ㅎ
        hubEntity.getHubIp();

        StatusDto status = new StatusDto();
        status.setOff(false);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("")
    public HubDto.Hub getHubs(){

        List<HubEntity> hubs = hubEntityRepository.findAll();

        HubDto.Hub response = new HubDto.Hub(hubs.stream().map(hub->hub.getHubName()).collect(Collectors.toList()));

        return response;
    }



    @GetMapping("/{hubName}/devices")
    public HubDto.Device getDevices(@PathVariable("hubName") String hubName){

        HubEntity hubEntity = hubEntityRepository.findByHubName(hubName).orElseThrow(()->new EntityNotFoundException("can't find "+hubName ));

        HubDto.Device devices = new HubDto.Device(hubEntity.getDevices());

        return devices;
    }

    @GetMapping("/{hubName}/devices/{deviceSlug}/commands")
    public HubDto.Command getCommands(@PathVariable("hubName") String hubName,@PathVariable("deviceSlug")String deviceSlug){

        HubEntity hubEntity = hubEntityRepository.findByHubNameAndDevices_slug(hubName,deviceSlug).orElseThrow(()->new EntityNotFoundException("can't find device "+deviceSlug + " connected to "+ hubName));

        List<CommandEntity> commandEntities = new ArrayList<>();

        for(DeviceEntity device : hubEntity.getDevices()){
            if(device.getSlug().equals(deviceSlug))
                commandEntities.addAll(device.getCommands());
        }

        HubDto.Command commands = new HubDto.Command(commandEntities);

        return commands;
    }

    @PostMapping("/{hubName}/devices/{deviceSlug}/commands/{commandSlug}")
    public Map<String,String> postCommands(
            @PathVariable("hubName") String hubName,
            @PathVariable("deviceSlug")String deviceSlug,
            @PathVariable("commandSlug")String commandSlug) throws JsonProcessingException , IOException , JSONException {

        HubEntity hubEntity = hubEntityRepository
                .findByHubNameAndDevices_slug(hubName,deviceSlug)
                .orElseThrow(()->new EntityNotFoundException("can't find device "+deviceSlug + " connected to "+ hubName));

        List<CommandEntity> commandEntities = new ArrayList<>();

        for(DeviceEntity device : hubEntity.getDevices()){
            if(device.getSlug().equals(deviceSlug))
                commandEntities.addAll(device.getCommands());
        }

        CommandEntity commandEntity = commandEntities.stream().filter(command->command.getSlug().equals(commandSlug))
                .findFirst().orElseThrow(()->new EntityNotFoundException("can't find command "+ commandSlug + " on device "+ deviceSlug));

        blasterService.send(hubEntity,commandEntity);

        Map<String,String> result = new HashMap<>();
        result.put("message","ok");

        return result;
    }
}
