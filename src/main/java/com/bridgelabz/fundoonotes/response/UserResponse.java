package com.bridgelabz.fundoonotes.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserResponse {

   public Map<String,Object> badRequest(String type,Integer code,List errors)
   {
    Map<String,Object> map=new HashMap<String,Object>();
    map.put("name","badrequest");
    map.put("code",400);
    map.put("Error",errors);
    return map;
   }
   
   
   public Map<String,Object> ok(String type,Integer code,List errors)
   {
	   Map<String,Object> map=new HashMap<String,Object>();
	   map.put("name","ok");
	   map.put("code",200);
	   map.put("Error",errors);
	   return map;
   }
}
