package com.example;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
	
	private List<Stock> stocks = new ArrayList<Stock>();
	private IStockmarketService stockmarketService;

	public StocksPortfolio(IStockmarketService stockmarketService) {
		this.stockmarketService = stockmarketService;
	}

	public void addStock(Stock stock) {
		stocks.add(stock);
	}

	public double getTotalValue() {
		double total = 0;
		for (Stock stock : stocks) {
			total += stockmarketService.lookUpPrice(stock.getLabel()) * stock.getQuantity();
		}
		return total;
	}
}
