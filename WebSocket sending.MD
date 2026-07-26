# Configure Bruno database updates

> how do I setup my headers and message and protocols in Bruno 4.0 to update the database with this application

<details><summary>4 previous messages</summary>

> I’ll inspect the application’s endpoints, DTOs, and WebSocket configuration so I can give you exact Bruno 4.0 settings rather than generic examples.
>
> <details><summary>Ran 2 commands</summary>
>
> - Ran `rg -n --hidden -S "WebSocket|websocket|@MessageMapping|@PostMapping|@PutMapping|@PatchMapping|@RequestMapping|Sec-WebSocket-Protocol|subprotocol|STOMP|Authorization|sensor|reading" .`
> - Ran `Get-Content arduino-websocket-endpoints\src\main\java\com\kc8qzo\arduinows\config\WebSocketConfig.java; Get-Content arduino-websocket-endpoints\src\main\java\com\kc8qzo\arduinows\endpoints\SensorReadingEndpoint.java; Get-ChildItem arduino-websocket-contracts\src\main\java -Recurse -File | ForEach-Object { $_.FullName; Get-Content $_.FullName }; Get-ChildItem arduino-websocket-endpoints\src\main\resources -Recurse -File | ForEach-Object { $_.FullName; Get-Content $_.FullName }`
>
> </details>
>
> This app uses STOMP over a raw WebSocket—not a REST `POST`. The key distinction in Bruno is that the connection URL and WebSocket subprotocol are configured first, then the database update is triggered by sending STOMP frames. I’m checking Bruno’s exact UI terminology for version 4.0.
>
> <details><summary>Ran a command, searched the web</summary>
>
> Searched the web for `site:docs.usebruno.com Bruno 4.0 WebSocket request headers protocols ...`
>
> Searched the web for `Bruno websocket STOMP null terminator send frame ...`
>
> - Ran `Get-Content arduino-websocket-db\src\main\java\com\kc8qzo\arduinows\db\domain\SensorReading.java; Get-Content arduino-websocket-services\src\main\java\com\kc8qzo\arduinows\services\SensorReadingService.java; Get-Content arduino-websocket-db\src\main\java\com\kc8qzo\arduinows\db\repository\SensorReadingRepository.java`
>
> </details>
</details>

This application does not use an HTTP `POST` to write sensor data. It uses STOMP messages over WebSocket.

## Bruno connection

Create a new **WebSocket** request in Bruno 4.0 with:

```text
ws://localhost:8080/sensor-reading
```

In the **Headers** tab:

| Header | Value |
|---|---|
| `Sec-WebSocket-Protocol` | `v12.stomp` |

No authorization or additional HTTP headers are required by the current application.

Click **Connect**. Do not send the sensor JSON immediately—you must establish the STOMP session first.

## 1. Send the STOMP CONNECT frame

Select the **Text** message type and send:

```text
CONNECT
accept-version:1.2
host:localhost

^@
```

Important: `^@` represents one actual NUL byte (`0x00`), not the two characters `^` and `@`, and not the text `\0`. Every STOMP frame must end with that NUL byte.

You should receive a response beginning with:

```text
CONNECTED
version:1.2
```

## 2. Subscribe to the result topic

This lets Bruno display the saved database record:

```text
SUBSCRIBE
id:sensor-subscription
destination:/topic/sensor-reading
ack:auto

^@
```

Again, replace `^@` with an actual NUL byte.

## 3. Send the database record

Send this as a **Text** message—not Bruno’s JSON message type—because the STOMP headers and JSON body must be in the same WebSocket message:

```text
SEND
destination:/app/sensor-reading
content-type:application/json

{"temperature":72.5,"humidity":45.2,"light":600,"passValue":123456,"sentAt":"2026-07-25T16:30:00Z"}
^@
```

The blank line between `content-type` and `{` is required.

The application will:

1. Set `postedAt` to the server’s current time.
2. insert the record into the SQLite `arduino_sensor` table;
3. assign an automatically generated `id`;
4. publish the saved object to `/topic/sensor-reading`.

You should receive something similar to:

```json
{
  "id": 1,
  "temperature": 72.5,
  "humidity": 45.2,
  "light": 600,
  "passValue": 123456,
  "sentAt": "2026-07-25T16:30:00Z",
  "postedAt": "2026-07-25T16:30:05Z"
}
```

## Reading the newest database record

Subscribe with:

```text
SUBSCRIBE
id:read-subscription
destination:/topic/read-sensor-reading
ack:auto

^@
```

Then send:

```text
SEND
destination:/app/read-sensor-reading

^@
```

## Important Bruno limitation

Bruno provides a raw WebSocket client; it does not automatically construct STOMP frames. If its editor sends `\0` literally instead of an actual NUL byte, Spring will not process the frame. In that case, the connection may succeed but `CONNECT` or `SEND` will appear to do nothing. You would need a STOMP-aware client or add a conventional REST endpoint to this application.

The WebSocket and subprotocol setup follows Bruno’s documented WebSocket configuration behavior: [Bruno WebSocket request documentation](https://docs.usebruno.com/send-requests/websocket/create-request).
