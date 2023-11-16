package com.boxing.reggie.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boxing.reggie.Entity.Employee;
import com.boxing.reggie.Service.EmployeeService;
import com.boxing.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/11/20 16:50
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     *
     * @param request
     * @param employee
     * @return
     */
    @RequestMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

//        1.将页面提交的用户名进行md5加密
        String password = employee.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());

//        2.根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> QueryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(QueryWrapper);

//        3.如果没有查询到则返回登录失败结果
        if(emp==null){
            return R.error("登录失败");
        }

        //4.密码比对，如果不一致则返回登录失败
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }

        //5.查看员工状态，如果已禁用，则返回员工已禁用
        if(emp.getStatus()==0){
            return  R.error("账号已禁用");
        }

//        6用户登陆成功，将员工id存入session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());


        return R.success(emp);

    }

    /**
     * 员工退出
     * @param requet
     * @return
     */
    @PostMapping("/logout")
    public  R<String> logout(HttpServletRequest requet ){
        requet.getSession().removeAttribute("employee");

        return R.success("退出成功");

    }

    @PostMapping
    public R<String> save(HttpServletRequest request ,@RequestBody Employee employee){
        //创建初始密码并用Md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        //获取当前时间
//        employee.setCreateTime(LocalDateTime.now());
//
//        //获取更新时间
//        employee.setUpdateTime(LocalDateTime.now());
//
//        Long empid = (Long) request.getSession().getAttribute("employee");
//
//        employee.setCreateUser(empid);
//
//        employee.setUpdateUser(empid);

        employeeService.save(employee);

        return R.success("保存成功");
    }

    /**
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping(value = "/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page={}，pagesize={},name={}",page,pageSize,name);

        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> lambdaQueryWrapper=new LambdaQueryWrapper();

        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);

        //
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询

        employeeService.page(pageInfo, lambdaQueryWrapper);

        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(@RequestBody Employee employee,HttpServletRequest request){
        log.info(employee.toString());
        long id = Thread.currentThread().getId();
        log.info("线程id为{}",id);
        //获取id并强转为long
        long empid = (long)request.getSession().getAttribute("employee");
//        获取更新时间
        employee.setUpdateTime(LocalDateTime.now());
//        获取更新用户
        employee.setUpdateUser(empid);
//        执行更新操作
        employeeService.updateById(employee);
        return  R.success("员工信息更新成功");
    }
//http://localhost:8080/backend/page/member/add.html?id=1596923637104308226
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable long id){
        Employee byId = employeeService.getById(id);
        if (byId!=null) {
            return R.success(byId);
        }
        else {
            return R.error("没有查询到员工");
        }

    }



}
