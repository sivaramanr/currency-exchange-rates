package com.siva.forex.net;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("api/live")
    Observable<ResponseBody> getRates(@Query("pairs") String pairs);

}
