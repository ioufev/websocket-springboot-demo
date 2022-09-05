### 依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### Controller

```java
@Controller
public class ThymeleafController {
    @GetMapping("/thymeleaf")
    public String hello(HttpServletRequest request, @RequestParam(value = "description", required = false,
            defaultValue = "springboot-thymeleaf") String description1){
        request.setAttribute("description_value", description1);
        return "index";
    }
}
```

### HTML

index.html

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Thymeleaf demo</title>
</head>
<body>
<p>description字段值为：</p>
<p th:text="${description_value}">这里显示的是 description 字段内容</p>
</body>
</html>
```

### 访问

运行：springboot运行

访问：http://localhost:8080/thymeleaf?description=demo