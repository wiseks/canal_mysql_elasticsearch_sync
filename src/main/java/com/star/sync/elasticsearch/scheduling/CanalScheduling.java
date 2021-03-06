package com.star.sync.elasticsearch.scheduling;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.Message;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.star.sync.elasticsearch.disruptor.event.DisruptorEvent;
import com.star.sync.elasticsearch.disruptor.event.DisruptorEventTranslator;
import com.star.sync.elasticsearch.event.DeleteCanalEvent;
import com.star.sync.elasticsearch.event.InsertCanalEvent;
import com.star.sync.elasticsearch.event.UpdateCanalEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="mailto:wangchao.star@gmail.com">wangchao</a>
 * @version 1.0
 * @since 2017-08-26 22:44:00
 */
@Component
public class CanalScheduling implements Runnable, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(CanalScheduling.class);
    private ApplicationContext applicationContext;

    @Resource
    private CanalConnector canalConnector;
    
    @Autowired
    private Disruptor<DisruptorEvent> disruptor;

    @Scheduled(fixedDelay = 100)
    @Override
    public void run() {
        try {
            int batchSize = 1000;
//            Message message = connector.get(batchSize);
            Message message = canalConnector.getWithoutAck(batchSize);
            long batchId = message.getId();
            logger.debug("scheduled_batchId=" + batchId);
            try {
                List<Entry> entries = message.getEntries();
                if (batchId != -1 && entries.size() > 0) {
                    entries.forEach(entry -> {
                        if (entry.getEntryType() == EntryType.ROWDATA) {
//                            publishCanalEvent(entry);
                        	publishEvent(entry);
                            System.out.println("entry:"+entry);
                        }
                    });
                }
                canalConnector.ack(batchId);
            } catch (Exception e) {
                logger.error("发送监听事件失败！batchId回滚,batchId=" + batchId, e);
                canalConnector.rollback(batchId);
            }
        } catch (Exception e) {
            logger.error("canal_scheduled异常！", e);
        }
    }

    private void publishCanalEvent(Entry entry) {
        EventType eventType = entry.getHeader().getEventType();
        switch (eventType) {
            case INSERT:
                applicationContext.publishEvent(new InsertCanalEvent(entry));
                break;
            case UPDATE:
                applicationContext.publishEvent(new UpdateCanalEvent(entry));
                break;
            case DELETE:
                applicationContext.publishEvent(new DeleteCanalEvent(entry));
                break;
            default:
                break;
        }
    }
    
    private void publishEvent(Entry entry){
    	final RingBuffer<DisruptorEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent(new DisruptorEventTranslator(), entry);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
