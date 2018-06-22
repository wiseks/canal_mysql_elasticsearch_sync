package com.star.sync.elasticsearch.disruptor.event;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;

public class DisruptorEvent {

	private Entry entry;

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}
	
	
}
