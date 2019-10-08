 만약 war로 배포한다고 생각하고 mvc view로 jsp를 쓰고자 한다면...
    src/main/webapp/WEB-INF/views 디렉토리를 만들어서 거기에 넣어야 함 
    
	src/main/resources/static/ 디렉토리 하위에 js, css 넣고 거기에 js, css 파일 위치 시킴 (대신 이렇게 하면 war, jar에 모두 다 포함됨.)
    
    application.yml에 아래 정보 추가
    spring.mvc.view.prefix = /WEB-INF/views/
    spring.mvc.view.suffix = .jsp
    spring.mvc.static-path-pattern=/resources/**

    (https://penthegom.tistory.com/12)
    (https://nevercaution.github.io/2018/02/22/spring-boot-jsp/)
    
-> war, jar, apache 연동 등을 모두 고려해서 초기 세팅을 해야 할 듯...

정적리소스를 war, jar에 포함시키지 않는 것도 한 방법일듯...