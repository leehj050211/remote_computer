#include <Keyboard.h>
#include <Mouse.h>
String command, macro_command, str;
char str1;
int Key;
int mouse_x=0, mouse_y=0;

void key();
void mouse();
void macro();

void setup(){
  Serial1.begin(9600);
  Keyboard.begin();
}
void loop(){
  if (Serial1.available()){
    command=Serial1.readStringUntil('|');// |까지의 시리얼 값을 받음
    command.trim();// 앞뒤 공백제거
    if (command.charAt(0) == '/'){
      if(command.substring(1, 4).equals("txt")){
          int slash_index = command.indexOf('/', 1);// /글자의 위치 값 저장
          Keyboard.print(command.substring(slash_index + 1));
      }else if(command.substring(1, 4).equals("key")){
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
      }else if(command.substring(1, 4).equals("pre")){
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
      }else if(command.substring(1, 7).equals("keyall")){
          Keyboard.releaseAll();
      }else if(command.substring(1, 4).equals("mm")){
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
      }else if(command.substring(1, 3).equals("ml")){
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
      }else if(command.substring(1, 3).equals("mr")){
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
      }else if(command.substring(1, 3).equals("mw")){
          int slash_index = command.indexOf('/', 1);// /글자의 위치 값 저장
          Mouse.move(0, 0, command.substring(slash_index + 1).toInt());
      }else if(command.substring(1, 6).equals("delay")){
          int slash_index = command.indexOf('/', 1);// /글자의 위치 값 저장
          delay(command.substring(slash_index + 1).toInt());
      }
    }
  }
}
