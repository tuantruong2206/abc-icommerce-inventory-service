package au.com.nab.icommerce.inventory.filter;

import au.com.nab.icommerce.base.context.RequestHeaderHolderContext;
import au.com.nab.icommerce.base.filter.RequestFilter;
import org.springframework.stereotype.Component;

@Component
public class RequestHeaderFilter extends RequestFilter {

    private static final String BUSINESS_URL_PATTERN = "/inventory/";

    public RequestHeaderFilter(RequestHeaderHolderContext requestHeaderHolderContext) {
        super(requestHeaderHolderContext);
    }

    @Override
    protected Boolean validateUrl(String s) {
        return s.contains(BUSINESS_URL_PATTERN);
    }
}
