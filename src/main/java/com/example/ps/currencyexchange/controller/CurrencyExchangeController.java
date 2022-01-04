package com.example.ps.currencyexchange.controller;

import com.example.ps.currencyexchange.dao.CurrencyExchangeRepository;
import com.example.ps.currencyexchange.domain.CurrencyExchange;
import com.example.ps.currencyexchange.exception.ExchangeUnitNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    private final Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private Environment env;

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to) {

        logger.info("retrieveExchangeValue called with {} & {}", from, to);
        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to)
                .orElseThrow(() -> new ExchangeUnitNotFoundException("Exchange Unit Not Found!"));
        currencyExchange.setEnvironment(env.getProperty("local.server.port"));

        return currencyExchange;
    }

}
