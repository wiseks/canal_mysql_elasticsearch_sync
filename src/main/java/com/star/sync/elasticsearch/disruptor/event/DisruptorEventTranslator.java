package com.star.sync.elasticsearch.disruptor.event;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.lmax.disruptor.EventTranslatorOneArg;

public class DisruptorEventTranslator implements EventTranslatorOneArg<DisruptorEvent,Entry> {

	@Override
	public void translateTo(DisruptorEvent event, long sequence, Entry entry) {
		event.setEntry(entry);
	}

}
