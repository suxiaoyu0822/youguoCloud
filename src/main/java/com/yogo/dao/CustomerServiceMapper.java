package com.yogo.dao;

import com.yogo.entity.CustomerService;
import com.yogo.entity.CustomerServiceExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerServiceMapper {
    long countByExample(CustomerServiceExample example);

    int deleteByExample(CustomerServiceExample example);

    int insert(CustomerService record);

    int insertSelective(CustomerService record);

    List<CustomerService> selectByExample(CustomerServiceExample example);

    int updateByExampleSelective(@Param("record") CustomerService record, @Param("example") CustomerServiceExample example);

    int updateByExample(@Param("record") CustomerService record, @Param("example") CustomerServiceExample example);

    int deleteCustomerService(int serviceId);

    List<CustomerService> selectAll();

    /**
     * Create By cjn
     * @return
     */
    int selectAllServerNum();
    List<CustomerService> selectNotDimissionPerson();

    List<CustomerService>selectBySearchName(@Param("name") String name);
    
    int updateDimission(@Param("service_id") int service_id);
    
    CustomerService verify(@Param("employeeId") String employeeId, @Param("password") String password);

    CustomerService selectByEIdAndPwd(@Param("employeeId") String employeeId, @Param("password") String password);
    
    List<CustomerService> selectAllCustom();
}