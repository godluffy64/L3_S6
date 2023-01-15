package com.example;

public class FizzBuzz {

    public String getResult(int i) {
        if (i % 3 == 0 && i % 5 == 0) {
            return "FizzBuzz";
        } else if (i % 3 == 0 || i == 3) {
            return "Fizz";

        } else if (i % 5 == 0 || i ==5) {
            return "Buzz";
        }
        else {
            return String.valueOf(i);
        }
        }
    public void the100(){
        for (int i = 1; i <= 100; i++) {
            System.out.println(getResult(i));
    }
}
}
