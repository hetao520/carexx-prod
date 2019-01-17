package com.sh.carexx.admin.controller;

import com.sh.carexx.bean.order.CustomerOrderTimeFormBean;
import com.sh.carexx.bean.order.CustomerOrderTimeQueryFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.web.BasicRetVal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/customerordertime")
public class CustomerOrderTimeController extends BaseController {

	@RequestMapping(value = "/by_instId")
    public String queryByInstId() {
        Integer instId = this.getCurrentUser().getInstId();
        return this.ucServiceClient.queryCustomerordertimeByInstId(instId);
    }
	
    @RequestMapping(value = "/get_by_instId/{instId}")
    public String getByInstId(@PathVariable("instId") Integer instId) {
        if(instId == 0 || instId == null){
            instId = this.getCurrentUser().getInstId();
        }
        return this.ucServiceClient.getCustomerordertimeByInstId(instId);
    }

    @RequestMapping(value = "/list")
    public String queryForList(CustomerOrderTimeQueryFormBean customerOrderTimeQueryFormBean){
        return this.ucServiceClient.queryCustomerOrderTimeForList(customerOrderTimeQueryFormBean);
    }

    @RequestMapping(value = "/add")
    public BasicRetVal add(@Valid CustomerOrderTimeFormBean customerOrderTimeFormBean, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
        }
        return this.ucServiceClient.addCustomerordertime(customerOrderTimeFormBean);
    }

    @RequestMapping(value = "/modify")
    public BasicRetVal modify(@Valid CustomerOrderTimeFormBean customerOrderTimeFormBean, BindingResult bindingResult) {
        if (customerOrderTimeFormBean.getId() == null || bindingResult.hasErrors()) {
            return new BasicRetVal(CarexxConstant.RetCode.INVALID_INPUT);
        }
        return this.ucServiceClient.modifyCustomerordertime(customerOrderTimeFormBean);
    }
}
