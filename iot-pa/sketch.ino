#include <WiFi.h>
#include <HTTPClient.h>

const char* ssid = "Wokwi-GUEST";
const char* password = "";
const int buttonPin = 2;     // GPIO number for the button
const int ledPin = 12;       // GPIO number for the green LED
const String baseUrl = "http://34.69.142.181:8080";
const String endpoint = "/api/scheduleActivities";
const String notifyEndpoint = "/notify";

TaskHandle_t ledTaskHandle;

void setup() {
  Serial.begin(115200);
  pinMode(buttonPin, INPUT_PULLUP);
  pinMode(ledPin, OUTPUT);

  connectToWiFi();

  xTaskCreate(
    ledTask,
    "LED Task",
    10000,
    NULL,
    1,
    &ledTaskHandle
  );
}

void loop() {
  if (WiFi.status() != WL_CONNECTED) {
    connectToWiFi();
  } 

  if (digitalRead(buttonPin) == LOW) {

     Serial.println("Button is pressed");
      digitalWrite(ledPin, HIGH);

    notifyCreatedScheduleActivities();
    delay(200); // Debounce delay

  }
}

void connectToWiFi() {
  Serial.println("Connecting to WiFi...");
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(100);
    Serial.print(".");
  }
  Serial.println("Connected to WiFi");
}

void notifyCreatedScheduleActivities() {
  String url = baseUrl + endpoint + notifyEndpoint;
  Serial.println(url);

    HTTPClient http;
      http.begin(url);
    int httpResponseCode = http.POST("");
    Serial.println(httpResponseCode);
  if (httpResponseCode > 0) {
        String response = http.getString();
        Serial.println(httpResponseCode);
        Serial.println(response);

        digitalWrite(ledPin, HIGH);
        delay(20);
        digitalWrite(ledPin, LOW);
      } else {
        Serial.print("Cannot send POST request: ");
        Serial.println(httpResponseCode);
      }
      http.end();
}

void ledTask(void* parameter) {
  while (true) {
    vTaskDelay(1000 / portTICK_PERIOD_MS);
  }
}