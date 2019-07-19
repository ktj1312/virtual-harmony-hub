package tk.ktj1312.virtualhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.ktj1312.virtualhub.dto.HubDto;
import tk.ktj1312.virtualhub.dto.StatusDto;
import tk.ktj1312.virtualhub.entity.CommandEntity;
import tk.ktj1312.virtualhub.entity.DeviceEntity;
import tk.ktj1312.virtualhub.entity.HubEntity;
import tk.ktj1312.virtualhub.repository.HubEntityRepository;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hubs")
public class InfoController {

    @Autowired
    private HubEntityRepository hubEntityRepository;

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
            for(CommandEntity command : device.getCommands()){
                commandEntities.add(command);
            }
        }

        HubDto.Command commands = new HubDto.Command(commandEntities);

        return commands;
    }
}
