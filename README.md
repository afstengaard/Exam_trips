## Theoretical questions
## http outputs:
-Populate:
POST http://localhost:7070/api/trips/populate

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 10:17:13 GMT
Content-Type: text/plain

<Response body is empty>

Response code: 204 (No Content); Time: 287ms (287 ms); Content length: 0 bytes (0 B)

-Get all trips:
GET http://localhost:7070/api/trips

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:17:40 GMT
Content-Type: application/json
Content-Length: 711

[
{
"id": 1,
"longitude": 45.0,
"latitude": -122.0,
"name": "Beach Adventure",
"price": 100.0,
"category": "BEACH",
"guideId": 1,
"starttime": "2023-01-01T09:00:00",
"endtime": "2023-01-01T17:00:00"
},
{
"id": 2,
"longitude": 44.5,
"latitude": -123.5,
"name": "Forest Hike",
"price": 100.0,
"category": "FOREST",
"guideId": 1,
"starttime": "2023-02-01T10:00:00",
"endtime": "2023-02-01T18:00:00"
},
{
"id": 3,
"longitude": 46.0,
"latitude": -121.5,
"name": "Snow Excursion",
"price": 200.0,
"category": "SNOW",
"guideId": 2,
"starttime": "2023-03-01T08:00:00",
"endtime": "2023-03-01T15:00:00"
},
{
"id": 4,
"longitude": 47.5,
"latitude": -120.5,
"name": "Lake Escape",
"price": 120.0,
"category": "LAKE",
"guideId": 2,
"starttime": "2023-04-01T07:00:00",
"endtime": "2023-04-01T16:00:00"
}
]
Response file saved.
> 2024-11-04T111741.200.json

Response code: 200 (OK); Time: 1183ms (1 s 183 ms); Content length: 711 bytes (711 B)

-Get trip by id:
GET http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:17:54 GMT
Content-Type: application/json
Content-Length: 179

{
"id": 1,
"longitude": 45.0,
"latitude": -122.0,
"name": "Beach Adventure",
"price": 100.0,
"category": "BEACH",
"guideId": 1,
"starttime": "2023-01-01T09:00:00",
"endtime": "2023-01-01T17:00:00"
}
Response file saved.
> 2024-11-04T111755.200.json

Response code: 200 (OK); Time: 27ms (27 ms); Content length: 179 bytes (179 B)

-Add new trip:
GET http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:17:54 GMT
Content-Type: application/json
Content-Length: 179

{
"id": 1,
"longitude": 45.0,
"latitude": -122.0,
"name": "Beach Adventure",
"price": 100.0,
"category": "BEACH",
"guideId": 1,
"starttime": "2023-01-01T09:00:00",
"endtime": "2023-01-01T17:00:00"
}
Response file saved.
> 2024-11-04T111755.200.json

Response code: 200 (OK); Time: 27ms (27 ms); Content length: 179 bytes (179 B)

-Update trip:
PUT http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:18:41 GMT
Content-Type: application/json
Content-Length: 185

{
"id": 1,
"longitude": 45.0,
"latitude": -122.0,
"name": "Updated SNOW Adventure",
"price": 250.0,
"category": "SNOW",
"guideId": 1,
"starttime": "2023-07-02T09:00:00",
"endtime": "2023-07-02T17:00:00"
}
Response file saved.
> 2024-11-04T111841.200.json

Response code: 200 (OK); Time: 36ms (36 ms); Content length: 185 bytes (185 B)

-Delete trip:
DELETE http://localhost:7070/api/trips/1

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 10:18:51 GMT
Content-Type: text/plain

<Response body is empty>

Response code: 204 (No Content); Time: 25ms (25 ms); Content length: 0 bytes (0 B)

-Add guide to trip:
PUT http://localhost:7070/api/trips/2/guides/2

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 10:46:45 GMT
Content-Type: text/plain

<Response body is empty>

Response code: 204 (No Content); Time: 19ms (19 ms); Content length: 0 bytes (0 B)


## Why use put for adding guide?
Because we're updating the trip. we always use put to update.

