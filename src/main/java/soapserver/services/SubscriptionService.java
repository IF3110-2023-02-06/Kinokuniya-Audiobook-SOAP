package soapserver.services;

import java.util.List;
import java.util.ArrayList;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

import soapserver.models.Subscription;

import io.github.cdimascio.dotenv.Dotenv;
import soapserver.enums.Stat;
import soapserver.repositories.SubscriptionRepository;

@WebService
@HandlerChain(file = "handler-chain.xml")
public class SubscriptionService {
    private SubscriptionRepository subscriptionRepo = new SubscriptionRepository();

    @WebMethod
    public String createSubscribe(int creator_id, int subscriber_id, String creator_name, String subscriber_name, String api_key) {
        if (!api_key.equals(Dotenv.load().get("APP_KEY"))) {
            return "Not authorized";
        }
        return subscriptionRepo.createSubscribe(creator_id, subscriber_id, creator_name, subscriber_name);
    }

    @WebMethod
    public String approveSubscribe(int creator_id, int subscriber_id, String api_key) {
        if (!api_key.equals(Dotenv.load().get("REST_KEY"))) {
            return "Not authorized";
        }

        String res = subscriptionRepo.approveSubscribe(creator_id, subscriber_id);

        return res;
    }

    @WebMethod
    public String rejectSubscribe(int creator_id, int subscriber_id, String api_key) {
        if (!api_key.equals(Dotenv.load().get("REST_KEY"))) {
            return "Not authorized";
        }

        String res =  subscriptionRepo.rejectSubscribe(creator_id, subscriber_id);

        return res;
    }

    @WebMethod
    public List<Subscription> getAllReqSubscribe(String api_key) {
        if (!api_key.equals(Dotenv.load().get("REST_KEY"))) {
            return new ArrayList<Subscription>();
        }
        return subscriptionRepo.getAllReqSubscribe();
    }

    @WebMethod
    public List<Subscription> getAllAuthorBySubID(Integer subscriber_id, String api_key) {
        if (!api_key.equals(Dotenv.load().get("APP_KEY"))) {
            return new ArrayList<Subscription>();
        }
        return subscriptionRepo.getAllAuthorBySubID(subscriber_id);
    }

    @WebMethod
    public Stat checkStatus(int creator_id, int subscriber_id, String api_key) {
        if (!api_key.equals(Dotenv.load().get("REST_KEY")) && !api_key.equals(Dotenv.load().get("APP_KEY"))) {
            return Stat.NODATA;
        }

        return subscriptionRepo.checkStatus(creator_id, subscriber_id);
    }
}
