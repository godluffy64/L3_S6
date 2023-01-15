%{
   #include <stdio.h> 
   
%}
identifiant [a-zA-Z][a-zA-Z0-9]*
%%

{identifiant}+"==" { printf("La variable: %s a pour valeur :  ", yytext); }
{identifiant} { printf("%s\n", yytext); } 

%%
int yywrap(){
    return 1;
}
int main(){
    printf("Entrez un mot : \n");
    yylex();
    return 0;
    }