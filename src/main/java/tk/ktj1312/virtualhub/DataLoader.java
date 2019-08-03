package tk.ktj1312.virtualhub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import tk.ktj1312.virtualhub.entity.CommandEntity;
import tk.ktj1312.virtualhub.entity.DeviceEntity;
import tk.ktj1312.virtualhub.entity.HubEntity;
import tk.ktj1312.virtualhub.entity.IrCodeEntity;
import tk.ktj1312.virtualhub.repository.HubEntityRepository;

import java.util.Arrays;
import java.util.Collections;

@Component
@Slf4j
public class DataLoader implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private HubEntityRepository hubEntityRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event){

        /*
        IrCodeEntity codeOn = new IrCodeEntity("nec","FF827D",32,null,null,null,null);
        CommandEntity commandOn = new CommandEntity("ChannelDown","channel-down", "Channel Down",codeOn);

        IrCodeEntity codeOff = new IrCodeEntity("raw","[2450,600,1300,600,700,550,1300,600,700,550,1300,550,700,550,700,600,1300,600,700,550,700,550,700,550,700]",null,null,null,38,null);
        CommandEntity commandOff = new CommandEntity("ChannelUp","channel-up", "Channel Up",codeOff);

        DeviceEntity deviceEntity = new DeviceEntity("tivo-premiere","TiVo Premiere", Arrays.asList(commandOn,commandOff));
        */

        IrCodeEntity codeOn = new IrCodeEntity("nec","616A817E",32,null,null,null,null);
        CommandEntity commandOn = new CommandEntity("PowerOn","power-on", "Power On",codeOn);

        IrCodeEntity codeOff = new IrCodeEntity("nec","616A817E",32,null,null,null,null);
        CommandEntity commandOff = new CommandEntity("PowerOff","power-off", "Power Off",codeOff);

        DeviceEntity deviceEntity = new DeviceEntity("lg-aircon","Lg Aircon", Arrays.asList(commandOn,commandOff));

        HubEntity hubEntity = new HubEntity("v_hub","192.168.0.170","1234",Collections.singletonList(deviceEntity));
        hubEntity = hubEntityRepository.save(hubEntity);

        log.info("Loaded 1 dummy data " + hubEntity.toString());
    }
}
