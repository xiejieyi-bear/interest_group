package com.interest.auth.rest;

import com.interest.auth.Constant;
import com.interest.auth.bean.ResultBean;
import com.interest.auth.bean.UserBean;
import com.interest.auth.dao.CourtRepository;
import com.interest.auth.dao.UserRepository;
import com.interest.auth.daobean.Court;
import com.interest.auth.daobean.User;
import com.interest.auth.util.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 类描述：场地管理类
 *
 * @author
 */
@RestController
public class CourtRest
{
    @Autowired
    private CourtRepository courtRepository;



    @RequestMapping(value = "/court",method = RequestMethod.POST )
    public boolean addCourt(@RequestBody Map<String, String> payload)
    {
        // TODO 校验入口参数
        System.out.println("input value " + payload);

        String courtName = payload.get("name");
        String address = payload.get("addr");
        String telephone = payload.get("telephone");
        Court court = new Court();
        court.setAddr(address);
        court.setName(courtName);
        court.setTelephone(telephone);
        try{
            courtRepository.save(court);
        }catch(Exception ex){
            System.out.println("save court  failed " + ex);
            return false;
        }
        return true;
    }

    //编辑场地信息
    @RequestMapping(value = "/court",method = RequestMethod.PUT )
    public @ResponseBody ResultBean editCourtByid(@RequestBody Map<String, String> payload){
        String courtName = payload.get("name");
        String address = payload.get("addr");
        String telephone = payload.get("telephone");

        Map<String,Object> retMap = new HashMap<String,Object>(10);
        ResultBean result = new ResultBean(Constant.SUCCESS,retMap);
        try{
            Long id = Long.valueOf(payload.get("id"));
            if(!courtRepository.exists(id)){
                result = new ResultBean(1,retMap);
                return result;
            }
            Court court = new Court();
            court.setAddr(address);
            court.setName(courtName);
            court.setTelephone(telephone);
            court.setId(id);
            courtRepository.save(court);
        }catch(Exception ex){
            System.out.println("save court  failed " + ex);
            result = new ResultBean(1,retMap);
            return result;
        }
        return result;
    }


    //查询所有的场地信息
    @RequestMapping(value = "/court",method = RequestMethod.GET )
    public @ResponseBody
    ResultBean queryAllCourts()
    {
        Iterable courts = courtRepository.findAll();
        ResultBean result = new ResultBean(Constant.SUCCESS,courts);
        return result;
    }

    @RequestMapping(value = "/court/{id}",method = RequestMethod.DELETE )
    public @ResponseBody ResultBean delete(@PathVariable Long id)
    {
        Integer retCode = 1;
        try{
            courtRepository.delete(id);
            retCode = Constant.SUCCESS;
        }catch(Exception ex){
            System.out.println("delete court failed ,ex = " + ex);
        }
        ResultBean result = new ResultBean(retCode,null);
        return result;
    }
}
