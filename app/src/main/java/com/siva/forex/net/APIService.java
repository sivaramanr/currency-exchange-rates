package com.siva.forex.net;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("api/live")
    Observable<ExchangeRates> getRates(@Query("pairs") String pairs);

}
