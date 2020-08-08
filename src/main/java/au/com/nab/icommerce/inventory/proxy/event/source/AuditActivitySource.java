package au.com.nab.icommerce.inventory.proxy.event.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface AuditActivitySource {
    String AUDIT_OUTPUT = "audit-output";

    @Output(AUDIT_OUTPUT)
    MessageChannel auditActivityOutput();
}
