package com.sh.carexx.uc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sh.carexx.bean.order.CustomerOrderQueryFormBean;
import com.sh.carexx.model.uc.CustomerOrder;

/**
 * 
 * ClassName: 客户预约服务订单 <br/>
 * 
 * @author hetao
 * @since JDK 1.8
 */
public interface CustomerOrderMapper {

	/**
	 * 
	 * insert:(添加方法). <br/>
	 * 
	 * @author hetao
	 * @param customerOrder
	 * @return
	 * @since JDK 1.8
	 */
	int insert(CustomerOrder customerOrder);
	
	/**
	 * 
	 * confirmCompleted:(完成订单). <br/> 
	 * 
	 * @author hetao 
	 * @param customerOrder
	 * @return 
	 * @since JDK 1.8
	 */
	int confirmCompleted(CustomerOrder customerOrder);

	/**
	 * 
	 * selectByUserIdCount:(客户端通过客户id查询服务订单分页总数). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	Integer selectByUserIdCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);

	/**
	 * 
	 * selectByUserId:(客户端通过客户id查询服务订单分页). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> selectByUserId(CustomerOrderQueryFormBean customerOrderQueryFormBean);

	/**
	 * 
	 * selectCustomerOrderCount:(查询客户预约服务订单总数). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	Integer selectCustomerOrderCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);

	/**
	 * 
	 * selectCustomerOrderList:(查询客户预约服务订单分页). <br/>
	 * 
	 * @author hetao
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> selectCustomerOrderList(CustomerOrderQueryFormBean customerOrderQueryFormBean);

	/**
	 * 
	 * selectByWorkTypeIdCount:(通过工种ID统计). <br/>
	 * 
	 * @author zhoulei
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	Integer selectByWorkTypeIdCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);

	/**
	 * 
	 * selectByWorkTypeIdList:(通过工种ID查询). <br/>
	 * 
	 * @author zhoulei
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> selectByWorkTypeIdList(CustomerOrderQueryFormBean customerOrderQueryFormBean);
	
	/**
	 * 
	 * selectStaffScheduleCount:(人员排班查看统计). <br/>
	 * 
	 * @author zhoulei
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	Integer selectStaffScheduleCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);

	/**
	 * 
	 * selectStaffScheduleList:(人员排班查看). <br/>
	 * 
	 * @author zhoulei
	 * @param customerOrderQueryFormBean
	 * @return
	 * @since JDK 1.8
	 */
	List<Map<?, ?>> selectStaffScheduleList(CustomerOrderQueryFormBean customerOrderQueryFormBean);
	
	/**
	 * 
	 * selectByOrderNo:(通过订单编号查询). <br/>
	 * 
	 * @author zhoulei
	 * @param orderNo
	 * @return
	 * @since JDK 1.8
	 */
	CustomerOrder selectByOrderNo(String orderNo);
	
	/**
	 * 
	 * selectOrderByExist:(客户下单查重复). <br/> 
	 * 
	 * @author hetao 
	 * @param customerOrderQueryFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<?,?>> selectOrderExistence(CustomerOrderQueryFormBean customerOrderQueryFormBean);
	
	/**
	 * 
	 * selectInstIncomeCount:(机构收入统计). <br/> 
	 * 
	 * @author zhoulei 
	 * @param customerOrderQueryFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<String, Object>> selectInstIncomeCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);
	
	/**
	 * 
	 * updateStatus:(修改订单状态). <br/> 
	 * 
	 * @author hetao 
	 * @param serviceStatus
	 * @param orderNo
	 * @return 
	 * @since JDK 1.8
	 */
	int updateStatus(@Param("orderNo") String orderNo, @Param("srcStatus") Byte srcStatus, @Param("targetStatus") Byte targetStatus);
	
	/**
	 * 
	 * updateOrderCancel:(取消订单). <br/> 
	 * 
	 * @author hetao 
	 * @param orderNo
	 * @param targetStatus
	 * @return 
	 * @since JDK 1.8
	 */
	int updateOrderCancel(@Param("orderNo") String orderNo, @Param("targetStatus") Byte targetStatus);

	/**
	 * 
	 * updateOrderDelete:(删除订单). <br/> 
	 * 
	 * @author zhoulei 
	 * @param orderNo
	 * @param targetStatus
	 * @return 
	 * @since JDK 1.8
	 */
	int updateOrderDelete(@Param("orderNo") String orderNo, @Param("targetStatus") Byte targetStatus);
	/**
	 * 
	 * selectIncomeCount:(收入统计). <br/> 
	 * 
	 * @author zhoulei 
	 * @param customerOrderQueryFormBean
	 * @return 
	 * @since JDK 1.8
	 */
	List<Map<String, Object>> selectIncomeCount(CustomerOrderQueryFormBean customerOrderQueryFormBean);
	
	/**
	 * 
	 * updateAdjustAmt:(调整订单金额). <br/> 
	 * 
	 * @author hetao 
	 * @param customerOrder
	 * @return 
	 * @since JDK 1.8
	 */
	int update(CustomerOrder customerOrder);
}