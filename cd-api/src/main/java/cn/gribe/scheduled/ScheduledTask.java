package cn.gribe.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 計劃任務
 * Created by Zhugw on 2019/1/8 0008.
 */
@Component
public class ScheduledTask {

    public static Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Scheduled(cron = "0 0/2 * * * ?")
    public void pushDataScheduled(){
        log.info("start push data scheduled!");

        log.info("end push data scheduled!");
    }


}
