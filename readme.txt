DONE Create JWT utility - to create token

Create web securityconfig class - how to config spring security
DONE  . Create user detail service -> tells spring sec how to deal with uer object


        Get userName , paassword from postman
        Get the USer from DB , create AppUser
  Done       Create SpringSecurityUSer from AppUSer - is a DTO

 . AuthEntry how to handle auth failures.
 . Filter to verfiy the token.

April 17 - WebSecurityConfig.java -> implement this class completely.

May 1
WebSecurityConfig.java
DONE                //TODO  cREATE JWTfILTER - read token and auth the username
DONE            //TODO - Handle Security Exceptions.

May 9
Resolve startup issues.
Plug ExceptionHandling
Add Filter in security chain.


>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
 how to debug  AppUserDetails
         List<GrantedAuthority> authorities = user.getRoles().stream()
                 .map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());


How does AppUserDetails get injected into authentication
AppUserDetails userPrincipal = (AppUserDetails) authentication.getPrincipal();


Create 2 microservice
1 - jwt token service
2. - application that used token.


What is thew appaorach that Niket is doing to save time for tokenization.
Secret is passed to client.
save one rest call.

h2 DATABASE - AppUserRepo

Shortcuts

Back - Ctrl + Alt <
Fworward Ctrl + Alt >

>>>>>>>>>>>>>>>>>>>>>

git token auth for jetbtrains

