spring.application.name=spring-boot
spring.datasource.url=jdbc:postgresql://localhost:5332/amigos
spring.datasource.username=bank_user
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true

spring.ai.openai.api-key=${OPEN_AI_KEY}