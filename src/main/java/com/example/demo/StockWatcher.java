package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StockWatcher {

	static Logger logger = LoggerFactory.getLogger(StockWatcher.class);

	List<StockMovement> trades = new ArrayList<>();

	@RabbitListener( //
			bindings = @QueueBinding( //
					value = @Queue, //
					exchange = @Exchange("stock-market"), //
					key = "SBUX"))
	void listenForStockTrades(StockMovement trade) {
		logger.info("Received an update on " + trade.getStockName() + " => NEW PRICE: " + trade.getPrice());
		trades.add(trade); // track all trades for testing
	}

	public List<StockMovement> getTrades() {
		return trades;
	}
}
