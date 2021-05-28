package gr.xe.rating.service.utils;

import gr.xe.rating.service.models.dto.RequestHelperInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintUtils {

    public static void onController(RequestHelperInfo requestHelperInfo) {
        if (log.isInfoEnabled())
            log.info("[{}] => Controller responses for transaction : [{} - {}].", requestHelperInfo.getTransactionID(), requestHelperInfo.getMethodName(), requestHelperInfo.getRequestURI());
    }

}