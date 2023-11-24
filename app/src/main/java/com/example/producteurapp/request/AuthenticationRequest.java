package com.example.producteurapp.request;



public class AuthenticationRequest  {
     public String email;
     public String password;

     public AuthenticationRequest(String email, String password) {
          this.email = email;
          this.password = password;
     }
}
