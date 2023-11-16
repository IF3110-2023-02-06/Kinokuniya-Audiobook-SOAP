package soapserver.services;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import soapserver.models.DataSubs;

import io.github.cdimascio.dotenv.Dotenv;
import soapserver.enums.Stat;
import soapserver.repositories.SubscriptionRepository;

@WebService
@HandlerChain(file = "handler-chain.xml")
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

        return res;
    }

    @WebMethod
    public String rejectSubscribe(int creator_id, int subscriber_id, String api_key) {
        if (!api_key.equals(Dotenv.load().get("REST_KEY"))) {
            return "Not authorized";
        }

        String res =  SubscriptionRepository.rejectSubscribe(creator_id, subscriber_id);

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
