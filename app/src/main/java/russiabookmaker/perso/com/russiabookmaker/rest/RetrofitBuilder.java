package russiabookmaker.perso.com.russiabookmaker.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by versusmind on 24/10/2016.
 */

public class RetrofitBuilder {

    //public static String baseUrl = "http://10.0.2.2:80/russiabookmaker/webservices/";
    public static String baseUrl = "http://10.0.2.2:8888/DesktopRussiaBookmaker/webservices/";
    public static Retrofit  setBaseUrl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
