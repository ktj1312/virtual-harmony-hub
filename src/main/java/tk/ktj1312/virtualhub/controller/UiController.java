package tk.ktj1312.virtualhub.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tk.ktj1312.virtualhub.dto.StatusDto;
import tk.ktj1312.virtualhub.entity.CommandEntity;
import tk.ktj1312.virtualhub.entity.DeviceEntity;
import tk.ktj1312.virtualhub.entity.HubEntity;
import tk.ktj1312.virtualhub.entity.IrCodeEntity;
import tk.ktj1312.virtualhub.repository.CommandEntityRepository;
import tk.ktj1312.virtualhub.repository.DeviceEntityRepository;
import tk.ktj1312.virtualhub.repository.HubEntityRepository;
import tk.ktj1312.virtualhub.vo.RegistCommandVo;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ui")
@Slf4j
public class UiController {

    @Autowired
    private HubEntityRepository hubEntityRepository;

    @Autowired
    private DeviceEntityRepository deviceEntityRepository;

    @Autowired
    private CommandEntityRepository commandEntityRepository;

    @GetMapping("")
    public String index(Model model){

        List<HubEntity> hubs = hubEntityRepository.findAll();
        model.addAttribute("hubs",hubs);

        return "hubs";
    }

    @GetMapping("/hubs/register")
    public String registerHub(Model model){

        model.addAttribute("hub",new HubEntity());

        return "hubs/register";
    }

    @PostMapping("/hubs/register")
    public String registerHub(@ModelAttribute HubEntity hubEntity){

        hubEntityRepository.save(hubEntity);

        return "redirect:/ui";
    }

    @GetMapping("/hubs/{hubId}/modify")
    public String modifyHub(Model model, @PathVariable("hubId")String id){

        HubEntity hubEntity = hubEntityRepository.findById(Long.valueOf(id)).orElseThrow(()->new EntityNotFoundException("can't find "+id ));
        model.addAttribute("hub",hubEntity);
        return "hubs/modify";
    }

    @PostMapping("/hubs/{hubId}/modify")
    public String doModifyHub(Model model, @PathVariable("hubId")String id,@ModelAttribute HubEntity request){

        HubEntity hubEntity = hubEntityRepository.findById(Long.valueOf(id)).orElseThrow(()->new EntityNotFoundException("can't find "+id ));

        hubEntity.setHubName(request.getHubName());
        hubEntity.setHubIp(request.getHubIp());
        hubEntity.setHubPass(request.getHubPass());

        hubEntity = hubEntityRepository.save(hubEntity);
        model.addAttribute("hub",hubEntity);
        return "redirect:/ui";
    }

    @GetMapping("/hubs/{hubId}/delete")
    public String deleteHub(@PathVariable("hubId")String id){

        hubEntityRepository.deleteById(Long.valueOf(id));

        return "redirect:/ui";
    }

    @GetMapping("/{hubName}/devices")
    public String hub_devices(Model model,@PathVariable("hubName")String hubName){

        List<HubEntity> hubs = hubEntityRepository.findAll();
        model.addAttribute("hubs",hubs);

        HubEntity hub = hubEntityRepository.findByHubName(hubName).orElseThrow(()->new EntityNotFoundException("can't find "+hubName ));

        model.addAttribute("hub",hub);

        return "devices/list";
    }

    @GetMapping("/{hubName}/devices/register")
    public String registerDevice(Model model){

        model.addAttribute("device",new DeviceEntity());

        return "devices/register";
    }

    @PostMapping("/{hubName}/devices/register")
    public String registerDevice(@PathVariable("hubName")String hubName , @ModelAttribute DeviceEntity deviceEntity){

        HubEntity hubEntity = hubEntityRepository.findByHubName(hubName).orElseThrow(()->new EntityNotFoundException("can't find "+hubName ));

        hubEntity.addDevice(deviceEntity);

        hubEntityRepository.save(hubEntity);

        return "redirect:/ui/" + hubName + "/devices";
    }

    @GetMapping("/hubs/{hubId}/devices/{deviceId}/modify")
    public String modifyDevice(Model model, @PathVariable("hubId")String hubId, @PathVariable("deviceId")String deviceId){

        DeviceEntity deviceEntity = deviceEntityRepository.findById(Long.valueOf(deviceId)).orElseThrow(()->new EntityNotFoundException("can't find "+ deviceId ));
        model.addAttribute("device",deviceEntity);
        return "devices/modify";
    }

    @PostMapping("/hubs/{hubId}/devices/{deviceId}/modify")
    public String doModifyDevice(Model model, @PathVariable("hubId")String hubId,@PathVariable("deviceId")String deviceId,@ModelAttribute DeviceEntity request){

        HubEntity hubEntity = hubEntityRepository.findById(Long.valueOf(hubId)).orElseThrow(()->new EntityNotFoundException("can't find hub "+hubId ));
        DeviceEntity deviceEntity = deviceEntityRepository.findById(Long.valueOf(deviceId)).orElseThrow(()->new EntityNotFoundException("can't find device "+ deviceId ));

        deviceEntity.setSlug(request.getSlug());
        deviceEntity.setLabel(request.getLabel());

        deviceEntity = deviceEntityRepository.save(deviceEntity);

        return "redirect:/ui/" + hubEntity.getHubName() + "/devices";
    }

    @GetMapping("/hubs/{hubId}/devices/{deviceId}/delete")
    public String deleteDevice(@PathVariable("hubId")String hubId,@PathVariable("deviceId")String deviceId){

        HubEntity hubEntity = hubEntityRepository.findById(Long.valueOf(hubId)).orElseThrow(()->new EntityNotFoundException("can't find hub "+hubId ));
        deviceEntityRepository.deleteById(Long.valueOf(deviceId));

        return "redirect:/ui/" + hubEntity.getHubName() + "/devices";
    }

    @GetMapping("/{hubName}/devices/{deviceSlug}/commands")
    public String hub_devices_commands(Model model, @PathVariable("hubName") String hubName,@PathVariable("deviceSlug")String deviceSlug){

        List<HubEntity> hubs = hubEntityRepository.findAll();
        model.addAttribute("hubs",hubs);

        HubEntity hubEntity = hubEntityRepository.findByHubNameAndDevices_slug(hubName,deviceSlug).orElseThrow(()->new EntityNotFoundException("can't find device "+deviceSlug + " connected to "+ hubName));

        List<CommandEntity> commandEntities = new ArrayList<>();

        for(DeviceEntity device : hubEntity.getDevices()){
            if(device.getSlug().equals(deviceSlug)) {
                model.addAttribute("device",device);
                commandEntities.addAll(device.getCommands());
                break;
            }
        }

        model.addAttribute("hub",hubEntity);
        model.addAttribute("commands",commandEntities);

        return "commands/list";
    }

    @PostMapping("/{hubName}/devices/{deviceSlug}/commands")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Map<String,String> hub_devices_commands_register(
            @PathVariable("hubName") String hubName,
            @PathVariable("deviceSlug")String deviceSlug,
            @RequestBody List<RegistCommandVo> commandVos){

        List<CommandEntity> newCommandEntities = new ArrayList<>();

        for (RegistCommandVo commandVo : commandVos) {
            IrCodeEntity irCodeEntity = new IrCodeEntity(
                    commandVo.getType(),
                    commandVo.getData(),
                    commandVo.getLength(),
                    commandVo.getKhz());

            CommandEntity commandEntity = new CommandEntity(
                    commandVo.getId(),
                    commandVo.getName(),
                    commandVo.getSlug(),
                    commandVo.getLabel(),
                    irCodeEntity);

            newCommandEntities.add(commandEntity);
        }

        HubEntity hubEntity = hubEntityRepository.findByHubNameAndDevices_slug(hubName,deviceSlug).orElseThrow(()->new EntityNotFoundException("can't find device "+deviceSlug + " connected to "+ hubName));

        DeviceEntity deviceEntity = hubEntity.getDevices().stream()
                .filter(device -> device.getSlug().equals(deviceSlug))
                .findFirst()
                .orElseThrow(()->new EntityNotFoundException("can't find device "+deviceSlug + " connected to "+ hubName));

        commandEntityRepository.deleteAll(deviceEntity.getCommands());

        deviceEntity.setCommands(newCommandEntities);

        deviceEntityRepository.save(deviceEntity);

        Map<String,String> result = new HashMap<>();
        result.put("result","success");
        return result;
    }

    /*
    //TODO 파일을 이용한 등록 기능 추가시 추가
    @GetMapping("/{hubName}/devices/{deviceSlug}/commands/register")
    public String hub_devices_commands_register(Model model, @PathVariable("hubName") String hubName,@PathVariable("deviceSlug")String deviceSlug){
        HubEntity hubEntity = hubEntityRepository.findByHubNameAndDevices_slug(hubName,deviceSlug).orElseThrow(()->new EntityNotFoundException("can't find device "+deviceSlug + " connected to "+ hubName));

        DeviceEntity deviceEntity = hubEntity.getDevices().stream()
                .filter(device -> device.getSlug().equals(deviceSlug))
                .findFirst()
                .orElseThrow(()->new EntityNotFoundException("can't find device "+deviceSlug + " connected to "+ hubName));

        model.addAttribute("hub",hubEntity);
        model.addAttribute("device",deviceEntity);

        return "commands/register";
    }

    @PostMapping("/{hubName}/devices/{deviceSlug}/commands/register")
    public String hub_devices_commands_register(
            @PathVariable("hubName") String hubName,
            @PathVariable("deviceSlug")String deviceSlug){


        return "redirect:/ui/" + hubName + "/devices/" + deviceSlug + "/commands";
    }
     */
}
