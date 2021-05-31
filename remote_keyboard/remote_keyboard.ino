#include <SoftwareSerial.h> 
#define BT_RXD 3
#define BT_TXD 2
SoftwareSerial bluetooth(BT_RXD, BT_TXD);
void setup(){
  Serial.begin(9600);
  bluetooth.begin(9600);
}
void loop(){
  if (bluetooth.available()){
    Serial.write(bluetooth.read());
  }
  if (Serial.available()){
    bluetooth.write(Serial.read());
  }
}
