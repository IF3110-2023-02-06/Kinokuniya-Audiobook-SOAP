package kinokuniya.middleware;

import com.sun.xml.ws.developer.JAXWSProperties;

import kinokuniya.model.Logging;
import kinokuniya.util.HibernateUtil;

import com.sun.net.httpserver.HttpExchange;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Date;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class LoggerServlet implements SOAPHandler<SOAPMessageContext> {
    private void logToDatabase(SOAPMessageContext smc) throws SOAPException {
        boolean isResponse = (boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        HttpExchange httpExchange = (HttpExchange) smc.get(JAXWSProperties.HTTP_EXCHANGE);

        if (!isResponse) {
            SOAPPart soapPart = smc.getMessage().getSOAPPart();
            SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
            SOAPBody soapBody = soapEnvelope.getBody();

            Node operation = soapBody.getChildNodes().item(1);
            String content = String.format("%s", operation.getLocalName());

            NodeList parameters = operation.getChildNodes();
            for (int i = 1; i < parameters.getLength(); i += 2) {
                content = String.format("%s %s(%s)", content, parameters.item(i).getLocalName(), parameters.item(i).getTextContent());
            }

            Logging logging = new Logging();
            logging.setDescription(content);
            logging.setEndpoint(httpExchange.getRequestURI().getPath());
            logging.setIP(httpExchange.getRemoteAddress().getHostString());
            logging.setRequestedAt(new Date());

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();
            session.save(logging);
            session.getTransaction().commit();
        }
    }

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext smc) {
        try {
            logToDatabase(smc);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean handleFault(SOAPMessageContext smc) {
        try {
            logToDatabase(smc);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void close(MessageContext messageContext) {
        // DO NOTHING
    }
}
