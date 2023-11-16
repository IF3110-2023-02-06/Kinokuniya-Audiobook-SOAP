package kinokuniya.service;


import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
// import javax.jws.HandlerChain;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import io.github.cdimascio.dotenv.Dotenv;
import kinokuniya.enums.Stat;
import kinokuniya.model.DataSubs;
import kinokuniya.repository.SubscriptionRepository;

// @HandlerChain(file = "handler-chain.xml")
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class SubscriptionService {
    private static final SubscriptionRepository SubscriptionRepository = new SubscriptionRepository();

    @WebMethod
    public String createSubscribe(int creator_id, int subscriber_id, String creator_name, String subscriber_name, String api_key) {
        if (!api_key.equals(Dotenv.load().get("APP_KEY"))) {
            return "Not authorized";
        }
        return SubscriptionRepository.createSubscribe(creator_id, subscriber_id, creator_name, subscriber_name);
    }

    @WebMethod
    public String approveSubscribe(int creator_id, int subscriber_id, String api_key) {
        if (!api_key.equals(Dotenv.load().get("REST_KEY"))) {
            return "Not authorized";
        }

        String res = SubscriptionRepository.approveSubscribe(creator_id, subscriber_id);

        if (res.equals("Subscription accepted")) {
            try {
                String SOAP_KEY = Dotenv.load().get("REST_KEY", "1234567890");
                URL url = new URL("http://host.docker.internal:8080/public/subs/update");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("PUT");
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                String data = "creator_id=" + creator_id + "&subscriber_id=" + subscriber_id + "&status=ACCEPTED" +
                "&soap_key=" + SOAP_KEY;
                OutputStreamWriter writer = new OutputStreamWriter(http.getOutputStream());
                writer.write(data);
                writer.flush();
                writer.close();
                System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
                http.disconnect();
            } catch (Exception e) {
                return res;
            }
        }

        return res;
    }

    @WebMethod
    public String rejectSubscribe(int creator_id, int subscriber_id, String api_key) {
        if (!api_key.equals(Dotenv.load().get("REST_KEY"))) {
            return "Not authorized";
        }

        String res =  SubscriptionRepository.rejectSubscribe(creator_id, subscriber_id);

        if (res.equals("Subscription rejected")) {
            try {
                String SOAP_KEY = Dotenv.load().get("REST_KEY", "1234567890");
                URL url = new URL("http://host.docker.internal:8080/public/subs/update");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("PUT");
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                String data = "creator_id=" + creator_id + "&subscriber_id=" + subscriber_id + "&status=REJECTED" +
                "&soap_key=" + SOAP_KEY;
                OutputStreamWriter writer = new OutputStreamWriter(http.getOutputStream());
                writer.write(data);
                writer.flush();
                writer.close();
                System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
                http.disconnect();
            } catch (Exception e) {
                return res;
            }
        }

        return res;
    }

    @WebMethod
    public DataSubs getAllReqSubscribe(String api_key) {
        if (!api_key.equals(Dotenv.load().get("REST_KEY"))) {
            return new DataSubs();
        }
        return SubscriptionRepository.getAllReqSubscribe();
    }

    @WebMethod
    public DataSubs getAllAuthorBySubID(Integer subscriber_id, String api_key) {
        if (!api_key.equals(Dotenv.load().get("APP_KEY"))) {
            return new DataSubs();
        }
        return SubscriptionRepository.getAllAuthorBySubID(subscriber_id);
    }

    @WebMethod
    public Stat checkStatus(int creator_id, int subscriber_id, String api_key) {
        if (!api_key.equals(Dotenv.load().get("REST_KEY")) && !api_key.equals(Dotenv.load().get("APP_KEY"))) {
            return Stat.NODATA;
        }

        return SubscriptionRepository.checkStatus(creator_id, subscriber_id);
    }
}
