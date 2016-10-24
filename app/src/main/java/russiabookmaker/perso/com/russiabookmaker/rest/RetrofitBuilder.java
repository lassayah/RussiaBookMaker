package russiabookmaker.perso.com.russiabookmaker.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by versusmind on 24/10/2016.
 */

public class RetrofitBuilder {

    public static Retrofit  setBaseUrl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8888/DesktopRussiaBookMaker/webservices/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}