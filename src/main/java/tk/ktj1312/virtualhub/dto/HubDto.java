package tk.ktj1312.virtualhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

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
        private List<ResDeviceDto> devices;
    }

    @Data
    @AllArgsConstructor
    public static class Command{
        private List<ResCommandDto> commands;
    }

    @Data
    @AllArgsConstructor
    public static class ResDeviceDto {
        private String id;
        private String slug;
        private String label;
    }

    @Data
    @AllArgsConstructor
    public static class ResCommandDto {
        private String id;
        private String name;
        private String slug;
        private String label;
    }

}
