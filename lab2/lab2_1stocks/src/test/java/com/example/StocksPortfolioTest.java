package com.example;

import org.mockito.Mockito;
import org.hamcrest.MatcherAssert;
import org.hamcrest.CoreMatchers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {
	
	@Mock
	private IStockmarketService stockmarketService;

	@InjectMocks
	private StocksPortfolio stocksPortfolio;

	@Test
	public void getTotalValueTest() {
		Stock stock1 = new Stock("IBM", 10);
		Stock stock2 = new Stock("AAPL", 20);

		Mockito.when(stockmarketService.lookUpPrice(stock1.getLabel())).thenReturn(100.0);
		Mockito.when(stockmarketService.lookUpPrice(stock2.getLabel())).thenReturn(50.0);

		stocksPortfolio.addStock(stock1);
		stocksPortfolio.addStock(stock2);

		MatcherAssert.assertThat(stocksPortfolio.getTotalValue(), CoreMatchers.equalTo(2000.0));

		Mockito.verify(stockmarketService, Mockito.times(2)).lookUpPrice(Mockito.anyString());
	}
}
