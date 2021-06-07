#include <Keyboard.h>
#include <Mouse.h>
String command, macro_command;
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
    if(command.substring(0, 3).equals("Mmo")){
        int slash_index = command.indexOf('/');// /글자의 위치값 저장
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
    }else if(command.substring(0, 3).equals("Mlc")){
        int slash_index = command.indexOf('/');// /글자의 위치값 저장
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
    }else if(command.substring(0, 3).equals("Mrc")){
        int slash_index = command.indexOf('/');// /글자의 위치값 저장
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
    }else if(command.substring(0, 3).equals("Key")){
        int slash_index = command.indexOf('/');// /글자의 위치값 저장
        Keyboard.print(command.substring(slash_index + 1));
    }
  }
}
