#include <Keyboard.h>
#include <Mouse.h>
String code, macro_code, str;
char str1;
int macro_status=0, macro_reload=1;

void macro();
void keyboard();
void mouse();

void setup(){
  Serial.begin(9600);
  Serial1.begin(9600);
  Keyboard.begin();
  Mouse.begin();
}
void loop(){
  if (Serial1.available()){// 블루투스
    if(macro_status==2){
      macro_code=Serial1.readString();// 입력 받은 매크로 코드 저장
      macro_code.trim();// 앞뒤 공백제거
      macro_status==0;
    }else{
      code=Serial1.readStringUntil('|');// |까지의 시리얼 값을 받음
      code.trim();// 앞뒤 공백제거
      command(code);// 명령 실행
    }
  }
  if (Serial.available()){// 매크로
    if(macro_status==1){
      code=Serial.readStringUntil('|');// |까지의 시리얼 값을 받음
      code.trim();// 앞뒤 공백제거
      command(code);
    }
  }
}
void macro(){
  macro_code.toCharArray(str1, macro_code.length()+1);
  Keyboard.print(macro_code);
}

void command(String command){
  int Key, mouse_x=0, mouse_y=0;
  if (command.charAt(0) == '/'){
    if(command.substring(1, 4).equals("txt")){// 텍스트 입력
      int slash_index = command.indexOf('/', 1);// /글자의 위치 값 저장
      Keyboard.print(command.substring(slash_index + 1));
    }else if(command.substring(1, 4).equals("key")){// 특수 키 누르기
      int slash_index = command.indexOf('/', 1);// /글자의 위치 값 저장
      int comma_index = command.indexOf(',', slash_index + 1);// ,글자의 위치값 저장
      Key = command.substring(comma_index + 1).toInt();
      switch(command.substring(slash_index + 1, comma_index).toInt()){
        case 0:
          Keyboard.release(Key);
          break;
        case 1:
          Keyboard.press(Key);
          break;
        case 2:
        default:
          Keyboard.press(Key);
          Keyboard.release(Key);
          break;
      }
    }else if(command.substring(1, 4).equals("pre")){// 키 누르기
      int slash_index = command.indexOf('/', 1);// /글자의 위치 값 저장
      int comma_index = command.indexOf(',', slash_index + 1);// ,글자의 위치값 저장
      str = command.substring(comma_index + 1);
      str.toCharArray(str1, str.length()+1);
      switch(command.substring(slash_index + 1, comma_index).toInt()){
        case 0:
          Keyboard.release(str1);
          break;
        case 1:
          Keyboard.press(str1);
          break;
        case 2:
        default:
          Keyboard.press(str1);
          Keyboard.release(str1);
          break;
      }
    }else if(command.substring(1, 7).equals("keyall")){// 키 입력 해제
      Keyboard.releaseAll();
    }else if(command.substring(1, 4).equals("mm")){// 마우스 이동
      int slash_index = command.indexOf('/', 1);// /글자의 위치 값 저장
      int comma_index = command.indexOf(',', slash_index + 1);// ,글자의 위치값 저장
      if (comma_index > 0){
        mouse_x = command.substring(slash_index + 1, comma_index).toInt();
        mouse_y = command.substring(comma_index + 1).toInt();
      }
      else{
        mouse_x = command.substring(slash_index + 1).toInt();
        mouse_y = 0;
      }
      Mouse.move(mouse_x, mouse_y, 0);
    }else if(command.substring(1, 3).equals("ml")){// 마우스 왼쪽 클릭
      int slash_index = command.indexOf('/', 1);// /글자의 위치 값 저장
      switch(command.substring(slash_index + 1).toInt()){
        case 0:
          Mouse.release(MOUSE_LEFT);
          break;
        case 1:
          Mouse.press(MOUSE_LEFT);
          break;
        case 2:
        default:
          Mouse.press(MOUSE_LEFT);
          Mouse.release(MOUSE_LEFT);
          break;
      }
    }else if(command.substring(1, 3).equals("mr")){// 마우스 오른쪽 클릭
      int slash_index = command.indexOf('/', 1);// /글자의 위치 값 저장
      switch(command.substring(slash_index + 1).toInt()){
        case 0:
          Mouse.release(MOUSE_RIGHT);
          break;
        case 1:
          Mouse.press(MOUSE_RIGHT);
          break;
        case 2:
        default:
          Mouse.press(MOUSE_RIGHT);
          Mouse.release(MOUSE_RIGHT);
          break;
      }
    }else if(command.substring(1, 3).equals("mw")){// 마우스 휠
      int slash_index = command.indexOf('/', 1);// /글자의 위치 값 저장
      Mouse.move(0, 0, command.substring(slash_index + 1).toInt());
    }else if(command.substring(1, 6).equals("delay")){// 딜레이
      int slash_index = command.indexOf('/', 1);// /글자의 위치 값 저장
      delay(command.substring(slash_index + 1).toInt());
    }else if(command.substring(1, 6).equals("start")){// 매크로 실행
      macro_status=1;
      macro();
    }else if(command.substring(1, 5).equals("stop")){// 매크로 실행
      macro_status=0;
    }else if(command.substring(1, 5).equals("edit")){// 매크로 입력
      int slash_index = command.indexOf('/', 1);// /글자의 위치 값 저장
      macro_reload=command.substring(slash_index + 1).toInt();
      macro_status=2;
    }else{
      
    }
  }
}
