package com.star.sync.elasticsearch.disruptor.event;

import com.lmax.disruptor.EventFactory;

public class DisruptorEventFactory implements EventFactory<DisruptorEvent> {

	@Override
	public DisruptorEvent newInstance() {
		return new DisruptorEvent();
	}

}
