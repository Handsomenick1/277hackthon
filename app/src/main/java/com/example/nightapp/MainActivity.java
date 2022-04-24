package com.example.nightapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.example.nightapp.convertor.JsonMap;
import com.example.nightapp.database.DatabaseHelper;
import com.example.nightapp.model.Macroeconomic;
import com.example.nightapp.model.Trade;
import com.example.nightapp.netconnection.NetClinet;


import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String TRADE_URL = "https://tzstkiqn45.execute-api.us-east-1.amazonaws.com/prod/finances?country=India&indicator=GNI%20%28current%20US%24%29";
    String MAC_URL = "https://tzstkiqn45.execute-api.us-east-1.amazonaws.com/prod/gdp?country=USA&indicator=gdp";
    String AGR_URL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        DatabaseHelper helper = new DatabaseHelper(this);

        NetClinet trade_netClinet = new NetClinet();
        String tradeResponse = trade_netClinet.sendGet(TRADE_URL);
        Log.i("***tradeResponse--->", tradeResponse);
        Map<String, Object> trademap = JsonMap.getMap(tradeResponse);

        NetClinet mac_netClinet = new NetClinet();
        String macResponse = mac_netClinet.sendGet(MAC_URL);
        Log.i("***macResponse--->", macResponse);
        Map<String, Object> macmap = JsonMap.getMap(macResponse);


        Trade trade = new Trade();
        trade.setUrl(TRADE_URL);
        trade.setDatapoint(trademap.get("data").toString());
        helper.insertTrade(trade);
        Log.i("*** tread_db--->", helper.readTreade(trade.getUrl()).toString());

        Macroeconomic macroeconomic = new Macroeconomic();
        macroeconomic.setUrl(MAC_URL);
        macroeconomic.setDatapoint(macmap.get("data").toString());
        helper.insertMacroeconomic(macroeconomic);
        Log.i("**macroeconomic_db--->", helper.readMacroeconomic(macroeconomic.getUrl()).toString());
        Log.i("***lllist--->", helper.convertTreadList(trade).toString());

    }
}