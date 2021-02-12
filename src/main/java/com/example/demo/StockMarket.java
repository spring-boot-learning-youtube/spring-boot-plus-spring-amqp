package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class StockMarket {

	static List<String> STOCK_SYMBOLS = Arrays.asList("ATT", "SBUX", "ZOOM");//List.of("ATT", "SBUX", "ZOOM");
	static Random RAND = new Random();

	AmqpTemplate template;
	Map<String, StockMovement> lastTrade;

	public StockMarket(AmqpTemplate template) {
		this.template = template;
		this.lastTrade = new HashMap<>();
		STOCK_SYMBOLS.forEach(symbol -> {
			StockMovement initialTrade = new StockMovement(symbol, RAND.nextFloat() * 100.0F);
			this.lastTrade.put(symbol, initialTrade);
		});
	}

	static String randomStockSymbol() {
		return STOCK_SYMBOLS.get(RAND.nextInt(STOCK_SYMBOLS.size()));
	}

	static float newPrice(float oldPrice) {
		return oldPrice * (RAND.nextFloat() + 0.5F);
	}

	@Scheduled(fixedRate = 500L) // ms
	void marketMovement() {
		String nextStockSymbol = randomStockSymbol();
		StockMovement lastTrade = this.lastTrade.get(nextStockSymbol);
		float newPrice = newPrice(lastTrade.getPrice());
		lastTrade.setPrice(newPrice);
		template.convertAndSend("stock-market", lastTrade.getStockName(), lastTrade);
	}
}
