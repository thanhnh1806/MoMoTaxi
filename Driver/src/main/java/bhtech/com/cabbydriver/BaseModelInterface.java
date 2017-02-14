package bhtech.com.cabbydriver;

import bhtech.com.cabbydriver.object.ErrorObj;

/**
 * Created by duongpv on 4/21/16.
 */
public interface BaseModelInterface {
    void success();

    void failure(ErrorObj errorObj);
}
