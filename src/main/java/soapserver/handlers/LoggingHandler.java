package soapserver.handlers;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import io.github.cdimascio.dotenv.Dotenv;
import soapserver.repositories.LoggingRepostitory;

public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {
  private LoggingRepostitory loggingRepostitory = new LoggingRepostitory();

  public Set<QName> getHeaders() {
    return Collections.emptySet();
  }

  public boolean handleMessage(SOAPMessageContext messageContext) {
    try {
      boolean isOutbound = (boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

      // Only intercepts inbound message
      if (!isOutbound) {
        SOAPMessage soapMessage = messageContext.getMessage();
        SOAPBody soapBody = soapMessage.getSOAPBody();
        SOAPHeader soapHeader = soapMessage.getSOAPHeader();

        String method = soapBody.getChildNodes().item(1).getLocalName();

        // API key validation
        String key = soapHeader.getChildNodes().item(0).getTextContent();
        if (!(key.equals(Dotenv.load().get("REST_KEY")))) {
          return false;
        }

        // Get request description
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        soapMessage.writeTo(output);
        String messageString = new String(output.toByteArray());

        String description = messageString.substring(messageString.indexOf("<soap:Body>"));
        description = description.substring(0, description.indexOf("</soap:Envelope>"));

        // Get remote address
        HttpServletRequest request = (HttpServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST);
        String origin = request.getRemoteAddr();

        loggingRepostitory.addNewLog(origin, method, description);
      }

      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  public boolean handleFault(SOAPMessageContext messageContext) {
    return true;
  }

  public void close(MessageContext messageContext) {

  }
}
