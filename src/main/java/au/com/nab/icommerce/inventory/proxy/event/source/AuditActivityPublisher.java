package au.com.nab.icommerce.inventory.proxy.event.source;

import au.com.nab.icommerce.base.context.RequestHeaderHolderContext;
import au.com.nab.icommerce.inventory.dto.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.util.Objects;

@Component
public class AuditActivityPublisher {

    private final Logger log = LoggerFactory.getLogger(AuditActivityPublisher.class);
    private final AuditActivitySource auditActivitySource;
    private final RequestHeaderHolderContext holderContext;

    public AuditActivityPublisher(AuditActivitySource auditActivitySource, RequestHeaderHolderContext holderContext) {
        this.auditActivitySource = auditActivitySource;
        this.holderContext = holderContext;
    }

    public void sendAuditActivity() throws Exception {
        log.info("Send audit log");
        this.auditActivitySource.auditActivityOutput().send(MessageBuilder
                .withPayload(holderContext.getRequestObj())
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }
}
