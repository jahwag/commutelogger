package com.combitech.commutelogger.backend.domain.entities;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class TransportTest {


    @Test
    void generatePreset() throws IOException {
        LinkedList<Transport> transports = new LinkedList<>();
        for (TransportEnum transportEnum : TransportEnum.values()) {
            transports.add(Transport.builder()
                                    .name(transportEnum.displayName)
                                    .perPersonKm(transportEnum.perPersonKm)
                                    .gramCo2PerKm(transportEnum.gramCo2PerKm)
                                    .shareable(transportEnum.enableRidesharing)
                                    .build());
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(mapper.getSerializationConfig()
                                   .getDefaultVisibilityChecker()
                                   .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                                   .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withCreatorVisibility(JsonAutoDetect.Visibility.ANY));
        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File("src/main/generated/transports.json"), transports);
    }

    /**
     * Sources:
     * https://sl.se/globalassets/rapporter-etc/sl_och_lanet_2018.pdf
     * https://energimyndigheten.a-w2m.se/FolderContents.mvc/Download?ResourceId=2099
     */
    enum TransportEnum {
        WALK("Walk", 0, false, false),

        BICYCLE("Bicycle", 0, false, false),

        MOTORCYCLE_125CC("Motorcycle 125cc", 69, false, false),

        MOTORCYCLE_500C("Motorcycle < 500cc", 84, false, false),

        MOTORCYCLE_500C_PLUS("Motorcycle > 500cc", 110, false, false),

        CAR("Car", 110, false, true),

        ELECTRIC_VEHICLE("Electric vehicle", (int) (13 * 2f), false, true),

        BUS("Bus", 15, true, false),

        TRAIN("Train or subway", 1, true, false),

        OTHER("Other", 0, false, false);

        private String displayName;

        private int gramCo2PerKm;

        private boolean perPersonKm;

        private boolean enableRidesharing;

        TransportEnum(String displayName, int gramCo2PerKm, boolean perPersonKm, boolean enableRidesharing) {
            this.displayName = displayName;
            this.gramCo2PerKm = gramCo2PerKm;
            this.perPersonKm = perPersonKm;
            this.enableRidesharing = enableRidesharing;
        }
    }


}
