package tk.ktj1312.virtualhub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import tk.ktj1312.virtualhub.entity.CommandEntity;
import tk.ktj1312.virtualhub.entity.DeviceEntity;
import tk.ktj1312.virtualhub.entity.HubEntity;
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

        CommandEntity commandOn = new CommandEntity("1","ChannelDown","channel-down", "Channel Down");
        DeviceEntity deviceEntity = new DeviceEntity("1","tivo-premiere","TiVo Premiere", Arrays.asList(commandOn));
        HubEntity hubEntity = new HubEntity("1","v_hub","192.168.0.21",Collections.singletonList(deviceEntity));
        hubEntity = hubEntityRepository.save(hubEntity);

        log.info("Loaded 1 dummy data " + hubEntity.toString());
    }
}
