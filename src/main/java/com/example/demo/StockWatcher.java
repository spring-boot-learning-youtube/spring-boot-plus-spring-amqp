package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StockWatcher {

	static Logger logger = LoggerFactory.getLogger(StockWatcher.class);

	List<StockMovement> trades = new ArrayList<>();

	@RabbitListener( //
			bindings = @QueueBinding( //
					value = @Queue, //
					exchange = @Exchange(value = "stock-market", type = ExchangeTypes.TOPIC), //
					key = "*"))
	void listenForStockTrades(StockMovement trade) {
		logger.info("Received an update on " + trade.getStockName() + " => NEW PRICE: " + trade.getPrice());
		trades.add(trade); // track all trades for testing
	}

	public List<StockMovement> getTrades() {
		return trades;
	}
}
