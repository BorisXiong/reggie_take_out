package com.boxing.reggie.Service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boxing.reggie.Entity.Employee;
import com.boxing.reggie.Service.EmployeeService;
import com.boxing.reggie.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/11/20 16:48
 */

@Service
public class EmployServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
