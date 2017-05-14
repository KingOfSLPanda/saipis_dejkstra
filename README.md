# PORTFL

## TODO

- [x] Add russian language
- [x] Add genders
- [ ] Add photosession types
- [ ] Rebuild database
- [x] Add logo
- [ ] Add new role for unconfirmed users
- [ ] Add tags
- [ ] Add photo stars
- [ ] Add smart crop for gallery
- [ ] Add controller security
- [x] Make profile edit
- [ ] Make photo edit
- [ ] Add drag&drop for gallery
- [ ] Add order of photos

## Properties files

### application.properties

    spring.jpa.hibernate.ddl-auto=update
    
    spring.datasource.url=jdbc:mysql://localhost:3306/{???}?useSSL=false
    spring.datasource.username={???}
    spring.datasource.password={???}
    spring.datasource.driver=com.mysql.jdbc.Driver
    
    spring.data.rest.base-path=/api
    
    spring.jpa.show-sql=true
    
    hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
    hibernate.show_sql=true
    hibernate.format_sql=true


### email.properties

    smtp.host=smtp.{???}.ru
    smtp.port=465
    smtp.protocol=smtps
    smtp.username={???}
    smtp.password={???}
    
    cloud_name={???}
    cloud_api_key={???}
    cloud_api_secret={???}