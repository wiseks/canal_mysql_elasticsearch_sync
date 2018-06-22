package com.star.sync.elasticsearch.disruptor.event;

import org.springframework.stereotype.Component;

import com.lmax.disruptor.EventHandler;

@Component
public class DisruptorEventHandler implements EventHandler<DisruptorEvent>{

	@Override
	public void onEvent(DisruptorEvent event, long sequence, boolean endOfBatch) throws Exception {
		System.out.println(">>>>>>>>>>>>>>>>>>"+event);
	}

}
