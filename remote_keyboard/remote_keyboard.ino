#include <Keyboard.h>
String command;
char *str;
char **macro_command;
int x, y, row, col;

void macro();
void presskey();

void setup(){
  Serial1.begin(9600);
  Keyboard.begin();
}
void loop(){
  if (Serial1.available()){
    command=Serial1.readString();//블루투스로 부터 시리얼 받기
    str = (char*) malloc(sizeof(char)*command.length()+1);//동적 변수
    command.toCharArray(str, command.length()+1);//String에서 char로 변환
    if(command=="<macro>"){//매크로 입력
      row=0;
      while(1){
        if(Serial1.available()){
          command=Serial1.readString();
          if(command=="</macro>"){//매크로 입력 끝
            break;
          }
          macro_command = (char**) malloc(sizeof(char*)*row);
          macro_command[row] = (char*) malloc(sizeof(char)*command.length()*row+1);//동적 2차원배열
          command.toCharArray(macro_command[row], command.length()+1);//String에서 char로 변환
          row++;
        }
      }
    }else{
      presskey(str);
    }
    free(str);
    for (int i=0;i<row;i++){//메모리 해제
      free(macro_command[i]);
    }
    free(macro_command);
  }
}

void macro(){
  
}
void presskey(char key){
  Keyboard.write(key);
}
