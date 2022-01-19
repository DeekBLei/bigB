package com.example.project_bigbangk.service;

import com.example.project_bigbangk.model.Asset;
import com.example.project_bigbangk.model.AssetCode_Name;
import com.example.project_bigbangk.model.Orders.Limit_Buy;
import com.example.project_bigbangk.model.Orders.Limit_Sell;
import com.example.project_bigbangk.model.Wallet;
import com.example.project_bigbangk.repository.RootRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderMatchingServiceTest {
    @Resource
    RootRepository rootRepository;

    @Resource
    OrderMatchingService orderMatchingService;

    @BeforeEach
    public void fillDB() {
        //Asset asset, double requestedPrice, double numberOfAssets, LocalDateTime date, Wallet sellerWallet
        Asset asset = rootRepository.getAllAssets().stream()
                .filter(a -> a.getCode().equals(AssetCode_Name.BTC.getAssetCode())).findFirst().orElse(null);
        Wallet wallet1 = rootRepository.findWalletByEmail("0@Yahoo.com");
        Wallet wallet2 = rootRepository.findWalletByEmail("Aalt@T-Mobile.biz");
        Wallet wallet3 = rootRepository.findWalletByEmail("Aartie@Gmail.de");
        Wallet wallet4 = rootRepository.findWalletByEmail("Abdelkarim@Wanadoo.de");
        Wallet wallet5 = rootRepository.findWalletByEmail("Abdelwahed@T-Mobile.be");
        Wallet wallet6 = rootRepository.findWalletByEmail("Abdeslam@T-Mobile.de");
        Wallet wallet7 = rootRepository.findWalletByEmail("Abe@Wanadoo.com");
        Wallet wallet8 = rootRepository.findWalletByEmail("Adriaantje@T-Mobile.nl");
        rootRepository.saveWaitingLimitSellOrder(new Limit_Sell(asset, 5, 2, LocalDateTime.now().minusDays(10), wallet1));
        rootRepository.saveWaitingLimitSellOrder(new Limit_Sell(asset, 4, 2, LocalDateTime.now().minusDays(20), wallet2));
        rootRepository.saveWaitingLimitSellOrder(new Limit_Sell(asset, 3, 2, LocalDateTime.now().minusDays(20), wallet3));
        rootRepository.saveWaitingLimitSellOrder(new Limit_Sell(asset, 5, 2, LocalDateTime.now().minusDays(90), wallet4));
        rootRepository.saveWaitingLimitSellOrder(new Limit_Sell(asset, 3, 2, LocalDateTime.now().minusDays(30), wallet5));
        rootRepository.saveWaitingLimitBuyOrder(new Limit_Buy(asset, 3, 3, LocalDateTime.now().minusDays(30), wallet6));
        System.out.println(rootRepository.getAllLimitBuy());
        //rootRepository.saveWaitingLimitBuyOrder(new Limit_Buy(asset, 4, 2, LocalDateTime.now().minusDays(45), wallet7));
      //  rootRepository.saveWaitingLimitBuyOrder(new Limit_Buy(asset, 5, 2, LocalDateTime.now().minusDays(50), wallet8));
    }

    @Test
    void checkForMatchingOrders() {
        orderMatchingService.checkForMatchingOrders();

    }
}