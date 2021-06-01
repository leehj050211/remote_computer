#include <SoftwareSerial.h> 
#define BT_RXD 3
#define BT_TXD 2
SoftwareSerial bluetooth(BT_RXD, BT_TXD);
String command;
char* str;
String* macro_command;

void macro();
void presskey();

void setup(){
  Serial.begin(9600);
  bluetooth.begin(9600);
}
void loop(){
  if (bluetooth.available()){
    command=bluetooth.readString();//블루투스로 부터 시리얼 받기
    str = (char*) malloc(sizeof(char)*command.length()+1);//동적 변수
    command.toCharArray(str, command.length()+1);//String에서 char로 변환
    Serial.write(str);
    if(command=="<macro>"){//매크로 입력
      while(1){
        if(bluetooth.available()){
          command=bluetooth.readString();
          if(command=="</macro>"){//매크로 입력 끝
            break;
          }
        }
      }
    }else{
      presskey(command);
    }
    free(str);//메모리 해제
  }
  if (Serial.available()){
    command=Serial.readString();
    str = (char*) malloc(sizeof(char)*command.length()+1);
    command.toCharArray(str, command.length()+1);
    bluetooth.write(str);
    free(str);
  }
}

void macro(){
  
}
void presskey(String key){
  
}
