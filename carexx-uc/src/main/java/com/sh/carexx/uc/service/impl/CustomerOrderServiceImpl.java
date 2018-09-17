package com.sh.carexx.uc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.carexx.bean.order.CustomerOrderQueryFormBean;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.enums.pay.PayMethod;
import com.sh.carexx.common.exception.BizException;
import com.sh.carexx.common.util.ValidUtils;
import com.sh.carexx.model.uc.CustomerOrder;
import com.sh.carexx.uc.dao.CustomerOrderMapper;
import com.sh.carexx.uc.service.CustomerOrderService;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

	@Autowired
	private CustomerOrderMapper customerOrderMapper;

	@Override
	public void save(CustomerOrder customerOrder) throws BizException {
		int rows = 0;
		try {
			rows = this.customerOrderMapper.insert(customerOrder);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public void confirmCompleted(CustomerOrder customerOrder) throws BizException {
		int rows = 0;
		try {
			rows = this.customerOrderMapper.confirmCompleted(customerOrder);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public Integer getByUserIdCount(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		return this.customerOrderMapper.selectByUserIdCount(customerOrderQueryFormBean);
	}

	@Override
	public List<Map<?, ?>> getByUserId(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		return this.customerOrderMapper.selectByUserId(customerOrderQueryFormBean);
	}

	@Override
	public Integer getCustomerOrderCount(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		if (ValidUtils.isDate(customerOrderQueryFormBean.getServiceStartTime())) {
			customerOrderQueryFormBean.setServiceStartTime(
					customerOrderQueryFormBean.getServiceStartTime() + CarexxConstant.Datetime.DAY_BEGIN_SUFFIX);
		}
		if (ValidUtils.isDate(customerOrderQueryFormBean.getServiceEndTime())) {
			customerOrderQueryFormBean.setServiceEndTime(
					customerOrderQueryFormBean.getServiceEndTime() + CarexxConstant.Datetime.DAY_END_SUFFIX);
		}
		return this.customerOrderMapper.selectCustomerOrderCount(customerOrderQueryFormBean);
	}

	@Override
	public List<Map<?, ?>> queryCustomerOrderList(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		if (ValidUtils.isDate(customerOrderQueryFormBean.getServiceStartTime())) {
			customerOrderQueryFormBean.setServiceStartTime(
					customerOrderQueryFormBean.getServiceStartTime() + CarexxConstant.Datetime.DAY_BEGIN_SUFFIX);
		}
		if (ValidUtils.isDate(customerOrderQueryFormBean.getServiceEndTime())) {
			customerOrderQueryFormBean.setServiceEndTime(
					customerOrderQueryFormBean.getServiceEndTime() + CarexxConstant.Datetime.DAY_END_SUFFIX);
		}
		return this.customerOrderMapper.selectCustomerOrderList(customerOrderQueryFormBean);
	}

	@Override
	public CustomerOrder getByOrderNo(String orderNo) {
		return this.customerOrderMapper.selectByOrderNo(orderNo);
	}

	@Override
	public List<Map<?, ?>> queryOrderExistence(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		return this.customerOrderMapper.selectOrderExistence(customerOrderQueryFormBean);
	}

	@Override
	public void updateStatus(String orderNo, Byte srcStatus, Byte targetStatus) throws BizException {
		int rows = 0;
		try {
			rows = this.customerOrderMapper.updateStatus(orderNo, srcStatus, targetStatus);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

	@Override
	public void updateOrderCancel(String orderNo, Byte targetStatus) throws BizException {
		int rows = 0;
		try {
			rows = this.customerOrderMapper.updateOrderCancel(orderNo, targetStatus);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}

	}

	@Override
	public List<Map<String, Object>> queryIncomeCount(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		List<Map<String, Object>> incomeCount = this.customerOrderMapper.selectIncomeCount(customerOrderQueryFormBean);
		for(Map<String, Object> map : incomeCount){
			BigDecimal orderAmt = new BigDecimal(String.valueOf(map.get("orderAmt")));
			BigDecimal orderAdjustAmt = new BigDecimal(String.valueOf(map.get("orderAdjustAmt")));
			Integer payType = Integer.parseInt(String.valueOf(map.get("payType")));
			if(payType < 3){
				BigDecimal pounDage = ((orderAmt.add(orderAdjustAmt)).multiply(new BigDecimal(0.006))).setScale(2,BigDecimal.ROUND_HALF_UP);
				map.put("pounDage", pounDage);
			}else{
				map.put("pounDage", 0.0);
			}
		}
		return incomeCount;
	}
	        
	@Override
	public List<Map<String, Object>> queryInstIncomeCount(CustomerOrderQueryFormBean customerOrderQueryFormBean) {
		List<Map<String, Object>> inputInstIncomeCountList = this.customerOrderMapper.selectInstIncomeCount(customerOrderQueryFormBean);
		List<Map<String, Object>> outputInstIncomeCountList = new ArrayList<Map<String, Object>>();
		boolean bool = false;
		
		for(Map<String, Object> inputInstIncomeCountMap : inputInstIncomeCountList) {
			int index = 0;
			if(outputInstIncomeCountList.size() != 0 || outputInstIncomeCountList != null) {
				for(Map<String, Object> outputInstIncomeCountMap : outputInstIncomeCountList) {
					if(Integer.parseInt(String.valueOf(outputInstIncomeCountMap.get("instId"))) == Integer.parseInt(String.valueOf(inputInstIncomeCountMap.get("instId")))) {
						BigDecimal inputOrderAmt = new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("orderAmt"))).add(new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("adjustAmt"))));
						BigDecimal outputOrderAmt = new BigDecimal(String.valueOf(outputInstIncomeCountMap.get("orderAmt"))).add(inputOrderAmt);
						
						BigDecimal inputAdjustAmt = new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("adjustAmt")));
						BigDecimal outputAdjustAmt = new BigDecimal(String.valueOf(outputInstIncomeCountMap.get("adjustAmt"))).add(inputAdjustAmt);
						
						BigDecimal inputStaffSettleAmt = new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("staffSettleAmt")));
						BigDecimal outputStaffSettleAmt = new BigDecimal(String.valueOf(outputInstIncomeCountMap.get("staffSettleAmt"))).add(inputStaffSettleAmt);
						
						BigDecimal inputInstSettleAmt = new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("instSettleAmt"))).add(new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("adjustAmt"))));
						BigDecimal outputInstSettleAmt = new BigDecimal(String.valueOf(outputInstIncomeCountMap.get("instSettleAmt"))).add(inputInstSettleAmt);
						
						Byte payType = Byte.valueOf(String.valueOf(inputInstIncomeCountMap.get("payType")));
						
						BigDecimal outputPounDage = new BigDecimal(String.valueOf(outputInstIncomeCountMap.get("pounDage")));
						if(payType == PayMethod.ONLINE_PAY.getValue() || payType == PayMethod.SCAN_PAY.getValue()) {
							BigDecimal inputPounDage = inputOrderAmt.multiply(new BigDecimal(0.006)).setScale(2,BigDecimal.ROUND_HALF_UP);
							outputPounDage = outputPounDage.add(inputPounDage);
						}
						
						outputInstIncomeCountMap.put("orderAmt",outputOrderAmt);
						outputInstIncomeCountMap.put("adjustAmt",outputAdjustAmt);
						outputInstIncomeCountMap.put("staffSettleAmt",outputStaffSettleAmt);
						outputInstIncomeCountMap.put("instSettleAmt",outputInstSettleAmt);
						outputInstIncomeCountMap.put("pounDage",outputPounDage);
						outputInstIncomeCountList.set(index, outputInstIncomeCountMap);
						
						bool = true;
						break;
					}
					index++;
				}
			}
			
			if(bool == true) {
				bool = false;
				continue;
			}
			
			Map<String, Object> outputInstIncomeCountMap = new HashMap<String, Object>();
			
			BigDecimal outputOrderAmt = new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("orderAmt"))).add(new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("adjustAmt"))));
			BigDecimal outputInstSettleAmt = new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("instSettleAmt"))).add(new BigDecimal(String.valueOf(inputInstIncomeCountMap.get("adjustAmt"))));
			
			Byte payType = Byte.valueOf(String.valueOf(inputInstIncomeCountMap.get("payType")));
			BigDecimal outputPounDage = new BigDecimal(0.0);
			if(payType == PayMethod.ONLINE_PAY.getValue() || payType == PayMethod.SCAN_PAY.getValue()) {
				BigDecimal inputPounDage = outputOrderAmt.multiply(new BigDecimal(0.006)).setScale(2,BigDecimal.ROUND_HALF_UP);
				outputPounDage = outputPounDage.add(inputPounDage);
			}
			
			outputInstIncomeCountMap.put("instId", inputInstIncomeCountMap.get("instId"));
			outputInstIncomeCountMap.put("instName", inputInstIncomeCountMap.get("instName"));
			outputInstIncomeCountMap.put("orderAmt", outputOrderAmt);
			outputInstIncomeCountMap.put("adjustAmt", inputInstIncomeCountMap.get("adjustAmt"));
			outputInstIncomeCountMap.put("staffSettleAmt", inputInstIncomeCountMap.get("staffSettleAmt"));
			outputInstIncomeCountMap.put("instSettleAmt", outputInstSettleAmt);
			outputInstIncomeCountMap.put("pounDage", outputPounDage);
			outputInstIncomeCountList.add(outputInstIncomeCountMap);
		}
		return outputInstIncomeCountList;
	}

	@Override
	public void update(CustomerOrder customerOrder) throws BizException{
		int rows = 0;
		try {
			rows = this.customerOrderMapper.update(customerOrder);
		} catch (Exception e) {
			throw new BizException(ErrorCode.DB_ERROR, e);
		}
		if (rows != 1) {
			throw new BizException(ErrorCode.DB_ERROR);
		}
	}

}
