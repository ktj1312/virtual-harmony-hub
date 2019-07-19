package tk.ktj1312.virtualhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tk.ktj1312.virtualhub.entity.CommandEntity;
import tk.ktj1312.virtualhub.entity.DeviceEntity;

import java.util.List;

public class HubDto {

    @Data
    @AllArgsConstructor
    public static class Hub{
        private List<String> hubs;
    }

    @Data
    @AllArgsConstructor
    public static class Device{
        private List<DeviceEntity> devices;
    }

    @Data
    @AllArgsConstructor
    public static class Command{
        private List<CommandEntity> commands;
    }
}
