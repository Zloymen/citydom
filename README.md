Тестовое задание “разработчик”.

Цель: Создать распределенный сервис для обработки soap-сообщений

1 - веб soap-сервис

2 - поддержка маршрутизации сообщений в разные обработчики, в зависимости от методов указанных в сообщении

3 - обработчик должен давать ответ веб-серверу ( либо ошибку ), тот в свою очередь должен создавать из ответа обработчика soap-сообщение и посылать его клиенту, который делал запрос.

4 - сервис должен иметь хранилище данных ( может использоваться любой тип хранилища )

5 - сервис должен поддерживать авторизацию

6 - поддержка масштабирования сервисов


Желаемый результат:
    • ссылка на код на github
    • реализация на высокоуровневом языке  ( предпочтительно java, python, etc... )

Дополнительно:
    • wsdl схема сервиса
    • Создание пользователей через soap протокол
    • докер контейнеры сервисов
    • docker-compose файл для запуска




Приложение 1. Сообщения обрабатываемые сервисом

Получение цены акции
Пример запроса:
<?xml version="1.0"?>

<soap:Envelope
xmlns:soap="http://www.w3.org/2003/05/soap-envelope/"
soap:encodingStyle="http://www.w3.org/2003/05/soap-encoding">

<soap:Body xmlns:m="http://www.example.org/stock">
  <m:GetStockPrice>
    <m:StockName>IBM</m:StockName>
  </m:GetStockPrice>
</soap:Body>

</soap:Envelope>

Пример ответа:
<?xml version="1.0"?>

<soap:Envelope
xmlns:soap="http://www.w3.org/2003/05/soap-envelope/"
soap:encodingStyle="http://www.w3.org/2003/05/soap-encoding">

<soap:Body xmlns:m="http://www.example.org/stock">
  <m:GetStockPriceResponse>
    <m:Price>34.5</m:Price>
  </m:GetStockPriceResponse>
</soap:Body>

</soap:Envelope>


Установка параметров стоимости акций ( необходима авторизация )
пример запроса:
<?xml version="1.0"?>

<soap:Envelope
xmlns:soap="http://www.w3.org/2003/05/soap-envelope/"
soap:encodingStyle="http://www.w3.org/2003/05/soap-encoding">
<soap:Body xmlns:m="http://www.example.org/stock">
  <m:SetStockPrice>
    <m:Parameters>
    	<StockName xsi:type="xsd:string">IBM</StockName>
        <price xsi:type="xsd:float">33.5</price>
    </m:Parameters>
  </m:SetStockPrice>
</soap:Body>
</soap:Envelope>

пример ответа:
<?xml version="1.0"?>

<soap:Envelope
xmlns:soap="http://www.w3.org/2003/05/soap-envelope/"
soap:encodingStyle="http://www.w3.org/2003/05/soap-encoding">

<soap:Body xmlns:m="http://www.example.org/stock">
  <m:SetStockPriceResponse>
    <m:Result>0</m:Result>
  </m:SetStockPriceResponse>
</soap:Body>

</soap:Envelope>

Описание:

сборка проекта для тестовой зоны:

создать бд
 database: citydom
 user: citydom & password "citydom"
     

mvn generate-sources
mvn install

cd target 
java -jar timesheet-1.0.jar

тест:
curl --header "content-type: text/xml" -d @request_GetStockPrice.xml http://localhost:4400/ws


сборка проекта для запуска в Docker:

mvn generate-sources
mvn install -P prod
mvn com.spotify:dockerfile-maven-plugin:1.3.6:build -P prod

cd docker

docker-compose -f app.yml up

тест:
curl --header "content-type: text/xml" -d @request_GetStockPrice.xml http://localhost:8081/ws
  
