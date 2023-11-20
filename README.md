# Spring Security 개인 미션

## Issue

- `HTTPSecurity` 설정 할 때 사용하는 `WebSecurityConfigurerAdapter` 사용 중단 (deprecated)
    - WebSecurityConfigure 정의할 때 WebSecurityConfigurerAdapter를 상속하는
      대신 [SecurityFilterChain](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)
      Bean 등록
- `WebSecurity` 설정 할 때 사용하는 `antMatchers()` 사용 중단 (deprecated)
  - [authorizeHttpRequests(), formLogin()](https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html#use-new-requestmatchers)
    메서드 사용하는 방식 변경 -> 파라미터에 람다식 넣는 것으로 변경