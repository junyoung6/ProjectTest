package com.project.rentcar.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
public class PortOneController {


    private TokenResponse tokenResponse;

    @GetMapping("/main")
    public void main() {
        log.info("GET /portOne/main...");
    }

    @GetMapping("/getToken")
    @ResponseBody
    public void getToken() {
        log.info("GET /portOne/getToken...");

        String url = "https://api.iamport.kr/users/getToken";

        //HTTP 요청 헤더
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("imp_key", "2525535482200355");
        params.add("imp_secret", "laHAn8aCWDfQgJh51ffnhdbfjpTDS3699fdIGlea5Ztz98zTqjBAlFCqZlYdlNUTZDZvBVTnK4xGiDkQ");

        //HTTP 엔티티(헤더 + 파라미터)
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        //HTTP 요청 후 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TokenResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, TokenResponse.class);
        System.out.println(response.getBody());
        this.tokenResponse = response.getBody();

    }


    @GetMapping("/getPayments")
    @ResponseBody
    public void getPayments() {
        log.info("GET /portOne/getPayments...");

        String url = "https://api.iamport.kr/payments?imp_uid[]=[]&merchant_uid[]=[]";

        //HTTP 요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + tokenResponse.getResponse().getAccess_token());

//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("imp_key","1546549255738924");
//        params.add("imp_secret","Zjy29fdoI6cNNwIZYMrDX4dkLCLvf6HFyFbbVCNwlRD5YzHCEQV4onWbydWFVbT1ID1Zw0Kp6POYsvKg");

        //HTTP 엔티티(헤더 + 파라미터)
        HttpEntity entity = new HttpEntity<>(headers);

        //HTTP 요청 후 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println(response.getBody());

    }
    @GetMapping(value="/getAuthInfo/{imp_uid}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody PortOneAuthInfoResponse getAuthInfo(@PathVariable("imp_uid") String imp_uid ){
        getToken();
        log.info("GET /portOne/getAuthInfo.."+imp_uid);

        //URL
        String url="https://api.iamport.kr/certifications/"+imp_uid;
        //HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        headers.add("Authorization","Bearer "+tokenResponse.getResponse().getAccess_token());
        //PARAMS
        MultiValueMap params = new LinkedMultiValueMap();

        //ENTITY
        HttpEntity<  MultiValueMap<String, String> > entity = new HttpEntity(params,headers);

        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<PortOneAuthInfoResponse> response = rt.exchange(url,HttpMethod.GET,entity,PortOneAuthInfoResponse.class);
        //RESPONSE
        System.out.println(response.getBody());

        return response.getBody();

    }


    //결제 정보 확인

    //결제 취소 요청
    @GetMapping("/cancel/{imp_uid}")
    public @ResponseBody void cancel(
            @PathVariable String imp_uid,
            @PathVariable String pay_id
    ){

        getToken();
        log.info("GET /payment/cancel..");
        // access-token 받기


        //URL
        String url = "https://api.iamport.kr/payments/cancel";

        //Request Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+tokenResponse.getResponse().getAccess_token());
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        //Request Body
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("imp_uid",imp_uid);

        //Hader+Body
        HttpEntity< MultiValueMap<String,String>> entity = new HttpEntity(params,headers);

        //요청
        RestTemplate restTemplate = new RestTemplate();

        //반환값확인
        ResponseEntity<String> resp =  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);

        System.out.println(resp);
        System.out.println(resp.getBody());
    }


    @Data
    private static class Response{
        public String access_token;
        public int now;
        public int expired_at;
    }
    @Data
    private static class TokenResponse{
        public int code;
        public Object message;
        public Response response;
    }

    //-----------------------------
    //인증정보 가져오기 Class
    //-----------------------------
    @Data
    private static class AuthInfoResponse{
        public int birth;
        public String birthday;
        public boolean certified;
        public int certified_at;
        public boolean foreigner;
        public Object foreigner_v2;
        public Object gender;
        public String imp_uid;
        public String merchant_uid;
        public String name;
        public String origin;
        public String pg_provider;
        public String pg_tid;
        public String phone;
        public Object unique_in_site;
        public String unique_key;
    }
    @Data
    private static class PortOneAuthInfoResponse{
        public int code;
        public Object message;
        public AuthInfoResponse response;
    }
}