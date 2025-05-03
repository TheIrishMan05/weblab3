# Машкин Григорий Андреевич P3230, вариант: 11400

Нужно было написать двухстраничное JSF-приложение. На первой странице часы, написанные на JS(обновление раз в 8 секунд), а на второй - форма и график, который меняется динамически в зависимости от введённых параметров. Точки после отправки формы сохраняются в БД под управлением Oracle DBMS.

### Что использовалось для написания лабораторной?

1. Client-side: Javascript, HTML + CSS
2. Server-side: Jakarta EE, CDI Beans, JSF(Primefaces), JDBC
3. СУБД: Oracle
4. Server: TomEE 10.1.30
5. Сборка проекта: Docker

### Как запустить лабораторную?

```
    git clone https://github.com/TheIrishMan05/weblab3.git
    cd weblab3
    docker-compose up --build
```
