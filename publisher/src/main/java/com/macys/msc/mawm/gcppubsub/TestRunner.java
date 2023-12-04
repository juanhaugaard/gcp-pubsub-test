package com.macys.msc.mawm.gcppubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.Timestamp;
import com.macys.msc.mawm.itemintegration.dto.ItemCreateDTO;
import com.macys.msc.mawm.itemintegration.service.UpcToItemMapper;
import com.macys.pag.client.core.generated.types.Upc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TestRunner implements CommandLineRunner {
    private final TestPublisher testPublisher;
    private final UpcToItemMapper upcToItemMapper;
    private final ObjectMapper objectMapper;

    private final int testCase = 2;

    private final int numIterations = 1;

    public TestRunner(TestPublisher testPublisher, UpcToItemMapper upcToItemMapper, ObjectMapper objectMapper) {
        this.testPublisher = testPublisher;
        this.upcToItemMapper = upcToItemMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) {
        log.info("Starting test runner");
        for (int i = 1; i <= numIterations; i++) {
            sleep(100);
            switch (testCase) {
                case 1:
                    publishTestMessage();
                    break;
                case 2:
                    publishPagMessages();
                    break;
                default:
                    log.error("UnImplemented test case: {}", testCase);
            }
        }
        sleep(1000);
        log.info("Exiting application");
    }

    private void publishTestMessage() {
        String message = "This is a test message";
        Map<String, String> attributes = makeAttributes();
        testPublisher.publishMessage(message, attributes);
    }

    private void publishPagMessages() {
        try {
            List<Upc> pagResponse = makePagResponse();
            pagResponse.forEach(this::publishPagMessage);
        } catch (Exception e) {
            log.error("PAG response exception {}", e.getMessage(), e);
        }
    }

    private void publishPagMessage(Upc upc) {
        try {
            ItemCreateDTO itemCreateDTO = upcToItemMapper.pagUpcToItemCreateDTO(upc);
            String messageJson = objectMapper.writeValueAsString(itemCreateDTO);
            Map<String, String> attributes = makeAttributes();
            testPublisher.publishMessage(messageJson, attributes);
        } catch (JsonProcessingException e) {
            log.error("Publication exception {}", e.getMessage(), e);
        }
    }

    private void sleep(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            log.error("Sleep interrupted, message: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private Map<String, String> makeAttributes() {
        Date date = new Date();
        Timestamp timestamp = Timestamp.of(date);
        return Map.of("timestamp", timestamp.toString(),
                "Organization","MacysDEV",
                "User", "macysdev-adminuser");
    }

    private List<Upc> makePagResponse() throws JsonProcessingException {
        TypeReference<List<Upc>> typeReference = new TypeReference<>() { };
        return objectMapper.readValue(PAG_RESPONSE_JSON, typeReference);
    }

    static final String PAG_RESPONSE_JSON =
            """
                [
                  {
                      "upcNumber": 3605975098129,
                      "divisionId": 12,
                      "upcName": "Lancôme Dual Finish Multi-Tasking Powder Foundation Oil-free Face Powder ",
                      "upcModelNumber": "2228",
                      "upcModelDescription": "DUAL FINISH VERSATIL",
                      "variation": {
                          "colorway": {
                              "primaryImage": {
                                  "imageName": "2470080.fpx"
                              },
                              "additionalImages": [
                                  {
                                      "imageName": "15137174.fpx"
                                  }
                              ],
                              "swatchImage": {
                                  "imageName": "1759904.fpx"
                              }
                          },
                          "legacyAttributes": {
                              "colorCode": 29,
                              "sizeCode": 0,
                              "colorDesc": "MEDIUM BRO",
                              "sizeDesc": "NO S"
                          },
                          "nrfAttributes": {
                              "colorCode": 211,
                              "colorDescription": "MEDIUM BRO",
                              "sizeCode": 0,
                              "sizeDescription": "NO SIZE"
                          }
                      },
                      "classification": {
                          "classId": 15,
                          "subClassId": 6,
                          "masterStyleDivisionNumber": 37,
                          "markStyle": 2236
                      },
                      "marketplaceFlag": false
                  }
               ]
            """;
}
