package com.zsl.RequestAndResponse;

import com.zsl.ResultMode.AbstractRequest;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/14
 * Time: 15:32
 * Description: No Description
 */
@Data
public class AllCommoditiesRequest extends AbstractRequest {

    @Override
    public void check() throws Exception {
        // nothing to do
    }
}
