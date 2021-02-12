package com.example.demo;

import java.util.Objects;

public class StockMovement {

	private String stockName;
	private float price;

	private StockMovement() {}

	public StockMovement(String stockName, float price) {
		this.stockName = stockName;
		this.price = price;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof StockMovement))
			return false;
		StockMovement that = (StockMovement) o;
		return Objects.equals(stockName, that.stockName) && Objects.equals(price, that.price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(stockName, price);
	}

	@Override
	public String toString() {
		return "StockMovement{" + "stockName='" + stockName + '\'' + ", newPrice=" + price + '}';
	}
}
