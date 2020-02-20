package thezero.pkd.ams.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientApi {
    private static String BASE_URL="http://10.0.2.2:3001";
    private static ClientApi apiInstance;
    private Retrofit retrofit;

    private ClientApi(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public static ClientApi getInstance(){
        if (apiInstance==null){
            apiInstance=new ClientApi();
        }
        return apiInstance;
    }
    public RetrofitRoutesInterface getApi(){
        return retrofit.create(RetrofitRoutesInterface.class);
    }
}
