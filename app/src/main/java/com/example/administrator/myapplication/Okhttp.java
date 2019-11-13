package com.example.administrator.myapplication;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/12/2.
 */

public class Okhttp {
    public static HttpClient httpClient;
    String appId = Constant.APPID;
    String secret = Constant.SECRET;
    String urlLogin = Constant.APP_AUTH;
    String urldatahistory=Constant.QUERY_DEVICE_HISTORY_DATA;
    String deviceCommands=Constant.POST_ASYN_CMD;
    String deviceId = "aa6a1a7e-c4a7-40e2-a0aa-cb96302b153c";
    String gatewayId="aa6a1a7e-c4a7-40e2-a0aa-cb96302b153c";
    String serviceId="S1";
    String accessToken;
    String startime=null;
    String endtime=null;
    public static int count;
    public static java wendu[]=new java[500];
    public static java shidu[]=new java[500];
    public static java turang[]=new java[500];
    public static java guang[]=new java[500];





    public  void GetaccessToken() throws Exception {

        // 服务器端需要验证的客户端证书
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        // 客户端信任的服务器端证书
        KeyStore trustStore = KeyStore.getInstance("BKS");

        InputStream ksIn = MainApplication.getCustomApplicationContext().getAssets().open(Constant.SELFCERTPATH);
        InputStream tsIn=MainApplication.getCustomApplicationContext().getAssets().open(Constant.TRUSTCAPATH);

        try {
            keyStore.load(ksIn, Constant.SELFCERTPWD.toCharArray());
            trustStore.load(tsIn, Constant.TRUSTCAPWD.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ksIn.close();
            } catch (Exception ignore) {
            }
            try {
                tsIn.close();
            } catch (Exception ignore) {
            }
        }

//        SSLContext sc = SSLContext.getInstance("TLS");
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
        keyManagerFactory.init(keyStore, Constant.SELFCERTPWD.toCharArray());
//        sc.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

        SSLSocketFactory ssl = new SSLSocketFactory(keyStore, Constant.SELFCERTPWD, trustStore);
        ssl.setHostnameVerifier(new AllowAllHostnameVerifier());
//        ssl.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
        httpClient = new DefaultHttpClient();
        if (ssl != null) {
            Scheme sch = new Scheme("https", ssl, 443);   //从这里开始研究
            httpClient.getConnectionManager().getSchemeRegistry().register(sch);
        }
        Map<String, String> param = new HashMap<>();
        param.put("appId", appId);
        param.put("secret", secret);

        List<NameValuePair> nvps = new LinkedList<NameValuePair>();
        Set<Map.Entry<String, String>> paramsSet = param.entrySet();
        for (Map.Entry<String, String> paramEntry : paramsSet) {
            nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry
                    .getValue()));
        }
        HttpPost request = new HttpPost(urlLogin);

        request.setEntity(new UrlEncodedFormEntity(nvps));
        //get accessToken
        HttpResponse response = executeHttpRequest(request);
    }
    private HttpResponse executeHttpRequest(HttpUriRequest request) {
        HttpResponse response = null;
        String result = "ERROR";
        try {
            response = httpClient.execute(request);
        } catch (Exception e) {
            System.out.println("executeHttpRequest failed.");
        } finally {
            if (response.getStatusLine().getStatusCode() == 200)
            {
                try {
                    result= convertStreamToString(response.getEntity().getContent());
                    Log.d("a","result:"+result);
                    //{"accessToken":"13e85324da547a3577feb8f3ac09ce6","tokenType":"bearer","refreshToken":"6629faa00902da8f879d131d821a6","expiresIn":3600,"scope":"default"}

                    parseJSONWithGSON(result);
                    //Log.d("a","accessToken:"+app.getAccessToken());
                    //accessToken=app.getAccessToken();


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            else
            {
                try {
                    Log.e(TAG , "CommonPostWithJson | response error | "
                            + response.getStatusLine().getStatusCode()
                            + " error :"
                            + EntityUtils.toString(response.getEntity()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            try {
//                System.out.println("aaa  ==  response -> " + response);
//                HttpEntity entity = response.getEntity();
//
//            } catch (Exception e) {
//                System.out.println("IOException: " + e.getMessage());
//            }
        }

        return response;
    }

    //get数据和时间
    public void GetDataandTime() {
        try {
            // 服务器端需要验证的客户端证书
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            // 客户端信任的服务器端证书
            KeyStore trustStore = KeyStore.getInstance("BKS");
            InputStream ksIn = MainApplication.getCustomApplicationContext().getAssets().open(Constant.SELFCERTPATH);
            InputStream tsIn=MainApplication.getCustomApplicationContext().getAssets().open(Constant.TRUSTCAPATH);

            try {
                keyStore.load(ksIn, Constant.SELFCERTPWD.toCharArray());
                trustStore.load(tsIn, Constant.TRUSTCAPWD.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    ksIn.close();
                } catch (Exception ignore) {
                }
                try {
                    tsIn.close();
                } catch (Exception ignore) {
                }
            }

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            keyManagerFactory.init(keyStore, Constant.SELFCERTPWD.toCharArray());


            SSLSocketFactory ssl = new SSLSocketFactory(keyStore, Constant.SELFCERTPWD, trustStore);
            ssl.setHostnameVerifier(new AllowAllHostnameVerifier());




            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    //不校验服务器端证书域名
                    return true;
                }
            };

            OkHttpClient mOkHttpClient = new OkHttpClient().newBuilder().hostnameVerifier(hostnameVerifier)
                    .sslSocketFactory(sc.getSocketFactory()).build();
//            RequestBody requestBodyPost = new FormBody.Builder()
//                    .add("appId", Constant.APPID)
//                    .add("secret", Constant.SECRET)
//                    .add("refreshToken","123")
//                    .build();
            Request request = null;


            request = new Request.Builder()
                    .url(urldatahistory + "?deviceId=" + deviceId +
                            "&gatewayId=" + gatewayId +
                            "&serviceId=Humidity"  )//2018/11/11 16:10:10  和实际相差8个小时  20181111T150000Z  2018/11/11 23:00:00 ? 获取11/11那天的数据 20181111T081000Z
                    .addHeader("app_key", appId)
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("Content-Type", "application/json")
                    .build();


            Call call = mOkHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String str = response.body().string();
                    parseJSONWithGSON2(str);
                    Log.d("sss","str:"+str);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //post 命令
    public void post(String ServiceId,String Method,int value) {
        try {
            // 服务器端需要验证的客户端证书
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            // 客户端信任的服务器端证书
            KeyStore trustStore = KeyStore.getInstance("BKS");

            InputStream ksIn = MainApplication.getCustomApplicationContext().getAssets().open(Constant.SELFCERTPATH);
            InputStream tsIn = MainApplication.getCustomApplicationContext().getAssets().open(Constant.TRUSTCAPATH);
            try {
                keyStore.load(ksIn, Constant.SELFCERTPWD.toCharArray());
                trustStore.load(tsIn, Constant.TRUSTCAPWD.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    ksIn.close();
                } catch (Exception ignore) {
                }
                try {
                    tsIn.close();
                } catch (Exception ignore) {
                }
            }

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            keyManagerFactory.init(keyStore, Constant.SELFCERTPWD.toCharArray());


            SSLSocketFactory ssl = new SSLSocketFactory(keyStore, Constant.SELFCERTPWD, trustStore);
            ssl.setHostnameVerifier(new AllowAllHostnameVerifier());




            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    //不校验服务器端证书域名
                    return true;
                }
            };

            OkHttpClient mOkHttpClient = new OkHttpClient().newBuilder().hostnameVerifier(hostnameVerifier)
                    .sslSocketFactory(sc.getSocketFactory()).build();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JsonBean jsonBean=new JsonBean();
            Command command=new Command();
            Paras paras=new Paras();
            jsonBean.setDeviceId(deviceId);
            command.setServiceId(ServiceId);//"JX"
            command.setMethod(Method);//"JXON"
            paras.setvalue(value);//0
            jsonBean.setCommand(command);
            command.setParas(paras);
            Gson gson = new Gson();
            String json = gson.toJson(jsonBean);
            Log.d("sss",json);
            RequestBody requestBody = RequestBody.create(JSON, json);

        /*    RequestBody requestBodyPost = new FormBody.Builder()
                    .add(requestBody)
                    .build();
        */
            Request request = null;

            request = new Request.Builder()
                    .url(deviceCommands + "?appId="+appId)//2018/11/11 16:10:10  和实际相差8个小时  20181111T150000Z  2018/11/11 23:00:00 ? 获取11/11那天的数据 20181111T081000Z
                    .addHeader("app_key", appId)
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("Content-Type", "application/json")
                    .post(requestBody)
                    .build();

            Call call = mOkHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {


                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String str = response.body().string();
                    Log.d("a",str);
                    //parseJSONWithGSON2(str);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //解析accessToken
    private void parseJSONWithGSON(String jsonData) {
        try
        {
            //app = new Gson().fromJson(jsonData, JsonRootBean.class);
            JSONObject jsonObject = new JSONObject(jsonData);
            accessToken = jsonObject.getString("accessToken");
            accessToken = accessToken.trim();
            Log.d("sss", "accessToken:"+accessToken);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //解析数据和时间
    private void parseJSONWithGSON2(String jsonData) {
        try
        {
            //JsonRootBean2 app2 = new Gson().fromJson(jsonData, JsonRootBean2.class);
      /*      List<DeviceDataHistoryDTOs> deviceDataHistoryDTOs = app2.getDeviceDataHistoryDTOs();
            for(int i=0;i<deviceDataHistoryDTOs.size();i++) {
                int batteryLevel = deviceDataHistoryDTOs.get(i).getData().getBatteryLevel();
                Log.d("a", "" + batteryLevel);
                String time = app2.getDeviceDataHistoryDTOs().get(i).getTimestamp();
                Log.d("a", time);
            }
*/
            JSONObject jsonObject = new JSONObject(jsonData);
            String  json=jsonObject.getString("deviceDataHistoryDTOs");
            JSONArray jsonArray = new JSONArray(json);
            int count1= jsonObject.getInt("totalCount");
            count=jsonArray.length();
            Log.d("sss","count1:"+count1);
            Log.d("sss","count:"+count);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String data= jsonObject1.getString("data");//{"S1Cur":92}
                String data2 = data.substring(data.indexOf(":") + 1, data.indexOf("}"));//92
                //Log.d("a",data);
                Log.d("sss","data2:"+data2);

                String time=jsonObject1.getString("timestamp");//20181111T031758Z
                String time2 = time.substring(time.indexOf("T") + 1, time.indexOf("Z"));//031758
                String time3=String.valueOf(Integer.parseInt(time2.substring(0, 2))+8)+time2.substring(2,6);//111758
                StringBuilder  sb = new StringBuilder (time3);
                sb.insert(4, ":");
                sb.insert(2, ":");
                String time4 = sb.toString();//11:17:58
                //Log.d("a",time);
                //Log.d("a",time);
                Log.d("sss","time4:"+time4);
                wendu[i] = new java();
                wendu[i].setData(data2);
                wendu[i].setTime(time4);


            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String convertStreamToString(InputStream is) {
        /*
          * To convert the InputStream to String we use the BufferedReader.readLine()
          * method. We iterate until the BufferedReader return null which means
          * there's no more data to read. Each line will appended to a StringBuilder
          * and returned as String.
          */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}
